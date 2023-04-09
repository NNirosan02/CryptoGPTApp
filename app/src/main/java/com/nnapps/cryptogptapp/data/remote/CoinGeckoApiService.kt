package com.nnapps.cryptogptapp.data.remote

import com.nnapps.cryptogptapp.data.model.CoinGecko
import com.nnapps.cryptogptapp.domain.model.CoinDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApiService {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") currency: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int
    ): List<CoinGecko>

    @GET("coins/{id}")
    suspend fun getCoinDetails(@Path("id") coinId: String): CoinDetails

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
    }
}