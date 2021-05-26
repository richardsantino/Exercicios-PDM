package br.senac.list.services

import br.senac.list.model.Cep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {

    @GET("/ws/{CEP}/json/")
    fun list(@Path("CEP") CEP: String): Call<Cep>
}