package com.uilover.project307.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uilover.project307.model.BottomNavTab
import com.uilover.project307.ui.cart.components.CartHeaderSection
import com.uilover.project307.ui.cart.components.CartItemCard
import com.uilover.project307.ui.cart.components.CartTopBar
import com.uilover.project307.ui.cart.components.EmptyCartView
import com.uilover.project307.ui.cart.components.FreeDeliveryBanner
import com.uilover.project307.ui.cart.components.OrderSummaryCard
import com.uilover.project307.ui.cart.components.PromoCouponSection
import com.uilover.project307.ui.cart.components.StickyCheckoutActionBar
import com.uilover.project307.ui.home.components.CartFeedbackToast
import com.uilover.project307.ui.home.components.StoreBottomNavBar

@Composable
fun CartScreen(
    uiState: CartUiState,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            CartTopBar(
                onNotificationsClick = { onAction(CartAction.OnNotificationsClick) }
            )
        },
        bottomBar = {
            StoreBottomNavBar(
                selectedTab = uiState.selectedTab,
                cartItemCount = uiState.totalUniqueItems,
                onTabSelected = { tab -> onAction(CartAction.OnTabSelected(tab)) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Top spacing
                item(key = "cart_top_spacer") {
                    Spacer(modifier = Modifier.height(2.dp))
                }

                // 1. Free Delivery Banner
                item(key = "free_delivery_banner") {
                    FreeDeliveryBanner()
                }

                // 2. Cart Header with Total Count & Clear All Action
                item(key = "cart_header") {
                    CartHeaderSection(
                        totalItems = uiState.totalUniqueItems,
                        onClearCart = { onAction(CartAction.OnClearCart) }
                    )
                }

                // 3. Cart Items List / Empty State
                if (uiState.items.isEmpty()) {
                    item(key = "empty_cart_state") {
                        EmptyCartView(
                            onStartShopping = { onAction(CartAction.OnStartShoppingClick) }
                        )
                    }
                } else {
                    items(
                        items = uiState.items,
                        key = { it.id },
                        contentType = { "cart_item" }
                    ) { item ->
                        CartItemCard(
                            item = item,
                            onQuantityChanged = { id, delta ->
                                onAction(CartAction.OnQuantityChanged(id, delta))
                            },
                            onRemoveItem = { id ->
                                onAction(CartAction.OnRemoveItem(id))
                            },
                            onProductClick = { productId ->
                                onAction(CartAction.OnProductClick(productId))
                            }
                        )
                    }

                    // 4. Promo Code & Gift Card Input Section
                    item(key = "promo_code_section") {
                        PromoCouponSection(
                            couponInput = uiState.couponInput,
                            onCouponInputChanged = { onAction(CartAction.OnCouponInputChanged(it)) },
                            onApplyCoupon = { onAction(CartAction.OnApplyCoupon) },
                            appliedCoupon = uiState.appliedCoupon,
                            couponFeedback = uiState.couponFeedback,
                            isCouponError = uiState.isCouponError
                        )
                    }

                    // 5. Order Financial Summary
                    item(key = "order_summary_section") {
                        OrderSummaryCard(
                            totalUnits = uiState.totalItemUnits,
                            subtotal = uiState.subtotal,
                            discountSavings = uiState.discountSavings,
                            appliedCoupon = uiState.appliedCoupon,
                            couponDiscount = uiState.couponDiscount,
                            totalPayable = uiState.totalPayable
                        )
                    }

                    // Bottom Spacer to prevent scrolling behind sticky checkout bar
                    item(key = "cart_bottom_padding") {
                        Spacer(modifier = Modifier.height(76.dp))
                    }
                }
            }

            // 6. Floating Sticky Checkout Action Bar
            if (uiState.items.isNotEmpty()) {
                StickyCheckoutActionBar(
                    totalPayable = uiState.totalPayable,
                    isCheckingOut = uiState.isCheckingOut,
                    onProceedToCheckout = { onAction(CartAction.OnProceedToCheckout) },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            // Interactive Feedback Toast
            CartFeedbackToast(
                message = uiState.toastMessage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = if (uiState.items.isNotEmpty()) 72.dp else 16.dp)
            )
        }
    }
}
