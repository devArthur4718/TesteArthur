package com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LocalAddress(
    val localName: String,
    val postalCode: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)