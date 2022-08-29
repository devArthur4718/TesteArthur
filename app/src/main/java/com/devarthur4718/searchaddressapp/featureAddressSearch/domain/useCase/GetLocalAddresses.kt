package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.useCase

import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.model.LocalAddress
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.repository.LocalAddressRepository
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.util.AddressOrder
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLocalAddresses(
    private val repository: LocalAddressRepository
) {

    operator fun invoke(
        addressOrder: AddressOrder = AddressOrder.AddressPostalCode(OrderType.Descending),
        querry: String
    ): Flow<List<LocalAddress>> {
        return repository.getAllAddress().map { addresses ->
            when (addressOrder.orderType) {
                is OrderType.Ascending -> {
                    when (addressOrder) {
                        is AddressOrder.AddressPostalCode -> addresses.sortedBy { it.postalCode }
                        is AddressOrder.AddressName -> addresses.sortedBy { it.localName }
                        is AddressOrder.AddressQueryByName ->
                            addresses.filter { (it.localName.contains(querry)) or (it.postalCode.contains(querry)) }
                    }
                }
                is OrderType.Descending -> {
                    when (addressOrder) {
                        is AddressOrder.AddressPostalCode -> addresses.sortedByDescending { it.postalCode }
                        is AddressOrder.AddressName -> addresses.sortedByDescending { it.localName }
                        is AddressOrder.AddressQueryByName ->
                            addresses.filter { (it.localName.contains(querry)) or (it.postalCode.contains(querry)) }
                    }
                }
            }
        }
    }
}
