package com.example.testforxml.feature_address_search.data.data_source

import androidx.room.Dao
import androidx.room.Query
import com.example.testforxml.feature_address_search.domain.model.LocalAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalAddressDao {

    @Query("SELECT * FROM LocalAddress")
    fun getAllAddress(): Flow<List<LocalAddress>>
}
