package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository

import okhttp3.ResponseBody

interface RemoteAddressRepository {
    suspend fun getAddressesFromApi(): ResponseBody
}
