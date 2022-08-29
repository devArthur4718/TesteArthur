package com.example.testforxml.feature_address_search.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testforxml.feature_address_search.domain.model.LocalAddress

@Database(
    entities = [LocalAddress::class],
    version = 1
)
abstract class LocalAddressDatabase : RoomDatabase() {
    abstract val localAddressDao: LocalAddressDao
}
