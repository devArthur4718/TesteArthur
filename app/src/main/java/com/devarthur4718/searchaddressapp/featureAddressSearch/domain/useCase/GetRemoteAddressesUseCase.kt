package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase

import android.util.Log
import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.core.StandardErrorMessages
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.RemoteAddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRemoteAddressesUseCase @Inject constructor(
    private val remoteApi: RemoteAddressRepository
) {
    operator fun invoke(): Flow<Resource<ResponseBody>> = flow {
        try {
            emit(Resource.Loading())
            val addressFile = remoteApi.getAddressesFromApi()
            emit(Resource.Success(addressFile))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: StandardErrorMessages.HTTP_EXCEPTION_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(StandardErrorMessages.NO_NETWORK_ERROR))
        }
    }

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

    fun getDataFromDatabase(): Flow<Resource<List<LocalAddress>>> = flow {
        try {
            emit(Resource.Loading())
            val results = remoteApi.getAddressesFromLocalDatabase()
            emit(Resource.Success(results))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
            Log.e("Wtest", "Database Error: $e}")
        }
    }
}
