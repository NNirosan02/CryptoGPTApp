package com.nnapps.cryptogptapp.data.datasource

import com.nnapps.cryptogptapp.domain.datasource.CryptoPriceDataSource
import com.nnapps.cryptogptapp.domain.model.CryptoPrice
import com.nnapps.cryptogptapp.data.remote.CoinGeckoApiService
import com.nnapps.cryptogptapp.domain.model.CoinDetails
import com.nnapps.cryptogptapp.domain.model.toCryptoPrice
import javax.inject.Inject

class CryptoPriceDataSourceImpl @Inject constructor(
    private val coinGeckoApiService: CoinGeckoApiService
) : CryptoPriceDataSource {

    override suspend fun getCoins(vsCurrency: String, order: String, perPage: Int): List<CryptoPrice> {
        val coinGeckoCoins = coinGeckoApiService.getCoins(vsCurrency, order, perPage)
        return coinGeckoCoins.map { it.toCryptoPrice() }
    }

    override suspend fun getCoinDetails(id: String): CoinDetails {
        return coinGeckoApiService.getCoinDetails(id)
    }
}