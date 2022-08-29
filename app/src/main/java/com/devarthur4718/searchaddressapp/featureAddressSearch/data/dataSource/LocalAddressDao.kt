package com.devarthur4718.searchaddressapp.featureAddressSearch.data.dataSource

import androidx.room.Dao
import androidx.room.Query
import com.devarthur4718.searchaddressapp.featureAddressSearch.domain.model.LocalAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalAddressDao {

    @Query("SELECT * FROM LocalAddress")
    fun getAllAddress(): Flow<List<LocalAddress>>
}
