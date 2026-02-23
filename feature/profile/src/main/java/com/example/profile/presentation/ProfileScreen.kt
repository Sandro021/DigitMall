package com.example.profile.presentation


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.profile.presentation.contract.ProfileEffect
import com.example.profile.presentation.contract.ProfileIntent
import com.example.ui.theme.MallTheme // Ensure this import matches your project structure

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MallTheme.colors.background, // Using your custom background
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        color = MallTheme.colors.textPrimary // Custom text color
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MallTheme.colors.background
                )
            )
        }
    ) { paddingValues ->

        if (state.isLoading && state.profile == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MallTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MallTheme.colors.brandSecondary) // Standout loading color
            }
            return@Scaffold
        }

        val profile = state.profile ?: return@Scaffold

        var firstName by remember(profile) { mutableStateOf(profile.firstName) }
        var lastName by remember(profile) { mutableStateOf(profile.lastName) }
        var username by remember(profile) { mutableStateOf(profile.username) }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

        val photoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }

        // Custom colors for all TextFields
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MallTheme.colors.textPrimary,
            unfocusedTextColor = MallTheme.colors.textPrimary,
            disabledTextColor = MallTheme.colors.textSecondary,
            focusedBorderColor = MallTheme.colors.brandPrimary,
            unfocusedBorderColor = MallTheme.colors.textSecondary,
            focusedLabelColor = MallTheme.colors.brandPrimary,
            unfocusedLabelColor = MallTheme.colors.textSecondary,
            disabledLabelColor = MallTheme.colors.textSecondary,
            cursorColor = MallTheme.colors.brandPrimary,
            focusedContainerColor = MallTheme.colors.surface,
            unfocusedContainerColor = MallTheme.colors.surface
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MallTheme.colors.background)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- AVATAR SECTION ---
            Box(
                modifier = Modifier.padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                val imageToShow = selectedImageUri ?: profile.avatarUrl

                AsyncImage(
                    model = imageToShow,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MallTheme.colors.surface) // Fallback background if image is null
                        .clickable {
                            photoPickerLauncher.launch(
                                androidx.activity.result.PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                )

                // Edit badge
                Surface(
                    shape = CircleShape,
                    color = MallTheme.colors.surface,
                    modifier = Modifier.size(36.dp),
                    shadowElevation = 4.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Avatar",
                        tint = MallTheme.colors.brandPrimary, // Custom brand color
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxSize()
                    )
                }
            }

            // --- FORM FIELDS ---
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // READ-ONLY Email
            OutlinedTextField(
                value = profile.email,
                onValueChange = { },
                label = { Text("Email") },
                enabled = false,
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            // --- SAVE BUTTON ---
            Button(
                onClick = {
                    viewModel.handleIntent(
                        ProfileIntent.UpdateProfile(
                            firstName = firstName,
                            lastName = lastName,
                            username = username,
                            newAvatarUriString = selectedImageUri?.toString()
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MallTheme.colors.brandPrimary,
                    contentColor = MallTheme.colors.onBrandPrimary,
                    disabledContainerColor = MallTheme.colors.brandPrimary.copy(alpha = 0.5f),
                    disabledContentColor = MallTheme.colors.onBrandPrimary.copy(alpha = 0.5f)
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MallTheme.colors.onBrandPrimary // White/Black spinner depending on theme
                    )
                } else {
                    Text("Save Changes")
                }
            }
        }
    }
}