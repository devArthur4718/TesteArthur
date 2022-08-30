package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase

import android.util.Log
import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveDataIntoRoomUseCase @Inject constructor(
    private val remoteApi: AddressRepository
) {
    fun saveDataIntoDatabase(addressList: MutableList<LocalAddress>): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            remoteApi.saveAddresses(addressList)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
            Log.e("Wtest", "Database Error: $e}")
        }
    }
}
