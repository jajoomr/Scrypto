package com.mayurjajoo.screepto.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinsDAO {
    @Query("SELECT * FROM coins")
    suspend fun getCoinsData():List<CoinsData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins:List<CoinsData>)
}