package com.example.cookingapp.presentation.screen.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.presentation.screen.allrecipes.mealsSectionBody
import com.example.cookingapp.presentation.screen.home.GenerateRecipeSection
import com.example.cookingapp.utils.Constants
import randomColor2

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryScreenViewModel = hiltViewModel(),
    onItemClicked: (SingleMealLocal, Color) -> Unit,
    onNavigateToNewRecipeScreen: () -> Unit = {}
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
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onItemClicked = onItemClicked,
        isMealsReachingTheEnd = {},
        onButtonClicked = onNavigateToNewRecipeScreen,
        focusRequester = focusRequester,
        onSearch = {
            viewModel.searchForMeal()
        }
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    uiState: LibraryScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    onItemClicked: (SingleMealLocal, Color) -> Unit,
    isMealsReachingTheEnd: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    focusRequester: FocusRequester,
    onSearch: (KeyboardActionScope.() -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ScreenHeader(
                onButtonClicked = onButtonClicked
            )
        }
        item {
            LibraryScreenSearchBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                focusRequester = focusRequester,
                onSearch = onSearch
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        if (uiState.searchResult != null && !uiState.isSearchLoading && uiState.searchQuery.isNotEmpty()) {
            mealsSectionBody(
                modifier = Modifier.padding(horizontal = 16.dp),
                meals = uiState.searchResult,
                isMealsReachingTheEnd = isMealsReachingTheEnd,
                isLoading = uiState.isLoading,
                onItemClicked = { meal, color ->
                    onItemClicked(meal, color)
                },
                onFavIconClicked = { isFavorite, index -> }
            )
        } else {
            uiState.meals?.let {
                mealsSectionBody(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    meals = it,
                    isMealsReachingTheEnd = isMealsReachingTheEnd,
                    isLoading = uiState.isLoading,
                    onItemClicked = onItemClicked,
                    onFavIconClicked = { isFavorite, index -> }
                )
            }
        }
    }
}

@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit = {},
) {
    GenerateRecipeSection(
        modifier = modifier.padding(horizontal = 16.dp),
        backgroundTopBoxColor = randomColor2,
        title = "Your recipes",
        secondDescription = "Make your own browine recipe, or your grandma's lasagna famous",
        buttonText = "Add Recipe",
        onButtonClicked = onButtonClicked,
        painter = painterResource(id = R.drawable.sec_gernerate_woman)
    )
}

@Composable
fun LibraryScreenSearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    onSearch: (KeyboardActionScope.() -> Unit)? = null
) {
    MainTextField(
        modifier = Modifier.padding(horizontal = 16.dp),
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = Constants.CustomTextFieldLabel.SEARCH,
        leadingIcon = Icons.Default.Search,
        focusRequester = focusRequester,
        imeAction = ImeAction.Search,
        onSearch = onSearch
    )
}