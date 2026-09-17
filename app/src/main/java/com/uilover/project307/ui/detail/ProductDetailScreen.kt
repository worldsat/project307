package com.uilover.project307.ui.detail

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
import com.uilover.project307.ui.detail.components.ColorVariantPicker
import com.uilover.project307.ui.detail.components.CustomerReviewsSection
import com.uilover.project307.ui.detail.components.DeliveryPerksSection
import com.uilover.project307.ui.detail.components.KeyHighlightsSection
import com.uilover.project307.ui.detail.components.ProductDetailTopBar
import com.uilover.project307.ui.detail.components.ProductGallerySection
import com.uilover.project307.ui.detail.components.ProductMetadataSection
import com.uilover.project307.ui.detail.components.StickyAddToCartBar
import com.uilover.project307.ui.detail.components.WarrantyAndCareSection
import com.uilover.project307.ui.home.components.CartFeedbackToast

@Composable
fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    onAction: (ProductDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            ProductDetailTopBar(
                isFavorite = uiState.isFavorite,
                onBackClick = { onAction(ProductDetailAction.OnBackClick) },
                onShareClick = { onAction(ProductDetailAction.OnShareClick) },
                onToggleFavorite = { onAction(ProductDetailAction.OnToggleFavorite) }
            )
        },
        bottomBar = {
            StickyAddToCartBar(
                originalPrice = uiState.productDetail.originalPrice,
                currentPrice = uiState.productDetail.currentPrice,
                discountPercent = uiState.productDetail.discountPercent,
                isAddedToCart = uiState.isAddedToCart,
                onAddToCartClick = { onAction(ProductDetailAction.OnAddToCart) }
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
                // 1. Top Image Gallery Stage with Selector
                item(key = "gallery_section") {
                    ProductGallerySection(
                        galleryImages = uiState.productDetail.galleryImages,
                        selectedIndex = uiState.selectedGalleryIndex,
                        isFavorite = uiState.isFavorite,
                        onSelectImage = { onAction(ProductDetailAction.OnSelectGallery(it)) },
                        onShareClick = { onAction(ProductDetailAction.OnShareClick) },
                        onToggleFavorite = { onAction(ProductDetailAction.OnToggleFavorite) }
                    )
                }

                // 2. Titles, Brand, SKU & Rating Meta
                item(key = "metadata_section") {
                    ProductMetadataSection(detail = uiState.productDetail)
                }

                // 3. Color Variant Swatches Picker
                item(key = "color_variant_section") {
                    ColorVariantPicker(
                        colors = uiState.productDetail.colors,
                        selectedIndex = uiState.selectedColorIndex,
                        onSelectColor = { onAction(ProductDetailAction.OnSelectColor(it)) }
                    )
                }

                // 4. Key Product Highlights (3 Cards)
                item(key = "highlights_section") {
                    KeyHighlightsSection(highlights = uiState.productDetail.highlights)
                }

                // 5. Warranty & Return Policy
                item(key = "warranty_section") {
                    WarrantyAndCareSection(
                        selectedOption = uiState.selectedWarrantyOption,
                        onSelectOption = { onAction(ProductDetailAction.OnSelectWarranty(it)) }
                    )
                }

                // 6. Free Delivery & Fulfillment Stock
                item(key = "delivery_perks_section") {
                    DeliveryPerksSection()
                }

                // 7. Customer Reviews Visual Bar & Featured Card
                item(key = "customer_reviews_section") {
                    CustomerReviewsSection(
                        summary = uiState.productDetail.reviewsSummary,
                        featuredReview = uiState.productDetail.featuredReview,
                        upvotes = uiState.reviewUpvotes,
                        downvotes = uiState.reviewDownvotes,
                        onVoteReview = { onAction(ProductDetailAction.OnVoteReview(it)) },
                        onWriteReviewClick = { onAction(ProductDetailAction.OnWriteReviewClick) }
                    )
                }

                // Generous bottom spacer for scroll clearance
                item(key = "bottom_spacer") {
                    Spacer(modifier = Modifier.height(28.dp))
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
