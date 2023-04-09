package com.nnapps.cryptogptapp.domain.model


data class CoinDetails(
    val id: String,
    val name: String,
    val description: CoinDetailsDescription,
    val marketData: MarketData
)
data class MarketData(
    val currentPrice: Map<String, Double>,
    val totalVolume: Map<String, Double>,
    val marketCap: Map<String, Double>,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double
)

data class CoinDetailsDescription(
    val en: String
)