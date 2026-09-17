# DESIGN.md — Jetpack Compose (Material 3) UI Specification

**Project:** Modern E-Commerce Mobile App  
**Target Platform:** Android (Jetpack Compose, Material 3)  
**Architecture Guideline:** Unidirectional Data Flow (UDF), MVVM, State-driven Compose  
**Design System Seed:** `Modern Persian Commerce` (`#E11D48` Primary Crimson Rose)

---

## 1. GLOBAL DESIGN TOKENS

### 1.1 Color Palette (Hex & Material 3 Roles)

| Token Name | Hex Code | Material 3 Role | Usage Description |
| :--- | :--- | :--- | :--- |
| `Primary` | `#E11D48` | `MaterialTheme.colorScheme.primary` | Key brand actions, primary CTAs, active indicators, badges |
| `OnPrimary` | `#FFFFFF` | `MaterialTheme.colorScheme.onPrimary` | Text & icons placed directly on top of `Primary` containers |
| `PrimaryContainer` | `#FFE4E6` | `MaterialTheme.colorScheme.primaryContainer` | Soft promo tags, badge backgrounds, pill highlights |
| `OnPrimaryContainer` | `#881337` | `MaterialTheme.colorScheme.onPrimaryContainer` | Text/icons placed inside `PrimaryContainer` elements |
| `Secondary / CTA` | `#BE123C` | `MaterialTheme.colorScheme.secondary` | High-emphasis hover/press states, secondary banners, critical buttons |
| `OnSecondary` | `#FFFFFF` | `MaterialTheme.colorScheme.onSecondary` | Text on secondary elements |
| `Surface` | `#FAFAFA` | `MaterialTheme.colorScheme.surface` | Global screen background |
| `SurfaceBright` | `#FFFFFF` | `MaterialTheme.colorScheme.surfaceBright` | Elevated card surfaces, floating sheets, input text field containers |
| `SurfaceContainerLow` | `#F1F5F9` | `MaterialTheme.colorScheme.surfaceContainerLow` | Subtle card borders, secondary pill badges, divider backgrounds |
| `SurfaceContainerHigh`| `#E2E8F0` | `MaterialTheme.colorScheme.surfaceContainerHigh` | Stepper buttons, active counter pills, icon backgrounds |
| `OnSurface` | `#0F172A` | `MaterialTheme.colorScheme.onSurface` | High-emphasis body text, product titles, headings, dark icons |
| `OnSurfaceVariant` | `#64748B` | `MaterialTheme.colorScheme.onSurfaceVariant` | Subtitles, meta information, SKU/review count, secondary captions |
| `Outline` | `#CBD5E1` | `MaterialTheme.colorScheme.outline` | Standard input borders, card boundaries, subtle separators |
| `OutlineVariant` | `#E2E8F0` | `MaterialTheme.colorScheme.outlineVariant` | Light dividers between list items |
| `SuccessGreen` | `#16A34A` | Custom semantic | In-stock tags, delivery badges, active warranty checkmarks |
| `WarningAmber` | `#D97706` | Custom semantic | Star ratings, member tier status, limited quantity alerts |

### 1.2 Typography (Material 3 Type Scale)

**Font Family:** `Plus Jakarta Sans` or Google Fonts `PlusJakartaSans` (Fallback: `FontFamily.SansSerif`)

| Material 3 Style | Size (`sp`) | Weight | Line Height (`sp`) | Tracking (`sp`) | Usage |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `headlineLarge` | `28.sp` | `FontWeight.Bold` (`W700`) | `36.sp` | `-0.2.sp` | Promotional banners, primary discount figures |
| `headlineMedium` | `24.sp` | `FontWeight.SemiBold` (`W600`) | `32.sp` | `0.sp` | Top-level screen headings, user display name |
| `titleLarge` | `20.sp` | `FontWeight.SemiBold` (`W600`) | `26.sp` | `0.sp` | Section headers (`Flash Deals`, `Best Sellers`, `My Orders`) |
| `titleMedium` | `16.sp` | `FontWeight.Bold` (`W700`) | `22.sp` | `0.1.sp` | Product card titles, item row names, total payable amount |
| `titleSmall` | `14.sp` | `FontWeight.SemiBold` (`W600`) | `20.sp` | `0.1.sp` | Cart item titles, filter labels, tab text |
| `bodyLarge` | `15.sp` | `FontWeight.Normal` (`W400`) | `22.sp` | `0.15.sp` | Customer review body copy, product descriptions |
| `bodyMedium` | `13.sp` | `FontWeight.Normal` (`W400`) | `18.sp` | `0.25.sp` | Meta details, warranty terms, coupon descriptions |
| `bodySmall` | `11.sp` | `FontWeight.Medium` (`W500`) | `16.sp` | `0.4.sp` | Order IDs, shipping timestamps, SKU labels |
| `labelLarge` | `14.sp` | `FontWeight.Bold` (`W700`) | `20.sp` | `0.1.sp` | Main CTA button labels (`Add to Cart`, `Proceed to Checkout`) |
| `labelMedium` | `12.sp` | `FontWeight.SemiBold` (`W600`) | `16.sp` | `0.5.sp` | Badges, discount percentages (`-15% OFF`), pill buttons |
| `labelSmall` | `10.sp` | `FontWeight.Bold` (`W700`) | `14.sp` | `0.5.sp` | Floating badge indicators on navigation icons |

### 1.3 Spacing & Shapes

#### Layout Spacing
- `ScreenHorizontalPadding`: `16.dp`
- `ScreenVerticalPadding`: `16.dp`
- `SectionSpacing`: `24.dp`
- `ItemSpacingCompact`: `8.dp`
- `ItemSpacingStandard`: `12.dp`
- `ItemSpacingGenerous`: `16.dp`

#### Corner Radii (Material 3 Shapes)
- `ButtonCornerRadius`: `16.dp` (`RoundedCornerShape(16.dp)`)
- `CardCornerRadius`: `20.dp` (`RoundedCornerShape(20.dp)`)
- `InputCornerRadius`: `14.dp` (`RoundedCornerShape(14.dp)`)
- `PillBadgeCornerRadius`: `100.dp` (`CircleShape`)
- `ImageContainerCornerRadius`: `16.dp` (`RoundedCornerShape(16.dp)`)
- `BottomSheetTopRadius`: `28.dp` (`RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)`)

---

## 2. SCREEN REFERENCE GALLERY

| # | Technical Screen Title | Route Identifier | Structural Purpose & Key Highlights |
| :--- | :--- | :--- | :--- |
| **01** | `HomeScreen` | `store/home` | Storefront entry point: dynamic search bar with QR scanner, promo carousel banner with countdown timer, horizontal category chip selector, flash deal carousel, and 2-column best seller grid with quick-add CTAs. |
| **02** | `ProductDetailScreen` | `store/product/{productId}` | High-intent PDP: edge-to-edge product image gallery with page indicator, pricing with discount badges, color variant picker, 3-card key highlight pills, warranty assurance card, verified customer reviews, and a sticky bottom CTA bar. |
| **03** | `CartScreen` | `store/cart` | Checkout funnel: free delivery qualification banner, interactive cart item cards with increment/decrement steppers & remove actions, promo code redemption input with inline apply CTA, detailed order summary breakdown, and primary checkout CTA. |
| **04** | `ProfileScreen` | `store/profile` | Account dashboard: user header with avatar & verified badge, wallet / rewards / coupon metrics trio cards, 4-status order progress grid (`Processing`, `Delivered`, `Cancelled`, `Returned`), active delivery tracking card, and navigation list items. |

---

## 3. COMPONENT-LEVEL SPECIFICATIONS

### 3.1 Global Navigation Chrome
- **`StoreScaffold`**:
  - Encapsulated via Compose `Scaffold`.
  - Includes `bottomBar = { StoreBottomNavBar(...) }` on top-level destinations (`Home`, `Cart`, `Profile`).
  - Supports sticky bottom action sheets on `ProductDetailScreen` and `CartScreen`.
- **`StoreBottomNavBar`**:
  - Layout: `NavigationBar` with `containerColor = Color.White` and elevation `8.dp`.
  - 4 items: `Home` (`Icons.Outlined.Home`), `Categories` (`Icons.Outlined.GridView`), `Cart` (`Icons.Outlined.ShoppingBag` with `BadgedBox` showing counter `2`), `Profile` (`Icons.Outlined.Person`).
  - Active color: `#E11D48`, Inactive color: `#64748B`.

### 3.2 Top App Bars
- **Home Top Bar**:
  - `Row` with user avatar thumbnail (`36.dp` circle), Title `"Home"`, and action icons (`NotificationBell` with unread dot, `BrandBagIcon`).
- **Product Detail Top Bar**:
  - `CenterAlignedTopAppBar` or custom `Row`: Back button (`Icons.AutoMirrored.Filled.ArrowBack`), title `"Product Details"`, actions (`Share`, `FavoriteBorder`, `BagIcon`).
- **Cart Top Bar**:
  - Simple top header with bold title `"Cart"`, notifications icon, and brand bag monogram.

### 3.3 Product & Deal Cards
- **`FlashDealCard`**:
  - Layout: `Surface` card (`RoundedCornerShape(20.dp)`), `width = 240.dp`.
  - Top: Badge `-45% OFF` in `#E11D48`.
  - Image: `AsyncImage` with `height = 180.dp`, `ContentScale.Crop`.
  - Content: Title (`titleSmall`, maxLines = 2), current price (`#E11D48`, `titleMedium`), strikethrough price (`bodySmall`), stock progress indicator bar.
- **`GridProductCard`**:
  - 2-column layout inside `LazyVerticalGrid(GridCells.Fixed(2))`.
  - Rounded surface (`16.dp`), favorite heart icon overlay (`Box` alignment `TopEnd`).
  - Bottom `Row`: Price + circular quick add button (`IconButton` with `#E11D48` background and white `+`).

### 3.4 Stepper & Form Controls
- **`QuantityStepper`**:
  - Horizontal `Row` wrapped in light gray container (`#F1F5F9`, shape `CircleShape` or `12.dp`).
  - Left button: `-` icon (`IconButton`, `size = 28.dp`).
  - Center: Current count text (`titleSmall`, `FontWeight.Bold`).
  - Right button: `+` icon (`IconButton`, `size = 28.dp`).
- **`CouponInputField`**:
  - Container: `Row` with `OutlinedTextField` / `BasicTextField` styled with `#F8FAFC` background and rounded outline (`14.dp`).
  - Trailing button: Solid `#E11D48` rounded `Button` (`Apply`).

### 3.5 Sticky Bottom Action Bar
- **`StickyCheckoutBar`**:
  - Wrapped in `Surface(color = Color.White, shadowElevation = 12.dp)`.
  - Inner content: `Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically)`.
  - Left column: Total price calculation with micro label (`"Total Payable"` / `"Total"`).
  - Right: Full-width or flex `Button` (`colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE11D48))`, `shape = RoundedCornerShape(16.dp)`).

---

## 4. FIXED IMAGE ASSET REGISTRY

| Asset Identifier | Asset Type | Dimensions / Aspect | Source / Visual Description | Target Compose Usage |
| :--- | :--- | :--- | :--- | :--- |
| `IMG_BRAND_LOGO` | Brand Logo / Icon | `1024x1024` (1:1 Vector PNG) | Monogram shopping bag with sparkle in `#E11D48` crimson red on transparent canvas | `TopAppBar` brand icon, splash screen, checkout receipt header |
| `IMG_USER_AVATAR` | Profile Photo | `1024x1024` (1:1 Portrait) | High-res studio portrait of young professional woman (Sara Mohammadi) with gold earrings & emerald blazer | Top bar profile thumbnail, profile screen header circle (`72.dp`) |
| `IMG_PROD_HEADPHONES` | Product Imagery | `1:1` or `4:3` Studio Photo | Pro Sound Max Wireless Over-Ear ANC Headphones in Matte Carbon Black on pedestal | PDP hero image gallery, Flash Deals card, Cart item #1 |
| `IMG_PROD_SMARTWATCH`| Product Imagery | `1:1` Studio Photo | Smart Watch Fit Pro with graphite silver body & black silicone strap | Flash Deals card, Cart item #2 |
| `IMG_PROD_CASE` | Product Imagery | `1:1` Studio Photo | Silicone Protective Earphone Case in Pastel Peach | Cart item #3 |
| `IMG_PROD_KEYBOARD` | Product Imagery | `1:1` Studio Photo | Retro Mechanical Wireless Pastel Keyboard on desk mat | Best Sellers 2-column grid item |
| `IMG_PROD_MUG` | Product Imagery | `1:1` Studio Photo | Stainless Steel Travel Tumbler Mug in Dusty Rose | Best Sellers 2-column grid item |
| `IMG_PROD_SERUM` | Product Imagery | `1:1` Studio Photo | Deep Hydrating Hyaluronic Facial Serum bottle with glass dropper | Best Sellers 2-column grid item |
| `IMG_PROD_LAMP` | Product Imagery | `1:1` Studio Photo | Modern Touch Rechargeable Bedside Night Lamp with warm glow | Best Sellers 2-column grid item |
| `IMG_BANNER_PROMO` | Marketing Banner | `16:9` Gradient Canvas | Deep crimson gradient `#BE123C` to `#E11D48` with promo copy & countdown digits | Home screen top promo banner |

---

## 5. AI IMPLEMENTATION SPECIAL INSTRUCTIONS

```kotlin
// =============================================================================
// AI GENERATOR DIRECTIVES (For Cursor, Copilot, Android Studio AI)
// =============================================================================

/**
 * ARCHITECTURE PRINCIPLES:
 * 1. UNIDIRECTIONAL DATA FLOW (UDF):
 *    - All screens must accept a State object and emit high-level UI events (lambdas).
 *    - Example:
 *      @Composable
 *      fun CartScreen(
 *          uiState: CartUiState,
 *          onItemCountChanged: (productId: String, delta: Int) -> Unit,
 *          onRemoveItem: (productId: String) -> Unit,
 *          onApplyCoupon: (code: String) -> Unit,
 *          onProceedToCheckout: () -> Unit
 *      )
 *
 * 2. COLOR TOKEN ENFORCEMENT:
 *    - Primary CTA container color: Color(0xFFE11D48).
 *    - Secondary CTA / Dark Red: Color(0xFFBE123C).
 *    - Surface Background: Color(0xFFFAFAFA).
 *    - Elevated Cards: Color(0xFFFFFFFF).
 *    - Do NOT default to Material 3 purple/indigo tint. Always use the provided palette.
 *
 * 3. LAYOUT & PERFORMANCE RULES:
 *    - Use LazyColumn / LazyVerticalGrid with contentType and unique key parameters:
 *      items(items = uiState.cartItems, key = { it.id }, contentType = { "cart_item" })
 *    - For sticky bottom buttons, place them OUTSIDE the scrolling LazyColumn:
 *      Scaffold(
 *          bottomBar = { StickyBottomAction(uiState.totalAmount, onProceedToCheckout) }
 *      ) { innerPadding ->
 *          LazyColumn(modifier = Modifier.padding(innerPadding)) { ... }
 *      }
 *
 * 4. RTL & LOCALIZATION COMPATIBILITY:
 *    - Use Modifier.padding(start = ..., end = ...) rather than left/right to ensure 
 *      flawless bidirectional support if Arabic/Persian/English is toggled.
 *    - Icon navigation arrows must use Icons.AutoMirrored.Filled.ArrowBack / ArrowForward.
 *
 * 5. IMAGE RENDERING:
 *    - Use Coil's rememberAsyncImagePainter or AsyncImage:
 *      AsyncImage(
 *          model = item.imageUrl,
 *          contentDescription = item.title,
 *          contentScale = ContentScale.Crop,
 *          modifier = Modifier.clip(RoundedCornerShape(16.dp))
 *      )
 */
```
