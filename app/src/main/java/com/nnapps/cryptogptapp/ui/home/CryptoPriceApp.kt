package com.nnapps.cryptogptapp.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nnapps.cryptogptapp.domain.model.CryptoPrice
import com.nnapps.cryptogptapp.ui.CryptoPriceViewModel
import com.nnapps.cryptogptapp.ui.formatAsCurrency
import com.nnapps.cryptogptapp.ui.theme.CryptoGPTAppTheme

@Composable
fun CryptoPriceApp() {
    MaterialTheme() {
        Surface(color = MaterialTheme.colors.background) {
            CryptoPriceScreen()
        }
    }
}

@Composable
fun CryptoPriceScreen(viewModel: CryptoPriceViewModel = hiltViewModel()) {
    val prices by viewModel.prices.observeAsState(emptyList())

    LaunchedEffect(viewModel) {
        viewModel.refreshPrices()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Crypto Prices",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(prices) { price ->
                var coinDetails by remember { mutableStateOf("") }
                var isExpanded by remember { mutableStateOf(false) }
                CryptoPriceItem(
                    price = price,
                    coinDetails = coinDetails,
                    isExpanded = isExpanded,
                    onCryptoClick = {
                        if (isExpanded) {
                            isExpanded = false
                            coinDetails = ""
                        } else {
                            viewModel.getCoinDetails(price.id) { details ->
                                coinDetails = details.description.en
                                isExpanded = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CryptoPriceItem(
    price: CryptoPrice,
    coinDetails: String,
    isExpanded: Boolean,
    onCryptoClick: () -> Unit
) {
    val transition = updateTransition(isExpanded, label = "")

    val expandIconRotation by transition.animateFloat(label = "") {
        if (it) 180f else 0f
    }

    val cardElevation by transition.animateDp(label = "") {
        if (it) 8.dp else 2.dp
    }

    val cardPadding by transition.animateDp(label = "") {
        if (it) 16.dp else 12.dp
    }

    val contentAlpha by transition.animateFloat(label = "") {
        if (it) 1f else 0f
    }

    Card(
        onClick = onCryptoClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = price.image,
                    contentDescription = "${price.name} (${price.symbol})",
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "${price.name} (${price.symbol})",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                )
                Text(
                    text = price.currentPrice.formatAsCurrency(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            if (isExpanded && !coinDetails.isNullOrEmpty()) {
                Text(
                    text = coinDetails.limitToParagraph() ?: "No description available",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CryptoPriceItemPreview() {
    val price = CryptoPrice(
        id = "bitcoin",
        name = "Bitcoin",
        symbol = "BTC",
        image = "",
        currentPrice = 55916.0,
        priceChangePercentage = -1.5,
        description = "Bitcoin is a decentralized digital currency, without a central bank or single administrator, that can be sent from user to user on the peer-to-peer bitcoin network without the need for intermediaries."
    )

    CryptoGPTAppTheme() {
        CryptoPriceItem(price = price, coinDetails = "Bitcoin is a decentralized digital currency, without a central bank or single administrator, that can be sent from user to user on the peer-to-peer bitcoin network without the need for intermediaries.", isExpanded = true, onCryptoClick = {})
    }
}

fun String.limitToParagraph(): String {
    val firstParagraph = substringBefore("\n")
    return if (firstParagraph != this) {
        "$firstParagraph"
    } else {
        firstParagraph
    }
}