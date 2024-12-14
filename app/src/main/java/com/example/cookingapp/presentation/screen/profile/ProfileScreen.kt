@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cookingapp.presentation.screen.profile

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cookingapp.R
import com.example.cookingapp.model.AboutItem
import com.example.cookingapp.presentation.components.MainBoxShape
import com.example.cookingapp.utils.Constants.TAG
import whiteMba3bas

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    onNavigateToProfileSettings: (userId: String?, userPhoto: Uri?, userName: String?, userEmail: String?) -> Unit,
    onNavigateToYourRecipes: () -> Unit = {},
    onNavigateToLogOut: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    ProfileScreenContent(
        modifier = modifier,
        uiState = uiState,
        onItemClicked = { index ->
            when (index) {
                0 -> onNavigateToProfileSettings(
                    uiState.userId,
                    uiState.userPhoto,
                    uiState.userName,
                    uiState.userEmail
                )

                1 -> onNavigateToYourRecipes()
                2 -> {
                    viewModel.updateIsShowBottomSheet(value = true)
                }

                3 -> {
                    viewModel.updateIsShowAlertDialog(value = true)
                }
            }
        },
        onEditIconClicked = {
            onNavigateToProfileSettings(
                uiState.userId,
                uiState.userPhoto,
                uiState.userName,
                uiState.userEmail
            )
        },
        sheetState = sheetState,
        onDismissRequest = { viewModel.updateIsShowBottomSheet(value = false) },
        onSheetItemClicked = { index ->
            when (index) {
                0 -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.aboutItems[0].url))
                    context.startActivity(intent)
                }

                1 -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.aboutItems[1].url))
                    context.startActivity(intent)
                }

                2 -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.aboutItems[2].url))
                    context.startActivity(intent)
                }

                3 -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.aboutItems[3].url))
                    context.startActivity(intent)
                }

                4 -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.aboutItems[4].url))
                    context.startActivity(intent)
                }

                5 -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.aboutItems[5].url))
                    context.startActivity(intent)
                }

                else -> {}

            }
        },
        onDismissDialogRequest = { viewModel.updateIsShowAlertDialog(value = false) },
        onConfirmation = {
            viewModel.updateIsShowAlertDialog(value = false)
            onNavigateToLogOut()
            viewModel.isLogoutSuccessful()
        }
    )
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    uiState: ProfileScreenUiState,
    onItemClicked: (Int) -> Unit = {},
    onEditIconClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState,
    onSheetItemClicked: (Int) -> Unit,
    onDismissDialogRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileScreenHeader()
        Spacer(modifier = Modifier.height(16.dp))
        ProfileScreenUserInfo(
            userPhoto = uiState.userPhoto,
            userName = uiState.userName ?: "",
            userEmail = uiState.userEmail ?: "",
            onEditIconClicked = onEditIconClicked
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileScreenBody(
            profileItemsMenu = listOf(
                "Profile settings",
                "Your recipes",
                "About",
                "Log out"
            ),
            onItemClicked = onItemClicked
        )
        ProfileScreenAboutBottomSheet(
            sheetState = sheetState,
            isShowBottomSheet = uiState.isShowBottomSheet,
            onDismissRequest = onDismissRequest,
            aboutItems = uiState.aboutItems,
            onSheetItemClicked = onSheetItemClicked
        )
        ProfileScreenLogoutAlertDialog(
            onDismissRequest = onDismissDialogRequest,
            onConfirmation = onConfirmation,
            dialogTitle = "Log out",
            dialogText = "Are you sure you want to log out?",
            icon = Icons.AutoMirrored.Filled.Logout,
            isShowAlertDialog = uiState.isOpenAlertDialog
        )
    }
}

@Composable
fun ProfileScreenHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProfileScreenUserInfo(
    modifier: Modifier = Modifier,
    userPhoto: Uri? = null,
    userName: String = "",
    userEmail: String = "",
    onEditIconClicked: () -> Unit = {},
    showTheImageCover: Boolean = false,
    onCoverClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ProfileImageBox(
            showTheImageCover = showTheImageCover,
            onCoverClicked = onCoverClicked,
            userPhoto = userPhoto
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontWeight = FontWeight.Normal
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = {
            onEditIconClicked()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = "love",
                tint = Color.Black
            )
        }

    }
}

@Composable
fun ProfileImageBox(
    modifier: Modifier = Modifier,
    showTheImageCover: Boolean = false,
    onCoverClicked: () -> Unit = {},
    userPhoto: Any? = null,
) {
    BoxWithConstraints(
        modifier = modifier
            .width(100.dp)
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        val weight = maxWidth
        val height = maxHeight
        val blackColorRadius = 4.dp
        MainBoxShape(
            boxWidth = weight - blackColorRadius,
            boxHeight = height - blackColorRadius,
            blackColorRadius = blackColorRadius,
            shapeRadius = 12.dp,
            backgroundTopBoxColor = Color.White,
            shape = CircleShape
        ) {
            if (userPhoto != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userPhoto)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.profile_place_holder),
                    onSuccess = {
                        Log.d(TAG, "SingleMealCard: Success")
                    },
                    onError = {
                        Log.d(TAG, "SingleMealCard: ${it.result.throwable.message}")
                    },
                    contentDescription = userPhoto.toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.profile_place_holder),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop
                )
            }
            if (showTheImageCover) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            onCoverClicked()
                        }
                        .background(Color.Black.copy(alpha = .2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Image,
                        contentDescription = "love",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreenBody(
    modifier: Modifier = Modifier,
    profileItemsMenu: List<String> = emptyList(),
    onItemClicked: (Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(profileItemsMenu.size) {
            val isLastIndex = profileItemsMenu.lastIndex == it
            ProfileScreenSingleItem(
                oneItem = profileItemsMenu[it],
                isLineVisible = !isLastIndex,
                onItemClicked = { onItemClicked(it) }
            )
        }
    }
}

@Composable
fun ProfileScreenSingleItem(
    modifier: Modifier = Modifier,
    oneItem: String,
    isLineVisible: Boolean = true,
    onItemClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = oneItem,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            IconButton(onClick = {
                onItemClicked()
            }) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "love",
                    tint = Color.Black
                )
            }

        }
        if (isLineVisible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(whiteMba3bas)
            )
        }
    }
}

@Composable
fun ProfileScreenAboutBottomSheet(
    modifier: Modifier = Modifier,
    isShowBottomSheet: Boolean = false,
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState,
    aboutItems: List<AboutItem> = emptyList(),
    onSheetItemClicked: (Int) -> Unit
) {
    if (isShowBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                items(aboutItems.size) {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable {
                                onSheetItemClicked(it)
                            },
                        painter = painterResource(id = aboutItems[it].icon),
                        contentDescription = aboutItems[it].url
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileScreenLogoutAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    isShowAlertDialog: Boolean = false
) {
    if (isShowAlertDialog) {
        AlertDialog(
            icon = {
                Icon(icon, contentDescription = "Example Icon")
            },
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(text = "Confirm", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(text = "Dismiss", color = Color.Black)
                }
            },
            containerColor = Color.White,
            textContentColor = Color.Black,
            titleContentColor = Color.Black,
            iconContentColor = Color.Black,
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {
        ProfileScreen(onNavigateToProfileSettings = { _, _, _, _ -> })
    }
}

