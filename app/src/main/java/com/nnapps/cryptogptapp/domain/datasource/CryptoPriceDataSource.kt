package com.nnapps.cryptogptapp.domain.datasource

import com.nnapps.cryptogptapp.domain.model.CoinDetails
import com.nnapps.cryptogptapp.domain.model.CryptoPrice

interface CryptoPriceDataSource {
    suspend fun getCoins(vsCurrency: String, order: String, perPage: Int): List<CryptoPrice>
    suspend fun getCoinDetails(id: String): CoinDetails
}