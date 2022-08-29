package com.example.testforxml.feature_address_search.domain.repository

import com.example.testforxml.feature_address_search.domain.model.LocalAddress
import kotlinx.coroutines.flow.Flow

interface LocalAddressRepository {

    fun getAllAddress(): Flow<List<LocalAddress>>
}
