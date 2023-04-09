package com.nnapps.cryptogptapp.domain.model

import com.nnapps.cryptogptapp.data.model.CoinGecko

data class CryptoPrice(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val description: String?,
    val priceChangePercentage: Double,
    val totalVolume: Double? = null,
    val circulatingSupply: Double? = null,
    val lastUpdated: String? = null,
    val marketCap: Double? = null,
)

fun CoinGecko.toCryptoPrice(): CryptoPrice {
    return CryptoPrice(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        image = this.image,
        description = this.description,
        currentPrice = this.price,
        priceChangePercentage = this.priceChangePercentage,
        marketCap = this.marketCap,
        totalVolume = this.totalVolume,
        circulatingSupply = this.circulatingSupply,
        lastUpdated = this.lastUpdated
    )
}