package com.example.cookingapp.presentation.screen.favorite

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.model.FavoriteMealLocal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.navigation.LottieAnimationBox
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
    val focusRequester = remember {
        FocusRequester()
    }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    ScreenContent(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { focusManager.clearFocus() },
        focusRequester = focusRequester,
        uiState = uiState,
        onFavIconClicked = { isFavorite, index ->
            viewModel.onFavIconClicked(isFavorite, index)
            onFavIconClicked(isFavorite, index)
        },
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onItemClicked = onNavigateToSingleRecipeScreen,
        onSearch = {
            viewModel.searchForMeal()
        },
        onCloseIconClicked = {viewModel.onSearchQueryChanged(searchQuery = "")}
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    uiState: FavoriteScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    onItemClicked: (SingleMealLocal, Color) -> Unit,
    onFavIconClicked: (Boolean, index: Int) -> Unit,
    focusRequester: FocusRequester,
    onSearch: (KeyboardActionScope.() -> Unit)? = null,
    onCloseIconClicked: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                onSearchQueryChanged = onSearchQueryChanged,
                focusRequester = focusRequester,
                onSearch = onSearch,
                onCloseIconClicked = onCloseIconClicked
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        if (uiState.searchResult != null && !uiState.isSearchLoading && uiState.searchQuery.isNotEmpty()) {
            mealsSectionBody(
                modifier = Modifier.padding(horizontal = 16.dp),
                meals = uiState.searchResult,
                isLoading = uiState.isLoading,
                onItemClicked = { meal, color ->
                    onItemClicked(meal, color)
                },
                onFavIconClicked = { isFavorite, index -> }
            )
        } else if (uiState.meals.isNotEmpty()) {
            mealsSectionBody(
                modifier = Modifier.padding(horizontal = 16.dp),
                meals = uiState.meals,
                isLoading = uiState.isLoading,
                onItemClicked = onItemClicked,
                onFavIconClicked = onFavIconClicked
            )
        }else{
            item {
                Log.d("library", "ScreenContent: animation")
                LottieAnimationBox(
                    resId = R.raw.favorite_resipe_empty,
                    animationText = "Do some likes....",
                    animationSize = 400.dp
                )
            }
        }
    }
}