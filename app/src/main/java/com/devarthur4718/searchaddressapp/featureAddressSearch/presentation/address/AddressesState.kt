package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.address

import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import okhttp3.ResponseBody

sealed class AddressState {
    object Loading : AddressState()
    data class OnRemoteAddressFileReceived(val data: ResponseBody) : AddressState()
    data class OnAddressesFetchedFromLocal(val list: List<LocalAddress>) : AddressState()
    data class OnQueryFinished(val querryList: List<LocalAddress>) : AddressState()
    object OnDataSaved : AddressState()
    data class Error(val message: String) : AddressState()
}
