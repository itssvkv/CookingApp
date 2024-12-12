package com.example.cookingapp.presentation.screen.yourrecipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.components.CommonHeaderSection
import com.example.cookingapp.presentation.screen.allrecipes.AllRecipesScreenSearchBar
import com.example.cookingapp.presentation.screen.allrecipes.mealsSectionBody
import com.example.cookingapp.presentation.screen.favorite.FavoriteScreenUiState

@Composable
fun YourRecipesScreen(
    modifier: Modifier = Modifier,
    viewModel: YourRecipesScreenViewModel = hiltViewModel(),
    onBackIconClicked: () -> Unit = {}

) {
    val uiState by viewModel.uiState.collectAsState()
    YourRecipesScreenContent(
        modifier = modifier,
        uiState = uiState,
        onItemClicked = { meal, color ->

        },
        onFavIconClicked = { isDeleteClicked, index ->

        },
        onBackIconClicked = onBackIconClicked
    )

}

@Composable
fun YourRecipesScreenContent(
    modifier: Modifier = Modifier,
    uiState: YourRecipesScreenUiState,
    onItemClicked: (SingleMealLocal, Color) -> Unit,
    onFavIconClicked: (Boolean, index: Int) -> Unit,
    onBackIconClicked: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CommonHeaderSection(title = "Your recipes", onBackIconClicked = onBackIconClicked)
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        mealsSectionBody(
            meals = uiState.meals,
            isLoading = uiState.isLoading,
            onItemClicked = onItemClicked,
            onFavIconClicked = onFavIconClicked,
            icon = R.drawable.delete_icon
        )
    }
}
