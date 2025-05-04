package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

data class Deal(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("aisle") val aisle: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("regular_price") val regularPrice: Price,
    @SerializedName("sale_price") val salePrice: Price? = null,
    @SerializedName("fulfillment") val fulfillment: String? = null,
    @SerializedName("availability") val availability: String? = null
)
