package com.target.targetcasestudy.api

import com.target.targetcasestudy.api.DealApi.Companion.BASE_URL
import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.data.DealResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DealApiRx {
    @GET("${BASE_URL}deals")
    fun retrieveDeals(): Single<DealResponse>

    @GET("${BASE_URL}deals/{dealId}")
    fun retrieveDeal(@Path("dealId") dealId: String): Single<Deal>
}
