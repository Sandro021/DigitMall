package com.example.item_feed.presentation




import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.item_feed.domain.model.SortOrder
import com.example.item_feed.presentation.contract.AllItemsIntent
import com.example.item_feed.presentation.model.AllItemUi
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.Radius
import com.example.ui.theme.Spacing
import com.example.ui.theme.common.LogoLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllItemsFeedScreen(
    viewModel: AllItemsViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MallTheme.colors.background,
        topBar = {
            TopAppBar(
                title = { Text("All Items", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MallTheme.colors.background,
                    titleContentColor = MallTheme.colors.textPrimary,
                    actionIconContentColor = MallTheme.colors.textPrimary
                ),
                actions = {
                    Box {
                        IconButton(onClick = { showSortMenu = true }) {
                            Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort")
                        }
                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false },
                            modifier = Modifier.background(MallTheme.colors.surface)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Default", color = MallTheme.colors.textPrimary) },
                                onClick = {
                                    viewModel.handleIntent(AllItemsIntent.ChangeSortOrder(SortOrder.NONE))
                                    showSortMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Price: Low to High",
                                        color = MallTheme.colors.textPrimary
                                    )
                                },
                                onClick = {
                                    viewModel.handleIntent(AllItemsIntent.ChangeSortOrder(SortOrder.PRICE_LOW_TO_HIGH))
                                    showSortMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Price: High to Low",
                                        color = MallTheme.colors.textPrimary
                                    )
                                },
                                onClick = {
                                    viewModel.handleIntent(AllItemsIntent.ChangeSortOrder(SortOrder.PRICE_HIGH_TO_LOW))
                                    showSortMenu = false
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            if (state.categories.size > 1) {
                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = Padding.padding16,
                        vertical = Padding.padding8
                    ),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.space8)
                ) {
                    items(state.categories) { category ->
                        val isSelected = state.selectedCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                viewModel.handleIntent(
                                    AllItemsIntent.SelectCategory(
                                        category
                                    )
                                )
                            },
                            label = { Text(category) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MallTheme.colors.brandPrimary,
                                selectedLabelColor = MallTheme.colors.onBrandPrimary, // Usually white
                                labelColor = MallTheme.colors.textPrimary
                            )
                        )
                    }
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LogoLoader(
                        modifier = Modifier.align(Alignment.Center),
                        size = 92.dp,
                        logoRes = com.example.ui.R.drawable.logo
                    )
                }
            } else if (state.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.error}", color = MallTheme.colors.error)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(Padding.padding16),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.space16),
                    verticalArrangement = Arrangement.spacedBy(Spacing.space16)
                ) {
                    items(state.items) { item ->
                        AllItemGridCard(
                            item = item,
                            onClick = { onItemClick(item.id) },
                            onAddToCartClick = { clickedItem ->
                                // NEW: Send the intent to your ViewModel
                                viewModel.handleIntent(AllItemsIntent.AddToCart(clickedItem))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AllItemGridCard(
    item: AllItemUi,
    onClick: () -> Unit,
    onAddToCartClick: (AllItemUi) -> Unit // NEW: Added click listener parameter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = Radius.radius12,
        colors = CardDefaults.cardColors(containerColor = MallTheme.colors.surface)
    ) {
        Column {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            Column(modifier = Modifier.padding(Padding.padding12)) {
                Text(
                    text = item.category.uppercase(),
                    style = MallTheme.typography.priceSmall,
                    color = MallTheme.colors.textSecondary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.name,
                    style = MallTheme.typography.productTitle,
                    fontWeight = FontWeight.Bold,
                    color = MallTheme.colors.textPrimary,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(Spacing.space4))

                // NEW: Row to hold Price on the left, Cart icon on the right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${item.displayPrice}",
                        color = MallTheme.colors.brandPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    IconButton(
                        onClick = { onAddToCartClick(item) },
                        modifier = Modifier.size(32.dp) // Keeps the icon compact
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Add to Cart",
                            tint = MallTheme.colors.brandPrimary
                        )
                    }
                }
            }
        }
    }
}