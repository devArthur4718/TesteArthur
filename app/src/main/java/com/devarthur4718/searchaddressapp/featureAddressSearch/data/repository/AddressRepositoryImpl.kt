package com.devarthur4718.searchaddressapp.featureAddressSearch.data.repository

import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.LocalAddressDao
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.remote.GitHubApi
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.AddressRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val api: GitHubApi,
    private val dao: LocalAddressDao
) : AddressRepository {

    override suspend fun getAddressesFromApi(): ResponseBody {
        return api.getPostalCodes()
    }

    override suspend fun getAddressesFromLocalDatabase(): List<LocalAddress> {
        return dao.getAllAddressFromRemote()
    }

    override suspend fun saveAddresses(list: List<LocalAddress>) {
        return dao.insertAddress(list)
    }

    override suspend fun searchForAddress(queryString: String): List<LocalAddress> {
        return dao.searchAddress(queryString.toUpperCase())
    }
}
