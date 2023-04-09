package com.nnapps.cryptogptapp.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CoinGecko(
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("current_price")
    val price: Double,
    @SerializedName("market_cap")
    val marketCap: Double,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage: Double,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double,
    @SerializedName("total_volume")
    val totalVolume: Double,
    @SerializedName("last_updated")
    val lastUpdated: String
)