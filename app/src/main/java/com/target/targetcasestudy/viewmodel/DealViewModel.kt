package com.target.targetcasestudy.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.api.DealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealViewModel @Inject constructor(
    private val dealRepository: DealRepository
) : ViewModel() {

    companion object {
        private const val TAG = "DealViewModel"
    }

    private val _dealsState = MutableStateFlow<DealsUiState>(DealsUiState.Loading)
    val dealsState: StateFlow<DealsUiState> = _dealsState

    private val _selectedDealState = MutableStateFlow<DealDetailUiState>(DealDetailUiState.Loading)
    val selectedDealState: StateFlow<DealDetailUiState> = _selectedDealState

    init {
        loadDeals()
    }

    fun loadDeals() {
        viewModelScope.launch {
            _dealsState.value = DealsUiState.Loading
            try {
                val deals = dealRepository.getDeals()
                Log.i(TAG, "All deals: ${deals.size}")
                _dealsState.value = if (deals.isNotEmpty()) {
                    DealsUiState.Success(deals)
                } else {
                    DealsUiState.Empty
                }
            } catch (e: Exception) {
                Log.e(TAG, "DetailUiState Error: ${e.message}")
                _dealsState.value = DealsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadDealDetails(dealId: String) {
        viewModelScope.launch {
            _selectedDealState.value = DealDetailUiState.Loading
            try {
                val deal = dealRepository.getDealById(dealId)
                _selectedDealState.value = if (deal != null) {
                    DealDetailUiState.Success(deal)
                } else {
                    DealDetailUiState.Error("Deal not found")
                }
            } catch (e: Exception) {
                Log.e(TAG, "DealDetailUiState Error: ${e.message}")
                _selectedDealState.value = DealDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class DealsUiState {
    data object Loading : DealsUiState()
    data class Success(val deals: List<Deal>) : DealsUiState()
    data object Empty : DealsUiState()
    data class Error(val message: String) : DealsUiState()
}

sealed class DealDetailUiState {
    data object Loading : DealDetailUiState()
    data class Success(val deal: Deal) : DealDetailUiState()
    data class Error(val message: String) : DealDetailUiState()
}