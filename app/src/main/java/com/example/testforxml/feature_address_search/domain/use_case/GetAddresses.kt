package com.example.testforxml.feature_address_search.domain.use_case

import com.example.testforxml.feature_address_search.domain.model.LocalAddress
import com.example.testforxml.feature_address_search.domain.repository.LocalAddressRepository
import com.example.testforxml.feature_address_search.domain.util.AddressOrder
import com.example.testforxml.feature_address_search.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAddresses(
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
                            addresses.filter { (it.localName.contains(querry)) or (it.postalCode.contains(querry))}
                    }
                }
                is OrderType.Descending -> {
                    when (addressOrder) {
                        is AddressOrder.AddressPostalCode -> addresses.sortedByDescending { it.postalCode }
                        is AddressOrder.AddressName -> addresses.sortedByDescending { it.localName }
                        is AddressOrder.AddressQueryByName ->
                            addresses.filter { (it.localName.contains(querry)) or (it.postalCode.contains(querry))}
                    }
                }
            }
        }
    }
}
