package com.example.testforxml.feature_address_search.data.repository

import com.example.testforxml.feature_address_search.data.data_source.LocalAddressDao
import com.example.testforxml.feature_address_search.domain.model.LocalAddress
import com.example.testforxml.feature_address_search.domain.repository.LocalAddressRepository
import kotlinx.coroutines.flow.Flow

class LocalAddressRepositoryImpl(private val dao: LocalAddressDao) : LocalAddressRepository {
    override fun getAllAddress(): Flow<List<LocalAddress>> {
        return dao.getAllAddress()
    }
}
