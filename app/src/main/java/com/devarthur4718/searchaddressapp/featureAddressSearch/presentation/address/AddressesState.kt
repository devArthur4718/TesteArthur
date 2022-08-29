package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.address

import okhttp3.ResponseBody

sealed class AddressState {
    object Loading : AddressState()
    data class onRemoteAddressFileReceived(val data: ResponseBody) : AddressState()
    data class Error(val message: String) : AddressState()
}
