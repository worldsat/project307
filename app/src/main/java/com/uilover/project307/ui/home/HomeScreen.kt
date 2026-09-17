package com.uilover.project307.ui.home

import androidx.compose.foundation.background
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
import com.uilover.project307.ui.home.components.BestSellersSection
import com.uilover.project307.ui.home.components.CartFeedbackToast
import com.uilover.project307.ui.home.components.EditorialBannersSection
import com.uilover.project307.ui.home.components.FeaturedCategoriesSection
import com.uilover.project307.ui.home.components.FlashDealsSection
import com.uilover.project307.ui.home.components.HeroPromoBanner
import com.uilover.project307.ui.home.components.HomeSearchBar
import com.uilover.project307.ui.home.components.HomeTopBar
import com.uilover.project307.ui.home.components.StoreBottomNavBar
import com.uilover.project307.ui.home.components.TrustBadgesStrip

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            HomeTopBar(
                onNotificationsClick = { onAction(HomeAction.OnNotificationsClick) },
                onBrandBagClick = { onAction(HomeAction.OnBrandBagClick) }
            )
        },
        bottomBar = {
            StoreBottomNavBar(
                selectedTab = uiState.selectedTab,
                cartItemCount = uiState.cartItemCount,
                onTabSelected = { tab -> onAction(HomeAction.OnTabSelected(tab)) }
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
                modifier = Modifier.fillMaxSize()
            ) {
                // 1. Search Bar & Filter Action
                item(key = "search_bar") {
                    HomeSearchBar(
                        query = uiState.searchQuery,
                        onQueryChanged = { onAction(HomeAction.OnSearchQueryChanged(it)) },
                        onScanBarcodeClick = { onAction(HomeAction.OnScanBarcodeClick) },
                        onFilterClick = { onAction(HomeAction.OnFilterClick) }
                    )
                }

                // 2. Trust Badges Micro-Strip
                item(key = "trust_badges") {
                    TrustBadgesStrip(badges = uiState.trustBadges)
                }

                // 3. Autumn Festival Hero Banner with Live Countdown
                if (uiState.heroBanner != null) {
                    item(key = "hero_banner") {
                        HeroPromoBanner(
                            banner = uiState.heroBanner,
                            hours = uiState.countdownHours,
                            minutes = uiState.countdownMinutes,
                            seconds = uiState.countdownSeconds,
                            onShopNowClick = { onAction(HomeAction.OnBannerShopNowClick) }
                        )
                    }
                }

                // 4. Quick Category Bubbles
                item(key = "categories_section") {
                    FeaturedCategoriesSection(
                        categories = uiState.categories,
                        selectedCategoryId = uiState.selectedCategoryId,
                        onCategoryClick = { onAction(HomeAction.OnCategorySelected(it)) },
                        onViewAllClick = { onAction(HomeAction.OnViewAllCategoriesClick) }
                    )
                }

                // 5. Flash Deals Scroller (Horizontal Carousel)
                item(key = "flash_deals_section") {
                    FlashDealsSection(
                        deals = uiState.flashDeals,
                        onProductClick = { onAction(HomeAction.OnProductClick(it)) },
                        onViewAllClick = { onAction(HomeAction.OnViewAllFlashDealsClick) }
                    )
                }

                // 6. Dual Editorial Minimal Banners
                item(key = "editorial_banners_section") {
                    EditorialBannersSection(
                        banners = uiState.editorialBanners
                    )
                }

                // 7. Best Sellers (2-Column Grid)
                item(key = "best_sellers_section") {
                    BestSellersSection(
                        products = uiState.bestSellers,
                        onToggleFavorite = { onAction(HomeAction.OnToggleFavorite(it)) },
                        onAddToCart = { onAction(HomeAction.OnAddToCart(it)) },
                        onProductClick = { onAction(HomeAction.OnProductClick(it)) },
                        onFilterSortClick = { onAction(HomeAction.OnFilterClick) }
                    )
                }

                // Generous bottom spacing for comfortable scrolling
                item(key = "bottom_spacer") {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            // Interactive Feedback Toast (Floating at bottom center)
            CartFeedbackToast(
                message = uiState.toastMessage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}
