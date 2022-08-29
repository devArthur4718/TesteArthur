package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository

import okhttp3.ResponseBody

interface GithubRepository {

    suspend fun getAddresesFromRemote(): ResponseBody
}