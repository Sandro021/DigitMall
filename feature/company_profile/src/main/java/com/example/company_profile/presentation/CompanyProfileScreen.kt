package com.example.company_profile.presentation


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.company_profile.R
import com.example.company_profile.presentation.contract.CompanyEffect
import com.example.company_profile.presentation.contract.CompanyIntent
import com.example.company_profile.presentation.model.ShopItemUiModel
import com.example.company_profile.presentation.model.ShopUiModel
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyProfileScreen(
    onBack: () -> Unit,
    viewModel: CompanyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    var showCreateShopDialog by remember { mutableStateOf(false) }
    var shopNameInput by remember { mutableStateOf("") }
    var shopDescInput by remember { mutableStateOf("") }
    var shopLocInput by remember { mutableStateOf("") }
    var selectedShopImageUri by remember { mutableStateOf<Uri?>(null) }

    var showAddItemDialog by remember { mutableStateOf(false) }
    var selectedShopIdForUpload by remember { mutableStateOf<String?>(null) }
    var itemNameInput by remember { mutableStateOf("") }
    var itemPriceInput by remember { mutableStateOf("") }
    var itemCategoryInput by remember { mutableStateOf("") }
    var itemSizesInput by remember { mutableStateOf("") }

    val shopImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> selectedShopImageUri = uri }

    val itemImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null && selectedShopIdForUpload != null) {
            val parsedSizes = itemSizesInput
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            viewModel.handleIntent(
                CompanyIntent.AddShopItem(
                    shopId = selectedShopIdForUpload!!,
                    name = itemNameInput,
                    price = itemPriceInput,
                    imageUri = uri.toString(),
                    category = itemCategoryInput.ifEmpty { context.getString(R.string.general) },
                    sizes = parsedSizes
                )
            )

            showAddItemDialog = false
            selectedShopIdForUpload = null
            itemNameInput = ""
            itemPriceInput = ""
            itemCategoryInput = ""
            itemSizesInput = ""
        }
    }

    val reelVideoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.handleIntent(CompanyIntent.UploadReel(it.toString(), "New Reel!"))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CompanyEffect.ShowToast -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(
        containerColor = MallTheme.colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.company_dashboard),
                        color = MallTheme.colors.textPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MallTheme.colors.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MallTheme.colors.surface,
                contentColor = MallTheme.colors.brandPrimary
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = {
                        Text(
                            text = stringResource(R.string.my_shops),
                            color = if (selectedTabIndex == 0) MallTheme.colors.brandPrimary else MallTheme.colors.textSecondary
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = {
                        Text(
                            text = stringResource(R.string.my_reels),
                            color = if (selectedTabIndex == 1) MallTheme.colors.brandPrimary else MallTheme.colors.textSecondary
                        )
                    }
                )
            }

            if (state.isLoading) LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MallTheme.colors.brandSecondary
            )

            if (selectedTabIndex == 0) {
                LazyColumn(
                    contentPadding = PaddingValues(Padding.padding16),
                    verticalArrangement = Arrangement.spacedBy(Spacing.space16)
                ) {
                    item {
                        Button(
                            onClick = { showCreateShopDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MallTheme.colors.brandPrimary)
                        ) {
                            Text(
                                stringResource(R.string.create_new_shop),
                                color = MallTheme.colors.onBrandPrimary
                            )
                        }
                    }
                    items(state.shops) { shop ->
                        ShopCard(
                            shop = shop,
                            items = state.shopItems[shop.id] ?: emptyList(),
                            onAddItemClicked = {
                                selectedShopIdForUpload = shop.id
                                showAddItemDialog = true
                            }
                        )
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = { reelVideoPicker.launch(context.getString(R.string.video)) },
                        colors = ButtonDefaults.buttonColors(containerColor = MallTheme.colors.brandPrimary)
                    ) {
                        Text(
                            stringResource(R.string.upload_reel),
                            color = MallTheme.colors.onBrandPrimary
                        )
                    }
                }
            }
        }

        if (showCreateShopDialog) {
            AlertDialog(
                onDismissRequest = { showCreateShopDialog = false },
                containerColor = MallTheme.colors.surface,
                titleContentColor = MallTheme.colors.textPrimary,
                textContentColor = MallTheme.colors.textPrimary,
                title = { Text(stringResource(R.string.create_new_shop)) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = shopNameInput,
                            onValueChange = { shopNameInput = it },
                            label = { Text(stringResource(R.string.shop_name)) }
                        )
                        OutlinedTextField(
                            value = shopDescInput,
                            onValueChange = { shopDescInput = it },
                            label = { Text(stringResource(R.string.description)) }
                        )
                        OutlinedTextField(
                            value = shopLocInput,
                            onValueChange = { shopLocInput = it },
                            label = { Text(stringResource(R.string.location)) }
                        )

                        Button(
                            onClick = {
                                shopImagePicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MallTheme.colors.brandSecondary)
                        ) {
                            Text(
                                text = if (selectedShopImageUri == null) "Pick Shop Cover Image" else stringResource(
                                    R.string.change_image
                                ),
                                color = MallTheme.colors.onBrandPrimary
                            )
                        }
                        if (selectedShopImageUri != null) {
                            AsyncImage(
                                model = selectedShopImageUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MallTheme.colors.brandPrimary),
                        onClick = {
                            if (selectedShopImageUri != null && shopNameInput.isNotEmpty()) {
                                viewModel.handleIntent(
                                    CompanyIntent.CreateShop(
                                        shopNameInput,
                                        shopDescInput,
                                        shopLocInput,
                                        selectedShopImageUri!!.toString()
                                    )
                                )
                                showCreateShopDialog = false
                                shopNameInput = ""
                                shopDescInput = ""
                                shopLocInput = ""
                                selectedShopImageUri = null
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.name_and_image_required),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) { Text("Create", color = MallTheme.colors.onBrandPrimary) }
                },
                dismissButton = {
                    TextButton(onClick = { showCreateShopDialog = false }) {
                        Text(
                            stringResource(R.string.cancel),
                            color = MallTheme.colors.textSecondary
                        )
                    }
                }
            )
        }

        if (showAddItemDialog) {
            AlertDialog(
                onDismissRequest = { showAddItemDialog = false },
                containerColor = MallTheme.colors.surface,
                titleContentColor = MallTheme.colors.textPrimary,
                textContentColor = MallTheme.colors.textPrimary,
                title = { Text(stringResource(R.string.add_item_details)) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = itemNameInput,
                            onValueChange = { itemNameInput = it },
                            label = { Text(stringResource(R.string.item_name)) }
                        )
                        OutlinedTextField(
                            value = itemPriceInput,
                            onValueChange = { itemPriceInput = it },
                            label = { Text(stringResource(R.string.price_e_g_19_99)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                        OutlinedTextField(
                            value = itemCategoryInput,
                            onValueChange = { itemCategoryInput = it },
                            label = { Text(stringResource(R.string.category_e_g_shoes_shirt)) }
                        )
                        OutlinedTextField(
                            value = itemSizesInput,
                            onValueChange = { itemSizesInput = it },
                            label = { Text(stringResource(R.string.sizes_comma_separated_32_34_36)) }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MallTheme.colors.brandPrimary),
                        onClick = {
                            if (itemNameInput.isNotEmpty() && itemPriceInput.isNotEmpty()) {
                                itemImagePicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.fill_name_and_price_first),
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    ) {
                        Text(
                            stringResource(R.string.next_pick_image),
                            color = MallTheme.colors.onBrandPrimary
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddItemDialog = false }) {
                        Text(
                            stringResource(R.string.cancel),
                            color = MallTheme.colors.textSecondary
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ShopCard(
    shop: ShopUiModel,
    items: List<ShopItemUiModel>,
    onAddItemClicked: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MallTheme.colors.surface)
    ) {
        Column {
            if (shop.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = shop.imageUrl,
                    contentDescription = "Shop Cover",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(Padding.padding16)) {
                Text(
                    text = shop.name,
                    fontWeight = FontWeight.Bold,
                    color = MallTheme.colors.textPrimary
                )
                Text(
                    text = shop.location,
                    color = MallTheme.colors.textSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onAddItemClicked,
                    colors = ButtonDefaults.buttonColors(containerColor = MallTheme.colors.brandSecondary)
                ) {
                    Text("Add Item to ${shop.name}", color = MallTheme.colors.onBrandPrimary)
                }
                if (items.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Items:",
                        fontWeight = FontWeight.SemiBold,
                        color = MallTheme.colors.textPrimary
                    )
                    items.forEach { item ->
                        Text(
                            text = "- ${item.name} ($${item.formattedPrice})",
                            color = MallTheme.colors.textSecondary
                        )
                    }
                }
            }
        }
    }
}