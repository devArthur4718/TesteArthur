package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository

import com.devarthur4718.searchaddressapp.featureAddressSearch.data.remote.GitHubApi
import okhttp3.ResponseBody
import javax.inject.Inject

class RemoteAddressRepositoryImpl @Inject constructor(
    private val api: GitHubApi
) : RemoteAddressRepository {

    override suspend fun getAddresesFromRemote(): ResponseBody {
        return api.getPostalCodes()
    }
}
