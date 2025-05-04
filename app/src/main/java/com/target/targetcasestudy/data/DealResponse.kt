package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

data class DealResponse(
  @SerializedName("products")
  val products: List<Deal> = emptyList()
)