package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.util

sealed class AddressOrder(val orderType: OrderType) {
    class AddressName(orderType: OrderType) : AddressOrder(orderType)
    class AddressPostalCode(orderType: OrderType) : AddressOrder(orderType)
    class AddressQueryByName(orderType: OrderType, name: String) : AddressOrder(orderType)
}
