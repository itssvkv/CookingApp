package com.example.cookingapp.presentation.screen.favorite

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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.model.FavoriteMealLocal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.components.CommonHeaderSection
import com.example.cookingapp.presentation.screen.allrecipes.AllRecipesScreenSearchBar
import com.example.cookingapp.presentation.screen.allrecipes.AllRecipesScreenUiState
import com.example.cookingapp.presentation.screen.allrecipes.mealsSectionBody

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    onNavigateToSingleRecipeScreen: (SingleMealLocal, Color) -> Unit,
    onFavIconClicked: (Boolean, index: Int) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsState()

    ScreenContent(
        modifier = modifier,
        uiState = uiState,
        onFavIconClicked = { isFavorite, index ->
            viewModel.onFavIconClicked(isFavorite, index)
            onFavIconClicked(isFavorite, index)
        },
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onItemClicked = onNavigateToSingleRecipeScreen
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    uiState: FavoriteScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    onItemClicked: (SingleMealLocal, Color) -> Unit,
    onFavIconClicked: (Boolean, index: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth().statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Favorite Recipes",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        item {
            AllRecipesScreenSearchBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChanged = onSearchQueryChanged
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        mealsSectionBody(
            meals = uiState.meals,
            isLoading = uiState.isLoading,
            onItemClicked = onItemClicked,
            onFavIconClicked = onFavIconClicked
        )

    }
}