package com.example.cookingapp.presentation.screen.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookingapp.presentation.components.CommonHeaderSection
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.screen.profile.ProfileImageBox
import com.example.cookingapp.presentation.screen.profile.ProfileScreen

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier
) {
    EditProfileScreenContent(
        onBackIconClicked = {},
        onButtonClicked = {}
    )
}

@Composable
fun EditProfileScreenContent(
    modifier: Modifier = Modifier,
    onBackIconClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditProfileScreenHeader(
            onButtonClicked = onButtonClicked,
            onBackIconClicked = onBackIconClicked,
            isLoading = isLoading
        )
    }
}

@Composable
fun EditProfileScreenHeader(
    modifier: Modifier = Modifier,
    onBackIconClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    isLoading: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        CommonHeaderSection(
            modifier = Modifier.weight(5f),
            title = "",
            onBackIconClicked = onBackIconClicked
        )
        MainButton(
            modifier = Modifier
                .weight(1.8f)
                .height(40.dp)
                .padding(0.dp),
            onButtonClicked = onButtonClicked,
            text = "Save",
            isLoading = isLoading
        )

    }
}

@Composable
fun EditProfileScreenBody(
    modifier: Modifier = Modifier,
    showTheImageCover: Boolean = false,
    onCoverClicked: () -> Unit = {},
    userPhoto: String = "",
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ProfileImageBox(
            showTheImageCover = showTheImageCover,
            onCoverClicked = onCoverClicked,
            userPhoto = userPhoto,
        )
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {
        EditProfileScreen()
    }
}