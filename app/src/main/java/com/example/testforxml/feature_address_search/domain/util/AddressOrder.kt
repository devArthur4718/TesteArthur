package com.example.testforxml.feature_address_search.domain.util

sealed class AddressOrder(val orderType: OrderType) {
    class AddressName(orderType: OrderType) : AddressOrder(orderType)
    class AddressPostalCode(orderType: OrderType) : AddressOrder(orderType)
    class AddressQueryByName(orderType: OrderType, name: String) : AddressOrder(orderType)
}
