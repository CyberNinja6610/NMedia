package ru.netology.nmedia.helpres

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Number.formatToString(): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault());
    val number = this.toLong()
    symbols.decimalSeparator = '.'
    symbols.groupingSeparator = ' '

    return when {
        number >= 1_000_000 -> "${DecimalFormat("#.#", symbols).format(number / 1_000_000.0)}M"
        number >= 10_000 -> "${DecimalFormat("#", symbols).format(number / 1_000.0)}K"
        number >= 1_100 -> "${DecimalFormat("#.#", symbols).format(number / 1_000.0)}K"
        else -> DecimalFormat("#,###.#", symbols).format(this )
    }
}