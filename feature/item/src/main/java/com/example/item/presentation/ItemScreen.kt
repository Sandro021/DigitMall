package com.example.item.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ui.theme.MallTheme
import com.example.ui.theme.common.LogoLoader

@Composable
fun ItemScreen(
    state: ItemContract.State,
    onEvent: (ItemContract.Event) -> Unit,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LogoLoader(
                    modifier = Modifier.align(Alignment.Center),
                    size = 92.dp,
                    logoRes = com.example.ui.R.drawable.logo
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    androidx.compose.material3.IconButton(onClick = onBack) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MallTheme.colors.textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = state.item?.name ?: "",
                        style = MallTheme.typography.heroBanner,
                        color = MallTheme.colors.textPrimary
                    )
                }

                AsyncImage(
                    model = state.item?.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.item?.category ?: "",
                    color = MallTheme.colors.textSecondary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        state.item?.sizes?.forEach { size ->
                            val selected = state.selectedSize == size
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (selected) MallTheme.colors.brandPrimary
                                        else MallTheme.colors.surface
                                    )
                                    .clickable { onEvent(ItemContract.Event.SelectSize(size)) }
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text = size,
                                    color = if (selected) MallTheme.colors.onBrandPrimary
                                    else MallTheme.colors.textPrimary
                                )
                            }
                        }
                    }

                    Text(
                        text = "${state.item?.price} $",
                        style = MallTheme.typography.heroBanner,
                        color = MallTheme.colors.brandPrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = { onEvent(ItemContract.Event.AddToCart) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = state.selectedSize != null
            ) {
                Text(
                    "Add To Cart",
                    color = MallTheme.colors.onBrandPrimary
                )
            }
        }
    }
}