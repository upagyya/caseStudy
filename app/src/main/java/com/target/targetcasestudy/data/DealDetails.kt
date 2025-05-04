package com.target.targetcasestudy.data

data class DealDetails(
    val id: String,
    val aisle: String,
    val description: String,
    val image_url: String,
    val price: String,
    val sale_price: String,
    val title: String,
    val additional_info: List<String>? = null
)
