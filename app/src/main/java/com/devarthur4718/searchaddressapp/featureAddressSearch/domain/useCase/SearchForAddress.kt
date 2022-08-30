package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase

import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchForAddress @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<LocalAddress>>> = flow {
        if (query.isBlank()) {
            return@flow
        }
        try {
            emit(Resource.Loading())
            val results = repository.searchForAddress(query)
            emit(Resource.Success(results))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}
