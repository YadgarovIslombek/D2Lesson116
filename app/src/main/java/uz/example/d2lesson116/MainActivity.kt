package uz.example.d2lesson116

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
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
            binding.progress.isVisible = true
            binding.buttonLoad.isEnabled = false
            loadCity {
                binding.tvLocation.text = it
                loadTemp(it) {it1->
                    binding.tvTemperature.text = it1.toString()
                    binding.progress.isVisible = false
                    binding.buttonLoad.isEnabled = true
                }
            }

        }
    }

    private fun loadTemp(city: String, callback: (Int) -> Unit) {
        thread {
            Thread.sleep(5000) //ikinnchi patok
            Handler(Looper.getMainLooper()).post{
                Toast.makeText(this, "Loading temperature for city $city", Toast.LENGTH_SHORT).show()
            }
            Handler(Looper.getMainLooper()).post{
            callback.invoke(17)
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {  //bu yangi patok
            Thread.sleep(5000)
            Handler(Looper.getMainLooper()).post{
            callback.invoke("Urgench")

            }
        }


    }
}