package com.nnapps.cryptogptapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nnapps.cryptogptapp.data.model.CoinGecko
import com.nnapps.cryptogptapp.domain.model.CoinDetails
import com.nnapps.cryptogptapp.domain.model.CryptoPrice
import com.nnapps.cryptogptapp.domain.repository.CryptoPriceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoPriceViewModel @Inject constructor(
    private val repository: CryptoPriceRepository
) : ViewModel() {

    private val _prices = MutableLiveData<List<CryptoPrice>>()
    val prices: LiveData<List<CryptoPrice>> = _prices

    private val _selectedCoin = MutableLiveData<CoinDetails>()
    val selectedCoin: LiveData<CoinDetails> = _selectedCoin

    fun refreshPrices(vsCurrency: String = "usd", order: String = "market_cap_desc", perPage: Int = 100, page: Int = 1) {
        viewModelScope.launch {
            val coins = repository.getCoins(vsCurrency, order, perPage, page)
            _prices.value = coins.map { coin ->
                CryptoPrice(
                    id = coin.id,
                    name = coin.name,
                    symbol = coin.symbol,
                    image = coin.image,
                    currentPrice = coin.currentPrice,
                    priceChangePercentage = coin.priceChangePercentage,
                    description = coin.description
                )
            }
        }
    }

    fun getCoinDetails(id: String, callback: (CoinDetails) -> Unit) {
        viewModelScope.launch {
            println("COIN ID IS $id")
            val coin = repository.getCoinDetails(id)
            callback(coin)
        }
    }
}