# Modern E-Commerce Android Project Rules

You are an Expert Senior Android Developer.

Build this project as a production-quality, compile-ready, responsive
native Android e-commerce application using:

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Unidirectional Data Flow (UDF)
- StateFlow
- Compose Navigation

The supplied screen references, current `Design.md`, actual local files
inside the project's `assets` folder, existing Android source code,
and supplied HTML references define this project.

Do not introduce functionality, screens, assets, services, or visual
patterns that are not grounded in those sources.

Follow these rules strictly.

---

# 1. SOURCE OF TRUTH

Before implementing or modifying any UI, inspect:

1. the supplied visual reference for that screen;
2. the current `Design.md`;
3. the real files inside the local `assets` folder;
4. the existing Android implementation;
5. the matching supplied HTML reference.

Reference priority:

1. Visual screen reference for:
   - composition
   - hierarchy
   - positioning
   - proportions
   - image crop
   - selected state
   - visible copy

2. `Design.md` for:
   - official Android colors
   - typography
   - spacing
   - shapes
   - architecture
   - routes
   - UDF requirements

3. Actual local assets supplied by the user.

4. Existing Android implementation where it already matches the design.

5. HTML reference for:
   - structural intent
   - interaction behavior
   - deterministic demo data
   - visible content

Never copy project-specific assumptions from an older UiLover project.

The previous project's `rules.md` may only be used as a structural
example. It is not a product-requirements source for this project.

---

# 2. PROJECT IDENTITY

This project is a general:

Modern E-Commerce Mobile App

It is NOT:

- LilaKicks
- a footwear-only store
- a fashion-only store
- an electronics-only store
- an old UiLover project

Do not invent a new brand name unless explicitly requested.

The current grounded screens are:

- HomeScreen
- ProductDetailScreen
- CartScreen
- ProfileScreen

Routes defined by the current design:

- `store/home`
- `store/product/{productId}`
- `store/cart`
- `store/profile`

Use Home as the deterministic start destination.

Do not invent:

- onboarding
- login
- registration
- splash workflow
- authentication gate

unless a real reference is supplied later.

---

# 3. HTML REFERENCES ARE DESIGN REFERENCES ONLY

The supplied HTML files are not production Android code.

Never use:

- WebView
- HTML UI
- Tailwind
- CSS
- JavaScript UI
- Material Symbols web fonts
- remote AIDA image URLs
- Google-hosted reference image URLs

Do not copy HTML directly into Android.

Translate the intended design and interactions into native
Jetpack Compose.

The JavaScript contained in the references is useful only for
understanding intended UI behavior.

For example:

- quantity changes
- cart removal
- Clear All
- promo code validation
- gallery selection
- favorite selection
- countdown
- Add to Cart feedback

must be recreated using Kotlin state.

---

# 4. DESIGN CONFLICT RESOLUTION

If the HTML and `Design.md` disagree:

Use the visual reference for:

- component hierarchy
- position
- size relationships
- image crop
- visible text
- active states

Use `Design.md` for:

- exact Android theme colors
- typography
- spacing tokens
- shapes
- architectural requirements

Do NOT copy Tailwind-generated colors merely because their token names
look similar to Material 3 names.

For example:

the Android primary color is the value defined by `Design.md`,
not whichever `primary` value happens to exist inside the HTML.

---

# 5. OFFICIAL LOCAL ASSET REGISTRY

The current project asset folder contains exactly these grounded files:

## `1.jpg`

Content:
Premium over-ear headphones on a light studio pedestal.

Use for:

- main headphone/product hero image
- Product Details gallery
- headphone product card where appropriate
- Cart headphone thumbnail where composition permits

Primary semantic role:

`IMG_PROD_HEADPHONES_MAIN`

---

## `2.jpg`

Content:
Smartwatch with dark strap on a desk.

Use for:

- smartwatch product
- Cart smartwatch item
- Flash Deal / product card when that product is shown

Semantic role:

`IMG_PROD_SMARTWATCH`

---

## `3.jpg`

Content:
Black true-wireless earbuds inside an open charging case.

Use for:

- earbuds / audio product
- Flash Deals
- Home product cards where the reference shows this product

Semantic role:

`IMG_PROD_EARBUDS`

Do not incorrectly use this as the over-ear headphone image.

---

## `4.jpg`

Content:
Pastel peach protective earbud case.

Use for:

- Silicone Protective Case
- Cart item
- related product presentation

Semantic role:

`IMG_PROD_CASE`

---

## `5.jpg`

Content:
Pastel mechanical keyboard on a desk.

Use for:

- Retro Mechanical Wireless Keyboard
- Best Sellers

Semantic role:

`IMG_PROD_KEYBOARD`

---

## `6.jpg`

Content:
Dusty-rose / pink insulated tumbler.

Use for:

- Stainless Steel Travel Mug / Tumbler
- Best Sellers

Semantic role:

`IMG_PROD_MUG`

---

## `7.jpg`

Content:
Hydrating serum / dropper skincare bottle.

Use for:

- Deep Hydrating Hyaluronic Serum
- Best Sellers

Semantic role:

`IMG_PROD_SERUM`

---

## `8.jpg`

Content:
Warm bedside/night lamp in a bedroom environment.

Use for:

- Modern Touch Rechargeable Bedside Lamp
- Best Sellers

Semantic role:

`IMG_PROD_LAMP`

---

## `9.jpg`

Content:
Close-up/detail view of the over-ear headphone earcup.

Use for:

- Product Details headphone gallery
- close-up/detail thumbnail

Semantic role:

`IMG_HEADPHONES_DETAIL`

Do not treat this as a separate unrelated product.

---

## `10.jpg`

Content:
Headphone carrying case with charging/audio cable.

Use for:

- Product Details headphone gallery
- accessory/package-content image

Semantic role:

`IMG_HEADPHONES_CASE`

Do not use it as the main product image.

---

## `11.jpg`

Content:
Lifestyle scene of a woman using/wearing headphones while working.

Use for:

- Product Details lifestyle gallery image
- headphone lifestyle context

Semantic role:

`IMG_HEADPHONES_LIFESTYLE`

Do not automatically use this image as the user's profile avatar.

---

# 6. PRODUCT DETAIL IMAGE GALLERY

For the primary headphone Product Detail reference, use the actual
available local gallery:

1. `1.jpg`
2. `9.jpg`
3. `10.jpg`
4. `11.jpg`

These four files form one coherent headphone gallery:

- main product view
- product detail
- carrying/accessory case
- lifestyle usage

The selected thumbnail must update the large hero image.

Do not:

- duplicate the same bitmap four times;
- download the remote HTML gallery images;
- invent extra gallery images;
- use unrelated products as gallery thumbnails.

Use a deterministic selected-gallery-index in
`ProductDetailsUiState`.

---

# 7. MISSING BRAND LOGO RULE

The supplied asset-folder screenshot does not establish a dedicated
local brand-logo image.

Therefore:

- do not download the HTML logo;
- do not use the AIDA/Google-hosted logo URL;
- do not invent a photographic logo file;
- do not introduce a fictional brand name.

Where the reference requires a small commerce-brand symbol, use an
appropriate native Material icon or local vector treatment, for example
a shopping bag / local mall symbol.

If a dedicated real logo asset is supplied later, replace the temporary
native symbol with that real asset.

---

# 8. MISSING PROFILE AVATAR RULE

The supplied assets do not establish a dedicated portrait/avatar file
matching the remote profile avatar in the HTML.

Therefore:

- do not download the HTML avatar;
- do not retain its remote URL;
- do not incorrectly use `11.jpg` as the profile avatar merely because
  a woman appears in it.

Until a real avatar file is supplied, use a deterministic native
placeholder such as:

- initials
- neutral profile icon
- styled avatar circle

while preserving the dimensions and layout of the reference.

Example initials may reflect the deterministic demo name when needed.

---

# 9. PROMOTIONAL BANNER ASSET RULE

There is no need for a bitmap asset for the main promotional banner.

Build the reference banner natively using Compose:

- gradient background
- rounded shape
- promo badge
- discount headline
- promo code
- local countdown
- pager indicators
- CTA

Use the official crimson theme colors.

Do not search for or download a banner image.

---

# 10. ASSET IMPLEMENTATION RULES

Before using any image:

1. verify the real filename;
2. map it to the correct semantic product;
3. preserve its intended crop/aspect ratio.

Do not rename assets unnecessarily.

If the existing Android project expects drawable resource names,
copy/import them using stable Android-safe names such as:

- `product_headphones_main`
- `product_smartwatch`
- `product_earbuds`
- `product_case_peach`
- `product_keyboard`
- `product_tumbler`
- `product_serum`
- `product_lamp`
- `headphones_detail`
- `headphones_case`
- `headphones_lifestyle`

However:

the files `1.jpg` ... `11.jpg` are the source-of-truth files.

Do not invent additional photographic assets.

Never leave these URLs in production source:

- `lh3.googleusercontent.com`
- `aida-public`
- temporary generated image URLs
- placeholder image websites

---

# 11. NATIVE ANDROID ARCHITECTURE

Use:

- Kotlin
- Jetpack Compose
- Material 3
- Single Activity
- MVVM
- UDF
- immutable UiState
- StateFlow
- `collectAsStateWithLifecycle()`
- Compose Navigation

Prefer:

`Screen(state, onAction)`

Example:

```kotlin
@Composable
fun CartScreen(
    state: CartUiState,
    onAction: (CartAction) -> Unit
)