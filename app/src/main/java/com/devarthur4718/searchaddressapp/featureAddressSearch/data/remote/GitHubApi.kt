package com.devarthur4718.searchaddressapp.featureAddressSearch.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GitHubApi {

    @GET("centraldedados/codigos_postais/master/data/codigos_postais.csv")
    fun getCodigosPostais(): Call<ResponseBody>

    @GET("centraldedados/codigos_postais/master/data/codigos_postais.csv")
    suspend fun getPostalCodes(): ResponseBody

    companion object {

        val BASE_URL = "https://raw.githubusercontent.com/"

        fun create(): GitHubApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(GitHubApi::class.java)
        }
    }
}
