package com.uilover.project307.ui.cart

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uilover.project307.R
import com.uilover.project307.model.CartItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val initialCartItems = listOf(
        CartItem(
            id = "item-1",
            productId = "deal_3",
            title = "Pro Sound Wireless Headphones",
            imageRes = R.drawable.product_headphones_main,
            colorName = "Color: Matte Black",
            colorHex = Color(0xFF1E293B),
            tagText = "18-month warranty",
            isTagPrimary = false,
            originalPrice = 70.00,
            currentPrice = 59.00,
            quantity = 1
        ),
        CartItem(
            id = "item-2",
            productId = "deal_2",
            title = "Smart Watch Fit Pro",
            imageRes = R.drawable.product_smartwatch,
            colorName = "Strap: Graphite Silver",
            colorHex = Color(0xFFCBD5E1),
            tagText = "Best Seller",
            isTagPrimary = true,
            originalPrice = 55.00,
            currentPrice = 45.00,
            quantity = 2
        ),
        CartItem(
            id = "item-3",
            productId = "prod_case",
            title = "Silicone Protective Case",
            imageRes = R.drawable.product_case_peach,
            colorName = "Color: Pastel Peach",
            colorHex = Color(0xFFFFB3B6),
            tagText = "Shockproof",
            isTagPrimary = false,
            originalPrice = 12.00,
            currentPrice = 9.00,
            quantity = 1
        )
    )

    private val _uiState = MutableStateFlow(CartUiState(items = initialCartItems))
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null
    private var checkoutJob: Job? = null

    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.OnQuantityChanged -> {
                _uiState.update { state ->
                    val updatedItems = state.items.map { item ->
                        if (item.id == action.itemId) {
                            val newQuantity = (item.quantity + action.delta).coerceAtLeast(1)
                            item.copy(quantity = newQuantity)
                        } else {
                            item
                        }
                    }
                    state.copy(items = updatedItems)
                }
            }

            is CartAction.OnRemoveItem -> {
                _uiState.update { state ->
                    val updatedItems = state.items.filterNot { it.id == action.itemId }
                    state.copy(items = updatedItems)
                }
                showToast("Item removed from cart")
            }

            is CartAction.OnClearCart -> {
                _uiState.update { state ->
                    state.copy(
                        items = emptyList(),
                        appliedCoupon = null,
                        couponDiscount = 0.0,
                        couponFeedback = null
                    )
                }
                showToast("Cart cleared")
            }

            is CartAction.OnCouponInputChanged -> {
                _uiState.update { it.copy(couponInput = action.code) }
            }

            is CartAction.OnApplyCoupon -> {
                val input = _uiState.value.couponInput.trim().uppercase()
                when {
                    input.isEmpty() -> {
                        _uiState.update {
                            it.copy(
                                couponFeedback = "Please enter a promo code.",
                                isCouponError = true
                            )
                        }
                    }
                    input in listOf("NOROOZ", "FALL1403", "OFF10", "SAVE10") -> {
                        _uiState.update {
                            it.copy(
                                appliedCoupon = input,
                                couponDiscount = 10.00,
                                couponFeedback = "$10.00 promo discount successfully applied.",
                                isCouponError = false
                            )
                        }
                        showToast("Promo discount of $10.00 applied!")
                    }
                    else -> {
                        _uiState.update {
                            it.copy(
                                couponFeedback = "Invalid or expired promo code.",
                                isCouponError = true
                            )
                        }
                    }
                }
            }

            is CartAction.OnProceedToCheckout -> {
                if (_uiState.value.items.isEmpty()) {
                    showToast("Your cart is empty!")
                    return
                }
                _uiState.update { it.copy(isCheckingOut = true) }
                checkoutJob?.cancel()
                checkoutJob = viewModelScope.launch {
                    delay(1200)
                    _uiState.update { it.copy(isCheckingOut = false) }
                    showToast("Order placed successfully! Thank you.")
                }
            }

            is CartAction.OnNotificationsClick -> {
                showToast("No new notifications")
            }

            is CartAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTab = action.tab) }
            }

            is CartAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }

            else -> {
                // Other navigation-related actions are handled at NavHost level
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
        checkoutJob?.cancel()
    }
}
