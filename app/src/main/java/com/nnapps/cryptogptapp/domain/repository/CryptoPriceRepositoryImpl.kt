package com.nnapps.cryptogptapp.domain.repository

import android.util.Log
import com.nnapps.cryptogptapp.domain.datasource.CryptoPriceDataSource
import com.nnapps.cryptogptapp.domain.model.CoinDetails
import com.nnapps.cryptogptapp.domain.model.CryptoPrice
import javax.inject.Inject

class CryptoPriceRepositoryImpl @Inject constructor(
    private val cryptoPriceDataSource: CryptoPriceDataSource
    ) : CryptoPriceRepository {

    override suspend fun getCoins(vsCurrency: String, order: String, perPage: Int, page: Int): List<CryptoPrice> {
        val coins = cryptoPriceDataSource.getCoins(vsCurrency, order, perPage)
        return coins.map { coin ->
            CryptoPrice(
                id = coin.id,
                name = coin.name,
                description = coin.description,
                symbol = coin.symbol,
                image = coin.image,
                currentPrice = coin.currentPrice,
                priceChangePercentage = coin.priceChangePercentage
            )
        }
    }

    override suspend fun getCoinDetails(id: String): CoinDetails {
        return cryptoPriceDataSource.getCoinDetails(id)
    }
}
