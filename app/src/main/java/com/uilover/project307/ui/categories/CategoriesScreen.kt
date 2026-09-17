package com.uilover.project307.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uilover.project307.ui.categories.components.CategoriesSearchBar
import com.uilover.project307.ui.categories.components.CategoriesTopBar
import com.uilover.project307.ui.categories.components.CategoryHeroPromoCard
import com.uilover.project307.ui.categories.components.CategoryProductCard
import com.uilover.project307.ui.categories.components.CategoryVerticalRail
import com.uilover.project307.ui.categories.components.SubcategoryChipsRow
import com.uilover.project307.ui.home.components.CartFeedbackToast
import com.uilover.project307.ui.home.components.StoreBottomNavBar

@Composable
fun CategoriesScreen(
    uiState: CategoriesUiState,
    onAction: (CategoriesAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val contentListState = rememberLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            CategoriesTopBar(
                onNotificationsClick = { onAction(CategoriesAction.OnNotificationsClick) }
            )
        },
        bottomBar = {
            StoreBottomNavBar(
                selectedTab = uiState.selectedTab,
                cartItemCount = uiState.cartItemCount,
                onTabSelected = { tab -> onAction(CategoriesAction.OnTabSelected(tab)) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // 1. Search & Filter Bar
                CategoriesSearchBar(
                    query = uiState.searchQuery,
                    onQueryChanged = { onAction(CategoriesAction.OnSearchQueryChanged(it)) },
                    onClearQuery = { onAction(CategoriesAction.OnClearSearch) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )

                // 2. Main Interactive Split-Rail Area
                Row(modifier = Modifier.fillMaxSize()) {
                    // Left Navigation Rail
                    CategoryVerticalRail(
                        categories = uiState.categories,
                        selectedCategoryId = uiState.selectedCategoryId,
                        onCategorySelected = { onAction(CategoriesAction.OnSelectCategory(it)) }
                    )

                    // Right Content Showcase
                    val currentCat = uiState.currentCategory
                    if (currentCat != null) {
                        LazyColumn(
                            state = contentListState,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Hero Promo Banner
                            item(key = "cat_hero_${currentCat.id}") {
                                CategoryHeroPromoCard(category = currentCat)
                            }

                            // Subcategory Pills Row
                            if (currentCat.subcategories.isNotEmpty()) {
                                item(key = "cat_subcategories_${currentCat.id}") {
                                    SubcategoryChipsRow(
                                        subcategories = currentCat.subcategories,
                                        selectedSubcategoryId = uiState.selectedSubcategoryId,
                                        onSubcategorySelected = {
                                            onAction(CategoriesAction.OnSelectSubcategory(it))
                                        }
                                    )
                                }
                            }

                            // Section Title & Items Count
                            item(key = "section_header_${currentCat.id}") {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = if (uiState.searchQuery.isNotBlank()) "Search Results" else "Curated ${currentCat.name}",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                    Text(
                                        text = "${uiState.filteredProducts.size} items",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            // 2-Column Product Grid in pairs
                            val productPairs = uiState.filteredProducts.chunked(2)
                            items(
                                items = productPairs,
                                key = { pair -> pair.joinToString("-") { it.id } }
                            ) { pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    CategoryProductCard(
                                        product = pair[0],
                                        onProductClick = { onAction(CategoriesAction.OnProductClick(it)) },
                                        onAddToCart = { onAction(CategoriesAction.OnAddToCart(it)) },
                                        onToggleFavorite = { onAction(CategoriesAction.OnToggleFavorite(it)) },
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (pair.size > 1) {
                                        CategoryProductCard(
                                            product = pair[1],
                                            onProductClick = { onAction(CategoriesAction.OnProductClick(it)) },
                                            onAddToCart = { onAction(CategoriesAction.OnAddToCart(it)) },
                                            onToggleFavorite = { onAction(CategoriesAction.OnToggleFavorite(it)) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }

                            // Bottom padding
                            item(key = "cat_bottom_spacer") {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
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
