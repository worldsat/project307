package com.uilover.project307.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uilover.project307.data.MockData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            userProfile = MockData.sampleUserProfile,
            orderStatus = MockData.sampleOrderStatus,
            activeDelivery = MockData.sampleActiveDelivery,
            menuItems = MockData.profileMenuItems,
            cartItemCount = 2
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnTopUpWallet -> {
                _uiState.update {
                    val updatedProfile = it.userProfile.copy(
                        walletBalance = it.userProfile.walletBalance + 50.00
                    )
                    it.copy(userProfile = updatedProfile)
                }
                showToast("Added $50.00 to Wallet!")
            }

            is ProfileAction.OnRedeemRewards -> {
                showToast("Rewards Catalog opened")
            }

            is ProfileAction.OnViewCoupons -> {
                showToast("3 Coupons available: FALL1403, WELCOME10, FREESHIP")
            }

            is ProfileAction.OnOrderStatusClick -> {
                showToast("Viewing ${action.statusName} orders")
            }

            is ProfileAction.OnViewAllOrdersClick -> {
                showToast("All orders history")
            }

            is ProfileAction.OnTrackDeliveryClick -> {
                showToast("Tracking Order #${_uiState.value.activeDelivery.orderId}")
            }

            is ProfileAction.OnMenuItemClick -> {
                val item = _uiState.value.menuItems.find { it.id == action.itemId }
                showToast("Opened ${item?.title ?: "Menu"}")
            }

            is ProfileAction.OnEditProfileClick -> {
                showToast("Edit Profile dialog")
            }

            is ProfileAction.OnNotificationsClick -> {
                showToast("No new notifications")
            }

            is ProfileAction.OnLogoutClick -> {
                showToast("Logged out successfully")
            }

            is ProfileAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTab = action.tab) }
            }

            is ProfileAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }
        }
    }

    private fun showToast(message: String) {
        toastJob?.cancel()
        _uiState.update { it.copy(toastMessage = message) }
        toastJob = viewModelScope.launch {
            delay(2200)
            _uiState.update { it.copy(toastMessage = null) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        toastJob?.cancel()
    }
}
