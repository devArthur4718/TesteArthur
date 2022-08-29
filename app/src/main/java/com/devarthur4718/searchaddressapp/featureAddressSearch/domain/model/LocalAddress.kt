package com.devarthur4718.searchaddressapp.featureAddressSearch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LocalAddress(
    val localName: String,
    @PrimaryKey
    val postalCode: String
)