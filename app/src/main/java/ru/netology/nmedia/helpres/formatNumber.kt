package ru.netology.nmedia.dto

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Int.formatToString(): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault());
    symbols.decimalSeparator = '.';//simple space
    symbols.groupingSeparator = ' '

    return when {
        this >= 1_000_000 -> "${DecimalFormat("#.#", symbols).format(this / 1_000_000.0)}M"
        this >= 10_000 -> "${DecimalFormat("#", symbols).format(this / 1_000.0)}K"
        this >= 1_100 -> "${DecimalFormat("#.#", symbols).format(this / 1_000.0)}K"
        else -> DecimalFormat("#,###.#", symbols).format(this )
    }
}