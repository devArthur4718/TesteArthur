package com.example.testforxml.feature_address_search.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
