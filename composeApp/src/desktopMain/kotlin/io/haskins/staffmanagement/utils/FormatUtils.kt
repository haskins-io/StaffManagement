package io.haskins.staffmanagement.utils

import java.text.NumberFormat
import java.util.*

class FormatUtils {

    companion object {

        private const val CURRENCY_US_DOLLARS: String = "USD"
        private const val CURRENCY_GBP: String = "GBP"

        private const val LANGUAGE_ENGLISH: String = "EN"

        private const val COUNTRY_US: String = "US"
        private const val COUNTRY_UK: String = "UK"
        private const val COUNTRY_CANADA: String = "CA"
        private const val COUNTRY_AUSTRALIA: String = "AU"

        fun formatCurrency(amount: Int): String =
            currencyInLocale(CURRENCY_GBP, LANGUAGE_ENGLISH, COUNTRY_UK).format(amount)

        fun formatCurrency(amount: Float): String =
            currencyInLocale(CURRENCY_GBP, LANGUAGE_ENGLISH, COUNTRY_UK).format(amount)

        private fun formatCurrency(amount: Float, currency: String, language: String, country: String) =
            currencyInLocale(currency, language, country).format(amount)

        private fun currencyInLocale(
            currencyCode: String,
            language: String,
            country: String = "",
            variant: String = ""
        ): NumberFormat =
            Locale(language, country, variant).let {
                NumberFormat.getCurrencyInstance(it).apply {
                    currency = Currency.getInstance(currencyCode)
                }
            }
        
    }
}