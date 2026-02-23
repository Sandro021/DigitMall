package com.example.cart.presentation


import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.cart.presentation.contract.CartIntent
import com.example.cart.presentation.model.CartItemUi
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.Radius
import com.example.ui.theme.Spacing

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onCheckOutClicked: (Float) -> Unit,
    isPaymentSuccessful: Boolean = false,
    onClearCartComplete: () -> Unit = {},
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(isPaymentSuccessful) {
        if (isPaymentSuccessful) {
            viewModel.handleIntent(CartIntent.ClearCart)
            onClearCartComplete()
        }
    }
    Scaffold(
        containerColor = MallTheme.colors.background,
        topBar = {
            TopAppBar(
                title = { Text("My Cart") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MallTheme.colors.background,
                    titleContentColor = MallTheme.colors.textPrimary,
                    navigationIconContentColor = MallTheme.colors.textPrimary,
                    actionIconContentColor = MallTheme.colors.textPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (state.cartItems.isNotEmpty()) {
                Surface(
                    shadowElevation = 8.dp,
                    color = MallTheme.colors.surface
                ) {
                    Column(modifier = Modifier.padding(Padding.padding16)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Total:",
                                style = MaterialTheme.typography.titleMedium,
                                color = MallTheme.colors.textPrimary
                            )
                            Text(
                                "$${String.format("%.2f", state.totalPrice)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MallTheme.colors.brandPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(Spacing.space16))
                        Button(
                            onClick = { onCheckOutClicked(state.totalPrice.toFloat()) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = Radius.radius8,

                            colors = ButtonDefaults.buttonColors(
                                containerColor = MallTheme.colors.brandPrimary,
                                contentColor = MallTheme.colors.onBrandPrimary
                            )
                        ) {
                            Text("Proceed to Checkout")
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MallTheme.colors.brandPrimary
                )
            }

            if (state.cartItems.isEmpty() && !state.isLoading) {
                Text(
                    "Your cart is empty",
                    modifier = Modifier.align(Alignment.Center),
                    color = MallTheme.colors.textSecondary
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(Padding.padding16),
                    verticalArrangement = Arrangement.spacedBy(Spacing.space16)
                ) {
                    items(state.cartItems) { item ->
                        CartItemRow(
                            item,
                            onDeleteClick = { viewModel.handleIntent(CartIntent.RemoveItem(item.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItemUi,
    onDeleteClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MallTheme.colors.surface
        ),
        shape = Radius.radius12
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
                    .size(80.dp)
                    .clip(Radius.radius8),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(Spacing.space16))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MallTheme.typography.productTitle,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = MallTheme.colors.textPrimary
                )
                Text(
                    text = "Size: ${item.size}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MallTheme.colors.textSecondary
                )
                Spacer(modifier = Modifier.height(Spacing.space4))
                Text(
                    text = "$${item.singlePriceDisplay}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MallTheme.colors.brandPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "x${item.quantity}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MallTheme.colors.textPrimary
            )

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MallTheme.colors.error
                )
            }
        }
    }
}