package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository

import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import okhttp3.ResponseBody

interface RemoteAddressRepository {
    suspend fun getAddressesFromApi(): ResponseBody
    suspend fun getAddressesFromLocalDatabase(): List<LocalAddress>
    suspend fun saveAddresses(list: List<LocalAddress>)
}
