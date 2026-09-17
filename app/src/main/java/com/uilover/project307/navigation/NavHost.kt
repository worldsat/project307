package com.uilover.project307.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uilover.project307.model.BottomNavTab
import com.uilover.project307.ui.cart.CartAction
import com.uilover.project307.ui.cart.CartScreen
import com.uilover.project307.ui.cart.CartViewModel
import com.uilover.project307.ui.categories.CategoriesAction
import com.uilover.project307.ui.categories.CategoriesScreen
import com.uilover.project307.ui.categories.CategoriesViewModel
import com.uilover.project307.ui.detail.ProductDetailAction
import com.uilover.project307.ui.detail.ProductDetailScreen
import com.uilover.project307.ui.detail.ProductDetailViewModel
import com.uilover.project307.ui.home.HomeAction
import com.uilover.project307.ui.home.HomeScreen
import com.uilover.project307.ui.home.HomeViewModel
import com.uilover.project307.ui.profile.ProfileAction
import com.uilover.project307.ui.profile.ProfileScreen
import com.uilover.project307.ui.profile.ProfileViewModel

object AppRoutes {
    const val HOME = "store/home"
    const val PRODUCT_DETAIL = "store/product/{productId}"
    const val CATEGORIES = "store/categories"
    const val CART = "store/cart"
    const val PROFILE = "store/profile"

    fun productDetail(productId: String): String = "store/product/$productId"
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppRoutes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // 1. Home / Discovery Screen
        composable(route = AppRoutes.HOME) {
            val homeViewModel: HomeViewModel = viewModel()
            val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is HomeAction.OnProductClick -> {
                            navController.navigate(AppRoutes.productDetail(action.productId))
                        }
                        is HomeAction.OnBannerShopNowClick -> {
                            navController.navigate(AppRoutes.productDetail("deal_3"))
                        }
                        is HomeAction.OnViewAllCategoriesClick -> {
                            navController.navigate(AppRoutes.CATEGORIES) {
                                popUpTo(AppRoutes.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        is HomeAction.OnBrandBagClick -> {
                            navController.navigate(AppRoutes.CART) {
                                popUpTo(AppRoutes.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        is HomeAction.OnTabSelected -> {
                            when (action.tab) {
                                BottomNavTab.CATEGORIES -> {
                                    navController.navigate(AppRoutes.CATEGORIES) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                BottomNavTab.CART -> {
                                    navController.navigate(AppRoutes.CART) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                BottomNavTab.PROFILE -> {
                                    navController.navigate(AppRoutes.PROFILE) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                else -> {
                                    homeViewModel.onAction(action)
                                }
                            }
                        }
                        else -> {
                            homeViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // 2. Product Detail Screen
        composable(
            route = AppRoutes.PRODUCT_DETAIL,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                    defaultValue = "deal_3"
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: "deal_3"
            val detailViewModel: ProductDetailViewModel = viewModel(
                key = productId,
                factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        return ProductDetailViewModel(productId) as T
                    }
                }
            )
            val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()

            ProductDetailScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is ProductDetailAction.OnBackClick -> {
                            navController.popBackStack()
                        }
                        else -> {
                            detailViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // 3. Categories Screen
        composable(route = AppRoutes.CATEGORIES) {
            val categoriesViewModel: CategoriesViewModel = viewModel()
            val uiState by categoriesViewModel.uiState.collectAsStateWithLifecycle()

            CategoriesScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is CategoriesAction.OnTabSelected -> {
                            when (action.tab) {
                                BottomNavTab.HOME -> {
                                    navController.navigate(AppRoutes.HOME) {
                                        popUpTo(AppRoutes.HOME) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                                BottomNavTab.CART -> {
                                    navController.navigate(AppRoutes.CART) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                BottomNavTab.PROFILE -> {
                                    navController.navigate(AppRoutes.PROFILE) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                else -> {
                                    categoriesViewModel.onAction(action)
                                }
                            }
                        }
                        is CategoriesAction.OnProductClick -> {
                            navController.navigate(AppRoutes.productDetail(action.productId))
                        }
                        else -> {
                            categoriesViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // 4. Cart Screen
        composable(route = AppRoutes.CART) {
            val cartViewModel: CartViewModel = viewModel()
            val uiState by cartViewModel.uiState.collectAsStateWithLifecycle()

            CartScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is CartAction.OnTabSelected -> {
                            when (action.tab) {
                                BottomNavTab.HOME -> {
                                    navController.navigate(AppRoutes.HOME) {
                                        popUpTo(AppRoutes.HOME) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                                BottomNavTab.CATEGORIES -> {
                                    navController.navigate(AppRoutes.CATEGORIES) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                BottomNavTab.PROFILE -> {
                                    navController.navigate(AppRoutes.PROFILE) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                else -> {
                                    cartViewModel.onAction(action)
                                }
                            }
                        }
                        is CartAction.OnProductClick -> {
                            navController.navigate(AppRoutes.productDetail(action.productId))
                        }
                        is CartAction.OnStartShoppingClick -> {
                            navController.navigate(AppRoutes.HOME) {
                                popUpTo(AppRoutes.HOME) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                        else -> {
                            cartViewModel.onAction(action)
                        }
                    }
                }
            )
        }

        // 5. User Profile Screen
        composable(route = AppRoutes.PROFILE) {
            val profileViewModel: ProfileViewModel = viewModel()
            val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

            ProfileScreen(
                uiState = uiState,
                onAction = { action ->
                    when (action) {
                        is ProfileAction.OnTabSelected -> {
                            when (action.tab) {
                                BottomNavTab.HOME -> {
                                    navController.navigate(AppRoutes.HOME) {
                                        popUpTo(AppRoutes.HOME) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                                BottomNavTab.CATEGORIES -> {
                                    navController.navigate(AppRoutes.CATEGORIES) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                BottomNavTab.CART -> {
                                    navController.navigate(AppRoutes.CART) {
                                        popUpTo(AppRoutes.HOME) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                else -> {
                                    profileViewModel.onAction(action)
                                }
                            }
                        }
                        else -> {
                            profileViewModel.onAction(action)
                        }
                    }
                }
            )
        }
    }
}
