package com.target.targetcasestudy.api

import android.util.Log
import com.target.targetcasestudy.data.Deal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DealRepository @Inject constructor(
    private val dealApiService: DealApiKtx
) {

    companion object {
        private const val TAG = "DealRepository"
    }

    suspend fun getDeals(): List<Deal> {
        return try {
            val deal = dealApiService.retrieveDeals().products
            deal

        } catch (e: Exception) {
            Log.e(TAG, "Error ${e.message}")
            emptyList()
        }
    }

    suspend fun getDealById(id: String): Deal? {
        return try {
            dealApiService.retrieveDeal(id)
        } catch (e: Exception) {
            Log.e(TAG, "error: ${e.message}")
            null
        }
    }
}


