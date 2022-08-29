package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository

import com.devarthur4718.searchaddressapp.featureAddressSearch.data.remote.GitHubApi
import okhttp3.ResponseBody
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val api: GitHubApi
) : GithubRepository {

    override suspend fun getAddresesFromRemote(): ResponseBody {
        return api.getPostalCodes()
    }
}
