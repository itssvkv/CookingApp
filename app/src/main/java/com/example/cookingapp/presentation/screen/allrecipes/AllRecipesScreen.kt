package com.example.cookingapp.presentation.screen.allrecipes

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.components.CommonHeaderSection
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.presentation.components.SingleMealCard
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.TAG
import primaryDark
import randomColor
import randomColor1
import randomColor2
import tertiaryDark

@Composable
fun AllRecipesScreen(
    modifier: Modifier = Modifier,
    viewModel: AllRecipesViewModel = hiltViewModel(),
    meals: List<SingleMealLocal>,
    title: String,
    onBackIconClicked: () -> Unit = {},
    onNavigateToSingleRecipeScreen: (SingleMealLocal, Color) -> Unit,

    ) {
    val focusRequester = remember {
        FocusRequester()
    }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true) {
        viewModel.onReceiveMeals(meals = meals)
    }

    val uiState by viewModel.uiState.collectAsState()
    ScreenContent(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { focusManager.clearFocus() },
        uiState = uiState,
        focusRequester = focusRequester,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        isMealsReachingTheEnd = { viewModel.getRandomMeals() },
        title = title,
        onBackIconClicked = onBackIconClicked,
        onItemClicked = onNavigateToSingleRecipeScreen,
        onFavIconClicked = { isFavorite, index ->
            viewModel.onFavIconClicked(isFavorite, index)

        },
        onSearch = {
            viewModel.onSearchImeActionClicked()
        },
        onCloseIconClicked = {viewModel.onSearchQueryChanged(searchQuery = "")}
    )
    BackHandler {
        onBackIconClicked()
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    uiState: AllRecipesScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    isMealsReachingTheEnd: () -> Unit = {},
    title: String,
    onBackIconClicked: () -> Unit = {},
    onItemClicked: (SingleMealLocal, Color) -> Unit,
    onFavIconClicked: (Boolean, index: Int) -> Unit,
    focusRequester: FocusRequester,
    onSearch: (KeyboardActionScope.() -> Unit)? = null,
    onCloseIconClicked: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CommonHeaderSection(title = title, onBackIconClicked = onBackIconClicked)
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
                isMealsReachingTheEnd = isMealsReachingTheEnd,
                isLoading = uiState.isLoading,
                onItemClicked = { meal, color ->
                    onItemClicked(meal, color)
                },
                onFavIconClicked = onFavIconClicked,
            )
        } else {
            mealsSectionBody(
                modifier = Modifier.padding(horizontal = 16.dp),
                meals = uiState.meals,
                isMealsReachingTheEnd = isMealsReachingTheEnd,
                isLoading = uiState.isLoading,
                onItemClicked = onItemClicked,
                onFavIconClicked = onFavIconClicked,
            )

        }
    }
}


@Composable
fun AllRecipesScreenSearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    onSearch: (KeyboardActionScope.() -> Unit)? = null,
    onCloseIconClicked: () -> Unit = {},
) {
    MainTextField(
        modifier = Modifier.padding(horizontal = 16.dp),
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = Constants.CustomTextFieldLabel.SEARCH,
        leadingIcon = Icons.Default.Search,
        focusRequester = focusRequester,
        imeAction = ImeAction.Search,
        onSearch = onSearch,
        onTrailingIconClicked = onCloseIconClicked
    )
}


fun LazyListScope.mealsSectionBody(
    modifier: Modifier = Modifier,
    listOfColors: List<Color> = listOf(
        tertiaryDark,
        primaryDark,
        randomColor,
        randomColor1,
        randomColor2
    ),
    meals: List<SingleMealLocal> = emptyList(),
    isLoading: Boolean = false,
    isMealsReachingTheEnd: () -> Unit = {},
    onFavIconClicked: (Boolean, index: Int) -> Unit,
    onItemClicked: (SingleMealLocal, Color) -> Unit,
    icon: Int = R.drawable.fav,
) {
    items(meals.size) { index: Int ->
        Log.d(TAG, "MealsSectionBody: Index$index")
        val num = index % listOfColors.size
        SingleMealCard(
            modifier = modifier,
            meal = meals[index],
            backgroundColor = listOfColors[num],
            width = 345.dp,
            height = 405.dp,
            favIcon = painterResource(id = icon),
            onFacIconClicked = { isFavorite ->
                onFavIconClicked(isFavorite, index)
            },
            onItemClicked = { onItemClicked(meals[index], listOfColors[num]) },
        )
        LaunchedEffect(key1 = meals.size) {
            if (index == meals.size - 1) {
                isMealsReachingTheEnd()
            }
        }

    }
    if (isLoading) {
        item {
            CircularProgressIndicator()
        }
    }
}


@Preview
@Composable
private fun AllRecipesScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

    }
}