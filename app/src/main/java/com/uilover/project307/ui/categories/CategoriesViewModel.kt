package com.uilover.project307.ui.categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uilover.project307.R
import com.uilover.project307.model.BadgeIconType
import com.uilover.project307.model.CategoryCollection
import com.uilover.project307.model.Product
import com.uilover.project307.model.SubCategory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val sampleCategories = listOf(
        CategoryCollection(
            id = "cat_digital",
            name = "Digital",
            iconVector = Icons.Outlined.Devices,
            bannerTag = "Flash Tech",
            bannerTitle = "Smart Audio & Gear",
            bannerSubtitle = "High-fidelity sound and wireless peripherals",
            discountText = "Up to 45% OFF",
            subcategories = listOf(
                SubCategory("sub_all", "All Gear", 18, Icons.Outlined.Devices),
                SubCategory("sub_audio", "Audio", 8, Icons.Outlined.Tune),
                SubCategory("sub_wearables", "Smartwatches", 5, Icons.Outlined.Devices),
                SubCategory("sub_keyboards", "Keyboards", 5, Icons.Outlined.ShoppingBag)
            ),
            products = listOf(
                Product(
                    id = "deal_3",
                    title = "Pro Sound Max ANC Headphones",
                    originalPrice = 249.99,
                    currentPrice = 149.99,
                    discountPercent = 40,
                    imageRes = R.drawable.product_headphones_main,
                    badgeText = "40% OFF",
                    stockProgress = 0.75f,
                    rating = 4.8f,
                    reviewCount = 124,
                    deliveryBadge = "Free Shipping",
                    deliveryBadgeIconType = BadgeIconType.SHIPPING
                ),
                Product(
                    id = "deal_2",
                    title = "Ultra Leather Smartwatch Pro",
                    originalPrice = 165.00,
                    currentPrice = 118.80,
                    discountPercent = 28,
                    imageRes = R.drawable.product_smartwatch,
                    badgeText = "28% OFF",
                    stockProgress = 0.60f,
                    rating = 4.7f,
                    reviewCount = 89,
                    deliveryBadge = "Best Seller",
                    deliveryBadgeIconType = BadgeIconType.VERIFIED
                ),
                Product(
                    id = "prod_keyboard",
                    title = "Retro Mechanical Wireless Keyboard",
                    originalPrice = 89.00,
                    currentPrice = 74.99,
                    discountPercent = 16,
                    imageRes = R.drawable.product_keyboard,
                    rating = 4.9f,
                    reviewCount = 340,
                    deliveryBadge = "18-Month Warranty",
                    deliveryBadgeIconType = BadgeIconType.WARRANTY
                ),
                Product(
                    id = "deal_1",
                    title = "Pro Sound Wireless Earbuds",
                    originalPrice = 109.99,
                    currentPrice = 59.99,
                    discountPercent = 45,
                    imageRes = R.drawable.product_earbuds,
                    badgeText = "45% OFF",
                    stockProgress = 0.80f,
                    rating = 4.6f,
                    reviewCount = 92,
                    deliveryBadge = "Free Shipping",
                    deliveryBadgeIconType = BadgeIconType.SHIPPING
                ),
                Product(
                    id = "prod_case",
                    title = "Silicone Protective Case",
                    originalPrice = 12.00,
                    currentPrice = 9.00,
                    discountPercent = 25,
                    imageRes = R.drawable.product_case_peach,
                    rating = 4.5f,
                    reviewCount = 54,
                    deliveryBadge = "Shockproof",
                    deliveryBadgeIconType = BadgeIconType.VERIFIED
                )
            )
        ),
        CategoryCollection(
            id = "cat_beauty",
            name = "Beauty",
            iconVector = Icons.Outlined.Spa,
            bannerTag = "Skincare Essentials",
            bannerTitle = "Glow & Hydration",
            bannerSubtitle = "Dermatologist-tested daily serums and treatments",
            discountText = "25% OFF",
            subcategories = listOf(
                SubCategory("sub_all_beauty", "All Beauty", 12, Icons.Outlined.Spa),
                SubCategory("sub_serums", "Serums", 6, Icons.Outlined.WaterDrop),
                SubCategory("sub_treatments", "Care", 6, Icons.Outlined.Spa)
            ),
            products = listOf(
                Product(
                    id = "prod_serum",
                    title = "Deep Hydrating Hyaluronic Serum",
                    originalPrice = 19.99,
                    currentPrice = 15.20,
                    discountPercent = 24,
                    rating = 4.7f,
                    reviewCount = 88,
                    imageRes = R.drawable.product_serum,
                    deliveryBadge = "Free Shipping",
                    deliveryBadgeIconType = BadgeIconType.SHIPPING
                )
            )
        ),
        CategoryCollection(
            id = "cat_home",
            name = "Home",
            iconVector = Icons.Outlined.Kitchen,
            bannerTag = "Modern Spaces",
            bannerTitle = "Cozy & Ambient Living",
            bannerSubtitle = "Warm bedside glow and insulated drinkware",
            discountText = "30% OFF",
            subcategories = listOf(
                SubCategory("sub_all_home", "All Home", 16, Icons.Outlined.Kitchen),
                SubCategory("sub_lighting", "Lighting", 7, Icons.Outlined.Tune),
                SubCategory("sub_drinkware", "Drinkware", 9, Icons.Outlined.ShoppingBag)
            ),
            products = listOf(
                Product(
                    id = "prod_lamp",
                    title = "Modern Touch Rechargeable Bedside Lamp",
                    originalPrice = 36.00,
                    currentPrice = 27.99,
                    discountPercent = 22,
                    rating = 4.9f,
                    reviewCount = 210,
                    imageRes = R.drawable.product_lamp,
                    deliveryBadge = "Original Guarantee",
                    deliveryBadgeIconType = BadgeIconType.VERIFIED
                ),
                Product(
                    id = "prod_mug",
                    title = "Stainless Steel Travel Mug Alpha",
                    originalPrice = 24.00,
                    currentPrice = 18.50,
                    discountPercent = 23,
                    rating = 4.8f,
                    reviewCount = 124,
                    imageRes = R.drawable.product_tumbler,
                    deliveryBadge = "Free Shipping",
                    deliveryBadgeIconType = BadgeIconType.SHIPPING
                )
            )
        ),
        CategoryCollection(
            id = "cat_fashion",
            name = "Fashion",
            iconVector = Icons.Outlined.Checkroom,
            bannerTag = "Autumn Style",
            bannerTitle = "Urban Sneakers & Apparel",
            bannerSubtitle = "Engineered comfort for your everyday journey",
            discountText = "40% OFF",
            subcategories = listOf(
                SubCategory("sub_all_fashion", "All Fashion", 24, Icons.Outlined.Checkroom),
                SubCategory("sub_footwear", "Sneakers", 14, Icons.Outlined.ShoppingBag),
                SubCategory("sub_lifestyle", "Lifestyle", 10, Icons.Outlined.Tune)
            ),
            products = listOf(
                Product(
                    id = "prod_sneakers",
                    title = "Comfort Sneakers Global Brands",
                    originalPrice = 99.00,
                    currentPrice = 64.00,
                    discountPercent = 35,
                    rating = 4.8f,
                    reviewCount = 156,
                    imageRes = R.drawable.banner_sneakers,
                    deliveryBadge = "Fast Delivery",
                    deliveryBadgeIconType = BadgeIconType.SHIPPING
                ),
                Product(
                    id = "prod_lifestyle",
                    title = "Daily Lifestyle Active Edition",
                    originalPrice = 120.00,
                    currentPrice = 89.00,
                    discountPercent = 26,
                    rating = 4.9f,
                    reviewCount = 74,
                    imageRes = R.drawable.headphones_lifestyle,
                    deliveryBadge = "Trending",
                    deliveryBadgeIconType = BadgeIconType.VERIFIED
                )
            )
        ),
        CategoryCollection(
            id = "cat_sports",
            name = "Sports",
            iconVector = Icons.Outlined.FitnessCenter,
            bannerTag = "Peak Fitness",
            bannerTitle = "Training & Activity",
            bannerSubtitle = "Track workouts and stay energized",
            discountText = "20% OFF",
            subcategories = listOf(
                SubCategory("sub_all_sports", "All Sports", 10, Icons.Outlined.FitnessCenter),
                SubCategory("sub_gear", "Fitness Gear", 6, Icons.Outlined.Tune)
            ),
            products = listOf(
                Product(
                    id = "deal_2",
                    title = "Ultra Leather Smartwatch Pro",
                    originalPrice = 165.00,
                    currentPrice = 118.80,
                    discountPercent = 28,
                    imageRes = R.drawable.product_smartwatch,
                    badgeText = "28% OFF",
                    stockProgress = 0.60f,
                    rating = 4.7f,
                    reviewCount = 89,
                    deliveryBadge = "Best Seller",
                    deliveryBadgeIconType = BadgeIconType.VERIFIED
                ),
                Product(
                    id = "prod_mug",
                    title = "Stainless Steel Travel Mug Alpha",
                    originalPrice = 24.00,
                    currentPrice = 18.50,
                    discountPercent = 23,
                    rating = 4.8f,
                    reviewCount = 124,
                    imageRes = R.drawable.product_tumbler,
                    deliveryBadge = "Free Shipping",
                    deliveryBadgeIconType = BadgeIconType.SHIPPING
                )
            )
        )
    )

    private val _uiState = MutableStateFlow(
        CategoriesUiState(
            categories = sampleCategories,
            selectedCategoryId = "cat_digital",
            cartItemCount = 2
        )
    )
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null

    fun onAction(action: CategoriesAction) {
        when (action) {
            is CategoriesAction.OnSelectCategory -> {
                _uiState.update {
                    it.copy(
                        selectedCategoryId = action.categoryId,
                        selectedSubcategoryId = null,
                        searchQuery = ""
                    )
                }
            }

            is CategoriesAction.OnSelectSubcategory -> {
                _uiState.update {
                    it.copy(selectedSubcategoryId = action.subcategoryId)
                }
            }

            is CategoriesAction.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = action.query) }
            }

            is CategoriesAction.OnClearSearch -> {
                _uiState.update { it.copy(searchQuery = "") }
            }

            is CategoriesAction.OnAddToCart -> {
                _uiState.update { it.copy(cartItemCount = it.cartItemCount + 1) }
                showToast("Added ${action.product.title.take(24)}... to Cart!")
            }

            is CategoriesAction.OnToggleFavorite -> {
                _uiState.update { state ->
                    val updatedCategories = state.categories.map { category ->
                        val updatedProducts = category.products.map { product ->
                            if (product.id == action.productId) {
                                product.copy(isFavorite = !product.isFavorite)
                            } else {
                                product
                            }
                        }
                        category.copy(products = updatedProducts)
                    }
                    state.copy(categories = updatedCategories)
                }
            }

            is CategoriesAction.OnNotificationsClick -> {
                showToast("No new category updates")
            }

            is CategoriesAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTab = action.tab) }
            }

            is CategoriesAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }

            else -> {
                // Product click is handled at navigation level
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
