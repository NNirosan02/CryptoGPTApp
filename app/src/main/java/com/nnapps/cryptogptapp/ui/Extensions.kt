package com.nnapps.cryptogptapp.ui


import java.text.NumberFormat
import java.util.*

fun Double.formatAsCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}