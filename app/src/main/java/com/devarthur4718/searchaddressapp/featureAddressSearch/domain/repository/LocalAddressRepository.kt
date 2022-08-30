package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository

import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.model.LocalAddress
import kotlinx.coroutines.flow.Flow

interface LocalAddressRepository {
    fun getAllAddress(): Flow<Resource<List<LocalAddress>>>
    suspend fun searchAddress(querry: String): List<LocalAddress>
}
