package com.example.cookingapp.presentation.screen.profile

import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cookingapp.R
import com.example.cookingapp.presentation.components.MainBoxShape
import com.example.cookingapp.utils.Constants.TAG
import whiteMba3bas

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateToProfileSettings: () -> Unit = {},
    onNavigateToYourRecipes: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onNavigateToLogOut: () -> Unit = {}
) {
    ProfileScreenContent(
        modifier = modifier,
        onItemClicked = { index ->
            when (index) {
                0 -> onNavigateToProfileSettings()
                1 -> onNavigateToYourRecipes()
                2 -> onNavigateToAbout()
                3 -> onNavigateToLogOut()
            }
        }
    )
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileScreenHeader()
        Spacer(modifier = Modifier.height(16.dp))
        ProfileScreenUserInfo()
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
    userPhoto: String = "",
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
    userPhoto: String = "",
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
                contentDescription = userPhoto,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
            )
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

@Preview
@Composable
private fun ProfileScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {
        ProfileScreen()
    }
}

