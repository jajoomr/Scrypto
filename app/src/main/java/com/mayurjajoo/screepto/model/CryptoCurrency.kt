package com.mayurjajoo.screepto.model

data class CryptoCurrency(
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val quotes: List<Quote>,
)