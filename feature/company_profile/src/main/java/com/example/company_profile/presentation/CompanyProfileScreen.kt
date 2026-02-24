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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.company_profile.presentation.contract.CompanyEffect
import com.example.company_profile.presentation.contract.CompanyIntent
import com.example.company_profile.presentation.model.ShopItemUiModel
import com.example.company_profile.presentation.model.ShopUiModel
import kotlin.collections.isNotEmpty


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
    var itemCategoryInput by remember { mutableStateOf("") }
    var showAddItemDialog by remember { mutableStateOf(false) }
    var selectedShopIdForUpload by remember { mutableStateOf<String?>(null) }
    var itemNameInput by remember { mutableStateOf("") }
    var itemPriceInput by remember { mutableStateOf("") }
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
                    category = itemCategoryInput.ifEmpty { "General" },
                    sizes = parsedSizes
                )
            )
            showAddItemDialog = false
            selectedShopIdForUpload = null
            itemNameInput = ""
            itemPriceInput = ""
            itemCategoryInput = ""
            itemSizesInput = ""
            showAddItemDialog = false
            selectedShopIdForUpload = null
            itemNameInput = ""
            itemPriceInput = ""
        }
    }

    val reelVideoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.handleIntent(CompanyIntent.UploadReel(it.toString(), "New Reel!"))
        }
    }

    // --- Effects ---
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

    // --- UI Content ---
    Scaffold(
        topBar = { TopAppBar(title = { Text("Company Dashboard") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("My Shops") })
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("My Reels") })
            }

            if (state.isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

            if (selectedTabIndex == 0) {
                // --- SHOPS TAB ---
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Button(
                            onClick = { showCreateShopDialog = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Create New Shop")
                        }
                    }
                    items(state.shops) { shop ->
                        ShopCard(
                            shop = shop,
                            items = state.shopItems[shop.id] ?: emptyList(),
                            onAddItemClicked = {
                                selectedShopIdForUpload = shop.id
                                showAddItemDialog = true
                            })
                    }
                }
            } else {
                // --- REELS TAB ---
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = { reelVideoPicker.launch("video/*") }) { Text("Upload Reel") }
                }
            }
        }

        // --- DIALOGS ---

        // 1. Create Shop Dialog
        if (showCreateShopDialog) {
            AlertDialog(
                onDismissRequest = { showCreateShopDialog = false },
                title = { Text("Create New Shop") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = shopNameInput,
                            onValueChange = { shopNameInput = it },
                            label = { Text("Shop Name") })
                        OutlinedTextField(
                            value = shopDescInput,
                            onValueChange = { shopDescInput = it },
                            label = { Text("Description") })
                        OutlinedTextField(
                            value = shopLocInput,
                            onValueChange = { shopLocInput = it },
                            label = { Text("Location") })

                        // Image Picker Button & Preview
                        Button(onClick = {
                            shopImagePicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }) {
                            Text(if (selectedShopImageUri == null) "Pick Shop Cover Image" else "Change Image")
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
                                // Reset inputs
                                shopNameInput = ""; shopDescInput = ""; shopLocInput =
                                    ""; selectedShopImageUri = null
                            } else {
                                Toast.makeText(
                                    context,
                                    "Name and Image required",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) { Text("Create") }
                },
                dismissButton = {
                    TextButton(onClick = { showCreateShopDialog = false }) { Text("Cancel") }
                }
            )
        }

        // 2. Add Item Dialog
        if (showAddItemDialog) {
            AlertDialog(
                onDismissRequest = { showAddItemDialog = false },
                title = { Text("Add Item details") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = itemNameInput,
                            onValueChange = { itemNameInput = it },
                            label = { Text("Item Name") }
                        )
                        OutlinedTextField(
                            value = itemPriceInput,
                            onValueChange = { itemPriceInput = it },
                            label = { Text("Price (e.g., 19.99)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                        OutlinedTextField(
                            value = itemCategoryInput,
                            onValueChange = { itemCategoryInput = it },
                            label = { Text("Category (e.g., Shoes, Electronics)") }
                        )
                        OutlinedTextField(
                            value = itemSizesInput,
                            onValueChange = { itemSizesInput = it },
                            label = { Text("Sizes (comma separated: 32, 34, 36)") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (itemNameInput.isNotEmpty() && itemPriceInput.isNotEmpty()) {
                                itemImagePicker.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    "Fill name and price first",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) { Text("Next: Pick Image") }
                },
                dismissButton = {
                    TextButton(onClick = { showAddItemDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}

// Helper Composable for Shop Card
@Composable
fun ShopCard(
    shop: ShopUiModel,
    items: List<ShopItemUiModel>,
    onAddItemClicked: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Column {
            // Shop Cover Image
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = shop.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = shop.location, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onAddItemClicked) { Text("Add Item to ${shop.name}") }
                if (items.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Items:", fontWeight = FontWeight.SemiBold)
                    items.forEach { item -> Text("- ${item.name} (${item.formattedPrice})") }
                }
            }
        }
    }
}