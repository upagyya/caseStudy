package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("amount_in_cents") val amountInCents: Int,
    @SerializedName("currency_symbol") val currencySymbol: String? = null,
    @SerializedName("display_string") val displayString: String? = null
)
