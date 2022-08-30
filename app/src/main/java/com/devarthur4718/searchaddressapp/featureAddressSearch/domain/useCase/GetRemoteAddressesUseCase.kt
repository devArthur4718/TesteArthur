package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase

import com.devarthur4718.searchaddressapp.core.Resource
import com.devarthur4718.searchaddressapp.core.StandardErrorMessages
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.LocalAddressRepository
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.RemoteAddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRemoteAddressesUseCase @Inject constructor(
    private val remoteApi: RemoteAddressRepository,
    private val repository: LocalAddressRepository
) {
    operator fun invoke(): Flow<Resource<ResponseBody>> = flow {
        try {
            emit(Resource.Loading())
            val addressFile = remoteApi.getAddresesFromRemote()
            emit(Resource.Success(addressFile))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: StandardErrorMessages.HTTP_EXCEPTION_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(StandardErrorMessages.NO_NETWORK_ERROR))
        }
    }
}
