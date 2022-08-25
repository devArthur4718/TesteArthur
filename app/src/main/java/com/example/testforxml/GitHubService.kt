package com.example.testforxml

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GitHubService {

    @GET("centraldedados/codigos_postais/master/data/codigos_postais.csv")
    fun getCodigosPostais(): Call<ResponseBody>

    companion object {

        val BASE_URL = "https://raw.githubusercontent.com/"

        fun create(): GitHubService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(GitHubService::class.java)
        }
    }
}
