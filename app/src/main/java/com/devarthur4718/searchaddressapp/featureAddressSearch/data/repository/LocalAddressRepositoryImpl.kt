package com.devarthur4718.searchaddressapp.featureAddressSearch.data.repository

import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.dataSource.LocalAddressDao
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.model.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.LocalAddressRepository
import kotlinx.coroutines.flow.Flow

class LocalAddressRepositoryImpl(
    private val dao: LocalAddressDao
) : LocalAddressRepository {
    override fun getAllAddress(): Flow<Resource<List<LocalAddress>>> {
        return dao.getAllAddress()
    }

    override suspend fun searchAddress(querry: String): Flow<List<LocalAddress>> {
        return dao.searchAddress(querry)
    }
}
