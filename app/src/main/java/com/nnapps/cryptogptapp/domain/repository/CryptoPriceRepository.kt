package com.nnapps.cryptogptapp.domain.repository

import com.nnapps.cryptogptapp.domain.model.CoinDetails
import com.nnapps.cryptogptapp.domain.model.CryptoPrice

interface CryptoPriceRepository {
    suspend fun getCoins(vsCurrency: String, order: String, perPage: Int, page: Int): List<CryptoPrice>
    suspend fun getCoinDetails(id: String): CoinDetails
}