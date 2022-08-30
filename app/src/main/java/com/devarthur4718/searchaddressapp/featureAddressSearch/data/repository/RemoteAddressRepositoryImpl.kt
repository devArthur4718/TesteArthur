package com.devarthur4718.searchaddressapp.featureAddressSearch.data.repository

import com.devarthur4718.searchaddressapp.featureAddressSearch.data.remote.GitHubApi
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.RemoteAddressRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class RemoteAddressRepositoryImpl @Inject constructor(
    private val api: GitHubApi
) : RemoteAddressRepository {

    override suspend fun getAddressesFromApi(): ResponseBody {
        return api.getPostalCodes()
    }

    // TODO : Save data into room
}
