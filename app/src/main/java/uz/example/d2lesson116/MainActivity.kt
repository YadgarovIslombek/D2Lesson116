package uz.example.d2lesson116

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.example.d2lesson116.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
//            lifecycleScope.launch {
//                loadData()
//            }
            loadDataWithoutCoroutine()
        }
    }

    private suspend fun loadData() {
        Log.d("TAG", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity() //1

        binding.tvLocation.text = city
        val temp = loadTemp(city) //2

        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("TAG", "Load finished: $this") // 3
    }

    private fun loadDataWithoutCoroutine(step: Int = 0, obj: Any? = null) {
        when (step) {
            0 -> {
                Log.d("TAG", "Load started: $this")
                binding.progress.isVisible = true
                binding.buttonLoad.isEnabled = false
                loadCityWithoutCorountine {
                    loadDataWithoutCoroutine(1, it)
                }
            }

            1 -> {
                val city = obj as String
                binding.tvLocation.text = city
                loadTempWithoutCoroutine(city){
                    loadDataWithoutCoroutine(2,it)
                }
            }
            2->{
                val temp = obj as Int
                binding.tvTemperature.text = temp.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
                Log.d("TAG", "Load finished: $this") // 3
            }
        }
    }

    private suspend fun loadTemp(city: String): Int {
        delay(5000)
        Toast.makeText(this, "Loading temperature for city $city", Toast.LENGTH_SHORT)
            .show()
        return 17
    }

    private fun loadTempWithoutCoroutine(city: String, callback: (Int) -> Unit) {
        Toast.makeText(this, "Loading temperature for city $city", Toast.LENGTH_SHORT)
            .show()
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke(17)
        },5000)
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Urgench"
    }

    private fun loadCityWithoutCorountine(callback: (String) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke("Urgench")
        }, 5000)

    }
}