package com.devarthur4718.searchaddressapp.featureAddressSearch.data.dataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalAddressDao {

    @Query("SELECT * FROM LocalAddress")
    suspend fun getAllAddress(): Flow<List<LocalAddress>>

    @Query("SELECT * FROM LocalAddress WHERE localName LIKE :searchQuery or postalCode LIKE :searchQuery")
    suspend fun searchAddress(searchQuery: String): List<LocalAddress>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(address: LocalAddress)
}
