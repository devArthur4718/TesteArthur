package com.devarthur4718.searchaddressapp.featureAddressSearch.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.model.LocalAddress

@Database(
    entities = [LocalAddress::class],
    version = 1
)
abstract class LocalAddressDatabase : RoomDatabase() {
    abstract val localAddressDao: LocalAddressDao

    companion object {
        const val DATABASE_NAME = "address_db"
    }
}
