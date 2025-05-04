package com.target.targetcasestudy.api

import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.data.DealResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DealApiKtx {

    companion object {
        const val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"
    }

    @GET("deals")
    suspend fun retrieveDeals(): DealResponse

    @GET("deals/{dealId}")
    suspend fun retrieveDeal(@Path("dealId") dealId: String): Deal
}
