package br.senac.list.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import br.senac.list.databinding.ActivityCepSearchBinding
import br.senac.list.model.Cep
import br.senac.list.services.CepService
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CepSearchActivity : AppCompatActivity() {
    lateinit var binding: ActivityCepSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCepSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtCepResults.text = "Nenhum CEP buscado."
        binding.progressBarCep.visibility = View.INVISIBLE

        binding.btnSearchCep.setOnClickListener {
            var cepTyped = binding.txtEnterCep.text.toString()

            if (cepTyped.isEmpty()) {
                Snackbar.make(
                        binding.cepContainer,
                        "Campo em branco.",
                        Snackbar.LENGTH_LONG
                ).show()
            } else {
                val cepFormat = "^\\d{5}\\-\\d{3}\$"
                val validCep = cepFormat.toRegex().find(cepTyped)?.value

                if (validCep.isNullOrEmpty()) {
                    Snackbar.make(
                            binding.cepContainer,
                            "Padrão de CEP incorreto.",
                            Snackbar.LENGTH_LONG
                    ).show()
                }
                else {
                    searchCep(cepTyped)
                }

            }
        }
    }

    fun searchCep(CepToSearch: String){
        val http = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://viacep.com.br")
                .addConverterFactory(GsonConverterFactory.create())
                .client(http)
                .build()

        val serv = retrofit.create(CepService::class.java)

        val call = serv.list(CepToSearch)

        val callback = object : Callback<Cep> {
            override fun onFailure(call: Call<Cep>, t: Throwable) {
                binding.progressBarCep.visibility = View.INVISIBLE
                Snackbar.make(
                        binding.cepContainer,
                        "Não foi possível pesquisar o CEP.",
                        Snackbar.LENGTH_LONG
                ).show()

                Log.e("ERRO-Retrofit", "Falha na conexão", t)
            }

            override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
                binding.progressBarCep.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    val cepInfo = response.body()
                    updateScreen(cepInfo)
                }
                else {
                    Snackbar.make(
                            binding.cepContainer,
                            "Não foi possível conectar-se ao servidor.",
                            Snackbar.LENGTH_LONG
                    ).show()

                    Log.e("ERRO-Retrofit", response.errorBody().toString())
                }
            }

        }

        call.enqueue(callback)
        binding.progressBarCep.visibility = View.VISIBLE
    }

    fun updateScreen(info: Cep?){

        if (info != null) {
            val text = """
            |CEP pesquisado: ${info.cep}
            |Logradouro: ${info.logradouro}
            |Complemento: ${info.complemento}
            |Bairro: ${info.bairro}
            |Localidade: ${info.localidade}
            |UF: ${info.uf}
            |DDD: ${info.ddd}
            |IBGE: ${info.ibge}
            |GIA: ${info.gia}
            |SIAFI: ${info.siafi}""".trimMargin("|")

            binding.txtCepResults.text = text
        }



    }
}