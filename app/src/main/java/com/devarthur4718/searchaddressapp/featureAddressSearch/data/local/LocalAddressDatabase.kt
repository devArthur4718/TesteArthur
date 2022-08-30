package com.devarthur4718.searchaddressapp.featureAddressSearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress

@Database(
    entities = [LocalAddress::class],
    version = 6
)
abstract class LocalAddressDatabase : RoomDatabase() {
    abstract val dao: LocalAddressDao
}
