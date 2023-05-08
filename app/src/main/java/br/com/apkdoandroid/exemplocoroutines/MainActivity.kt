package br.com.apkdoandroid.exemplocoroutines

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.apkdoandroid.exemplocoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonIniciar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                repeat(10){index ->
                    Log.d("Teste","Contando: $index - Thread: ${Thread.currentThread().name}")
                    //Thread.sleep(2000)
                    delay(2000)
                    withContext(Dispatchers.Main){
                             binding.buttonIniciar.text = "Contando: $index - Thread: ${Thread.currentThread().name}"

                    }
                }
            }
        }
    }
}