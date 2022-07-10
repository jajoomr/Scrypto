package com.mayurjajoo.screepto.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for detailed quote of crypto coin
 */
data class Quote(
    val quoteId:Int,
    val name: String,
    val percentChange1h: Double,
    val percentChange24h: Double,
    val percentChange30d: Double,
    val percentChange60d: Double,
    val percentChange7d: Double,
    val percentChange90d: Double,
    val price: Double,
)