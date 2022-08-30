package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase

import android.util.Log
import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDataFromLocalStorage @Inject constructor(
    private val repository: AddressRepository
) {

    operator fun invoke(): Flow<Resource<List<LocalAddress>>> = flow {
        try {
            emit(Resource.Loading())
            val results = repository.getAddressesFromLocalDatabase()
            emit(Resource.Success(results))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
            Log.e("Wtest", "Database Error: $e}")
        }
    }
}
