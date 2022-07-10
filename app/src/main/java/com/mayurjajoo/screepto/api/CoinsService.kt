package com.mayurjajoo.screepto.api

import com.mayurjajoo.screepto.model.CoinList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsService {

    @GET(".")
    suspend fun getCoinsData(
        @Query("start") start: Int,
        @Query("end") end: Int
    ): Response<CoinList>
}