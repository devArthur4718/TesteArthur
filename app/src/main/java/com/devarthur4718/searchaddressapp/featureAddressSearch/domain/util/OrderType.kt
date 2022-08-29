package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
