package com.uilover.project307.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uilover.project307.ui.home.components.CartFeedbackToast
import com.uilover.project307.ui.home.components.StoreBottomNavBar
import com.uilover.project307.ui.profile.components.AccountMenuCard
import com.uilover.project307.ui.profile.components.LogoutButton
import com.uilover.project307.ui.profile.components.OrdersStatusCard
import com.uilover.project307.ui.profile.components.ProfileHeaderCard
import com.uilover.project307.ui.profile.components.ProfileTopBar
import com.uilover.project307.ui.profile.components.WalletLoyaltyWidget

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            ProfileTopBar(
                onNotificationsClick = { onAction(ProfileAction.OnNotificationsClick) }
            )
        },
        bottomBar = {
            StoreBottomNavBar(
                selectedTab = uiState.selectedTab,
                cartItemCount = uiState.cartItemCount,
                onTabSelected = { tab -> onAction(ProfileAction.OnTabSelected(tab)) }
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
                item(key = "top_spacer") {
                    Spacer(modifier = Modifier.height(2.dp))
                }

                // 1. Profile Header Card
                item(key = "profile_header") {
                    ProfileHeaderCard(
                        user = uiState.userProfile,
                        onEditClick = { onAction(ProfileAction.OnEditProfileClick) }
                    )
                }

                // 2. Wallet & Reward Club Trio Widget
                item(key = "wallet_loyalty_widget") {
                    WalletLoyaltyWidget(
                        walletBalance = uiState.userProfile.walletBalance,
                        rewardPoints = uiState.userProfile.rewardPoints,
                        couponsCount = uiState.userProfile.activeCouponsCount,
                        onTopUpClick = { onAction(ProfileAction.OnTopUpWallet) },
                        onRedeemClick = { onAction(ProfileAction.OnRedeemRewards) },
                        onViewCouponsClick = { onAction(ProfileAction.OnViewCoupons) }
                    )
                }

                // 3. My Orders Status Overview & Tracker
                item(key = "orders_status_card") {
                    OrdersStatusCard(
                        orderStatus = uiState.orderStatus,
                        activeDelivery = uiState.activeDelivery,
                        onStatusClick = { onAction(ProfileAction.OnOrderStatusClick(it)) },
                        onViewAllClick = { onAction(ProfileAction.OnViewAllOrdersClick) },
                        onTrackDeliveryClick = { onAction(ProfileAction.OnTrackDeliveryClick) }
                    )
                }

                // 4. Account Navigation Menu
                item(key = "account_menu_card") {
                    AccountMenuCard(
                        menuItems = uiState.menuItems,
                        onItemClick = { onAction(ProfileAction.OnMenuItemClick(it)) }
                    )
                }

                // 5. Logout Button
                item(key = "logout_button") {
                    LogoutButton(
                        onClick = { onAction(ProfileAction.OnLogoutClick) }
                    )
                }

                item(key = "bottom_spacer") {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Interactive Feedback Toast
            CartFeedbackToast(
                message = uiState.toastMessage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}
