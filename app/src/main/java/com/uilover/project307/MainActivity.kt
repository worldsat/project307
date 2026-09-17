package com.uilover.project307

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uilover.project307.data.MockData
import com.uilover.project307.navigation.AppNavHost
import com.uilover.project307.ui.detail.ProductDetailScreen
import com.uilover.project307.ui.detail.ProductDetailUiState
import com.uilover.project307.ui.home.HomeScreen
import com.uilover.project307.ui.home.HomeUiState
import com.uilover.project307.ui.theme.Project307Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project307Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    Project307Theme {
        HomeScreen(
            uiState = HomeUiState(
                trustBadges = MockData.trustBadges,
                heroBanner = MockData.heroPromoBanner,
                remainingSeconds = MockData.heroPromoBanner.remainingSeconds,
                categories = MockData.categories,
                flashDeals = MockData.flashDeals,
                editorialBanners = MockData.editorialBanners,
                bestSellers = MockData.bestSellers,
                cartItemCount = 2
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductDetailScreenPreview() {
    Project307Theme {
        ProductDetailScreen(
            uiState = ProductDetailUiState(
                productDetail = MockData.sampleHeadphoneDetail
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview() {
    Project307Theme {
        com.uilover.project307.ui.cart.CartScreen(
            uiState = com.uilover.project307.ui.cart.CartUiState(
                items = listOf(
                    com.uilover.project307.model.CartItem(
                        id = "item-1",
                        productId = "deal_3",
                        title = "Pro Sound Wireless Headphones",
                        imageRes = R.drawable.product_headphones_main,
                        colorName = "Color: Matte Black",
                        colorHex = androidx.compose.ui.graphics.Color(0xFF1E293B),
                        tagText = "18-month warranty",
                        isTagPrimary = false,
                        originalPrice = 70.00,
                        currentPrice = 59.00,
                        quantity = 1
                    ),
                    com.uilover.project307.model.CartItem(
                        id = "item-2",
                        productId = "deal_2",
                        title = "Smart Watch Fit Pro",
                        imageRes = R.drawable.product_smartwatch,
                        colorName = "Strap: Graphite Silver",
                        colorHex = androidx.compose.ui.graphics.Color(0xFFCBD5E1),
                        tagText = "Best Seller",
                        isTagPrimary = true,
                        originalPrice = 55.00,
                        currentPrice = 45.00,
                        quantity = 2
                    ),
                    com.uilover.project307.model.CartItem(
                        id = "item-3",
                        productId = "deal_1",
                        title = "Silicone Protective Case",
                        imageRes = R.drawable.product_case_peach,
                        colorName = "Color: Pastel Peach",
                        colorHex = androidx.compose.ui.graphics.Color(0xFFFFB3B6),
                        tagText = "Shockproof",
                        isTagPrimary = false,
                        originalPrice = 12.00,
                        currentPrice = 9.00,
                        quantity = 1
                    )
                )
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesScreenPreview() {
    Project307Theme {
        com.uilover.project307.ui.categories.CategoriesScreen(
            uiState = com.uilover.project307.ui.categories.CategoriesViewModel().uiState.value,
            onAction = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    Project307Theme {
        com.uilover.project307.ui.profile.ProfileScreen(
            uiState = com.uilover.project307.ui.profile.ProfileUiState(
                userProfile = MockData.sampleUserProfile,
                orderStatus = MockData.sampleOrderStatus,
                activeDelivery = MockData.sampleActiveDelivery,
                menuItems = MockData.profileMenuItems,
                cartItemCount = 2
            ),
            onAction = {}
        )
    }
}