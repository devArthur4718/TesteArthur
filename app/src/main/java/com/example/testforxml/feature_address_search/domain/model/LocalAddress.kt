package com.example.testforxml.feature_address_search.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LocalAddress(
    val localName: String,
    @PrimaryKey
    val postalCode: String
)