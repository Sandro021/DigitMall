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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.profile.R
import com.example.profile.presentation.contract.ProfileEffect
import com.example.profile.presentation.contract.ProfileIntent
import com.example.ui.theme.MallTheme
import com.example.ui.theme.Padding
import com.example.ui.theme.common.LogoLoader
import com.google.firebase.auth.FirebaseAuth

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
        containerColor = MallTheme.colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.edit_profile),
                        color = MallTheme.colors.textPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MallTheme.colors.background
                ),
                actions = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = MallTheme.colors.brandPrimary
                        )
                    }
                }
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
                CircularProgressIndicator(color = MallTheme.colors.brandSecondary)
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
                .padding(Padding.padding16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.padding(bottom = Padding.padding32),
                contentAlignment = Alignment.BottomEnd
            ) {
                val imageToShow = selectedImageUri ?: profile.avatarUrl

                AsyncImage(
                    model = imageToShow,
                    contentDescription = stringResource(R.string.profile_picture),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MallTheme.colors.surface)
                        .clickable {
                            photoPickerLauncher.launch(
                                androidx.activity.result.PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                )

                Surface(
                    shape = CircleShape,
                    color = MallTheme.colors.surface,
                    modifier = Modifier.size(36.dp),
                    shadowElevation = 4.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_avatar),
                        tint = MallTheme.colors.brandPrimary,
                        modifier = Modifier
                            .padding(Padding.padding6)
                            .fillMaxSize()
                    )
                }
            }

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(stringResource(R.string.first_name)) },
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(stringResource(R.string.last_name)) },
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = profile.email,
                onValueChange = { },
                label = { Text(stringResource(R.string.email)) },
                enabled = false,
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

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
                    LogoLoader(
                        size = 24.dp,
                        logoRes = com.example.ui.R.drawable.logo
                    )
                } else {
                    Text(stringResource(R.string.save_changes))
                }
            }
        }
    }
}