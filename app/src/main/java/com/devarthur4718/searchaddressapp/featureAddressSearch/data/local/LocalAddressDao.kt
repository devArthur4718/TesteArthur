package com.devarthur4718.searchaddressapp.featureAddressSearch.data.local

import androidx.room.*
import com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity.LocalAddress

@Dao
interface LocalAddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(list: List<LocalAddress>)

    @Query("DELETE FROM LocalAddress")
    suspend fun deleteAllAddress()

    @Query("SELECT * FROM LocalAddress WHERE localName LIKE :searchQuery or postalCode LIKE :searchQuery")
    fun searchAddress(searchQuery: String): List<LocalAddress>
}
