package com.devarthur4718.searchaddressapp.featureAddressSearch.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET

interface GitHubApi {
    @GET("centraldedados/codigos_postais/master/data/codigos_postais.csv")
    suspend fun getPostalCodes(): ResponseBody
}
