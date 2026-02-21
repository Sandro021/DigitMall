package com.example.item_list.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.item_list.R
import com.example.item_list.presentation.contract.ItemListIntent
import com.example.item_list.presentation.model.ItemUi
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.Radius
import com.example.ui.theme.Spacing
import com.example.ui.theme.common.LogoLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    shopId: String,
    viewModel: ItemListViewModel = hiltViewModel(),
    onCartClick: () -> Unit,
    onBackClick: () -> Unit // Note: You might want to use this in navigationIcon
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(shopId) {
        viewModel.handleIntent(ItemListIntent.LoadItems(shopId))
    }

    Scaffold(
        // 1. Set Screen Background
        containerColor = MallTheme.colors.background,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.shop_items)) },
                // 2. Set App Bar Colors
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MallTheme.colors.background,
                    titleContentColor = MallTheme.colors.textPrimary,
                    actionIconContentColor = MallTheme.colors.textPrimary,
                    navigationIconContentColor = MallTheme.colors.textPrimary
                ),
                actions = {
                    IconButton(onClick = onCartClick) {
                        BadgedBox(badge = {
                            if (state.cartCount > 0) {
                                Badge(
                                    containerColor = MallTheme.colors.brandSecondary,
                                    contentColor = Color.White
                                ) {
                                    Text("${state.cartCount}")
                                }
                            }
                        }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = stringResource(R.string.cart)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            if (state.categories.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = Padding.padding16,
                        vertical = Padding.padding8
                    ),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.space8)
                ) {
                    items(state.categories) { category ->
                        FilterChip(
                            selected = state.selectedCategory == category,
                            onClick = {
                                viewModel.handleIntent(ItemListIntent.SelectCategory(category))
                            },
                            label = { Text(category) }
                            // FilterChip defaults usually adapt well,
                            // but you can customize colors here if needed.
                        )
                    }
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LogoLoader(
                        size = 92.dp,
                        logoRes = com.example.ui.R.drawable.logo
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(Padding.padding16),
                    verticalArrangement = Arrangement.spacedBy(Spacing.space16)
                ) {
                    items(state.displayedItems) { item ->
                        ShopItemRow(
                            item = item,
                            onAddToCart = { size ->
                                viewModel.handleIntent(ItemListIntent.AddToCart(item, size))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShopItemRow(
    item: ItemUi,
    onAddToCart: (String) -> Unit
) {
    // Default to first size if available
    var selectedSize by remember { mutableStateOf(item.sizes.firstOrNull() ?: "32") }

    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        // 3. Fix: Use Theme Surface instead of Color.White
        colors = CardDefaults.cardColors(
            containerColor = MallTheme.colors.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.padding12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(Radius.radius8),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(Spacing.space16))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    style = MallTheme.typography.productTitle,
                    color = MallTheme.colors.textPrimary // Explicit text color
                )
                Text(
                    text = "$${item.displayPrice}",
                    color = MallTheme.colors.textPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(Spacing.space8))

                // Size Label
                Text(
                    text = stringResource(R.string.size, selectedSize),
                    style = MallTheme.typography.priceSmall,
                    color = MallTheme.colors.textSecondary // Use secondary for label
                )

                Spacer(modifier = Modifier.height(Spacing.space4))

                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.space4)) {
                    item.sizes.forEach { size ->
                        val isSelected = selectedSize == size
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    // 4. Fix: Use textSecondary for unselected border (visible in dark mode)
                                    color = if (isSelected) MallTheme.colors.brandPrimary else MallTheme.colors.textSecondary,
                                    shape = Radius.radius4
                                )
                                .background(
                                    // Slight tint for selected state
                                    color = if (isSelected) MallTheme.colors.brandPrimary.copy(alpha = 0.1f) else Color.Transparent
                                )
                                .clip(Radius.radius4) // Good practice to clip clickable area
                                .clickable { selectedSize = size }
                                .padding(horizontal = Padding.padding8, vertical = Padding.padding4)
                        ) {
                            Text(
                                text = size,
                                style = MallTheme.typography.priceSmall,
                                // Highlight selected text with Brand color, otherwise normal text
                                color = if (isSelected) MallTheme.colors.brandPrimary else MallTheme.colors.textPrimary
                            )
                        }
                    }
                }
            }

            IconButton(
                onClick = { onAddToCart(selectedSize) },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_to_cart),
                    tint = MallTheme.colors.brandPrimary
                )
            }
        }
    }
}