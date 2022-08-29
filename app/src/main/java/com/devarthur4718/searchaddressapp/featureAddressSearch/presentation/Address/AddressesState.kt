package com.devarthur4718.searchaddressapp.featureAddressSearch.presentation.Address

import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.model.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.util.AddressOrder
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.util.OrderType

data class AddressesState(
    val addresses: List<LocalAddress> = emptyList(),
    val addressOrder: AddressOrder = AddressOrder.AddressName(OrderType.Descending)
)
