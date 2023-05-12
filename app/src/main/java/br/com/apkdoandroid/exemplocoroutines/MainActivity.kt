package br.com.apkdoandroid.exemplocoroutines

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.apkdoandroid.exemplocoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureNanoTime

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var job : Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonIniciar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                repeat(10){index ->
                    Log.d("Coroutines","Contando: $index - Thread: ${Thread.currentThread().name}")
                    //Thread.sleep(2000)
                    delay(2000)
                    withContext(Dispatchers.Main){
                             binding.buttonIniciar.text = "Contando: $index - Thread: ${Thread.currentThread().name}"

                    }
                }
            }
        }

        binding.buttonParar.setOnClickListener {
            job?.cancel()
            binding.buttonSuspensa.text = "Reiniciar"
            binding.buttonSuspensa.isEnabled = true
        }

        binding.buttonSuspensa.setOnClickListener {
          /*  CoroutineScope(Dispatchers.IO).launch {
              //  executar()
                job =  CoroutineScope(Dispatchers.IO).launch {
                   /* withTimeout(5000L){
                        exec()
                    }*/

                    //tarefas simultaneas

                    var tempo  =  measureNanoTime {
                        var resultado1: String? = null
                        var resultado2: String? = null
                      val job1 =  launch {
                            resultado1 = tarefa1()
                        }
                      val job2 =  launch {
                            resultado2 = tarefa2()
                        }
                        // val resultado1 = tarefa1()
                        // val resultado2 = tarefa2()

                        job1.join()
                        job2.join()
                        Log.d("Coroutines","Resultado1: $resultado1")
                        Log.d("Coroutines","Resultado2: $resultado2")
                    }
                    Log.d("Coroutines","Tempo: $tempo")

                }

            }*/

            sync_()
        }

    }

    private fun sync_(){
        CoroutineScope(Dispatchers.IO).launch {
            //  executar()
            job =  CoroutineScope(Dispatchers.IO).launch {
                /* withTimeout(5000L){
                     exec()
                 }*/

                //tarefas simultaneas

                var tempo  =  measureNanoTime {
                    val resultado1 =  async {tarefa1() }
                    val resultado2 =  async {tarefa2() }


                    Log.d("Coroutines","Resultado1: ${resultado1.await()}")
                    Log.d("Coroutines","Resultado2: ${resultado2.await()}")
                }
                Log.d("Coroutines","Tempo: $tempo")

            }

        }
    }

    private suspend fun  tarefa1() : String{
        repeat(15){index ->
            Log.d("Log.d(\"Coroutines\",\"tarefa2: $index - Thread: ${Thread.currentThread().name}\")","tarefa1: $index - Thread: ${Thread.currentThread().name}")
            //Thread.sleep(2000)
            delay(2000)

        }
        return "Executou tarefa 1"
    }

    private suspend fun  tarefa2() : String{
        repeat(15){index ->
            Log.d("Coroutines","tarefa2: $index - Thread: ${Thread.currentThread().name}")
            //Thread.sleep(2000)
            delay(2000)

        }
        return "Executou tarefa 2"
    }

    private suspend fun  exec(){


            repeat(15){index ->
                Log.d("Coroutines","Contando: $index - Thread: ${Thread.currentThread().name}")
                //Thread.sleep(2000)
                delay(2000)
                withContext(Dispatchers.Main){
                    binding.buttonSuspensa.text = "Contando: $index - Thread: ${Thread.currentThread().name}"
                    binding.buttonSuspensa.isEnabled = false

                }
            }



    }

    private suspend fun recuperarPostagem(idUsuario: Int) : List<String>{
        delay(2000)

        return listOf("Elanilson","Rafaela","Miguel")

    }
    private suspend fun executar(){
        var usuario = recuperarUsuarioLogado()
        Log.d("Coroutines","usuario: ${usuario.nome} - Thread: ${Thread.currentThread().name}")
        var postagens =  recuperarPostagem(usuario.id)
        Log.d("Coroutines","postagens: ${postagens.size} - Thread: ${Thread.currentThread().name}")
    }
      private  suspend fun recuperarUsuarioLogado() : Usuario{
           delay(2000)
           return Usuario(10,"Elanilson")
       }
}