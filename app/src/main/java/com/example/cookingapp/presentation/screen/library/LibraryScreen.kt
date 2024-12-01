package com.example.cookingapp.presentation.screen.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cookingapp.R
import com.example.cookingapp.presentation.screen.home.GenerateRecipeSection
import randomColor2

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier
) {
    ScreenContent(modifier = modifier)
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ScreenHeader()
        }
    }
}

@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier
) {
    GenerateRecipeSection(
        modifier = modifier,
        backgroundTopBoxColor = randomColor2,
        title = "Your recipes",
        secondDescription = "Make your own browine recipe, or your grandma's lasagna famous",
        buttonText = "Add Recipe",
        onGenerateClicked = {},
        painter = painterResource(id = R.drawable.sec_gernerate_woman)
    )
}