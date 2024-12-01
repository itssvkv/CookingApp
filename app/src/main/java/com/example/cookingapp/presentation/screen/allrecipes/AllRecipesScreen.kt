package com.example.cookingapp.presentation.screen.allrecipes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.model.SingleMeal
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
    meals: List<SingleMeal>,
    title: String,
    onBackIconClicked: () -> Unit = {},
    onNavigateToSingleRecipeScreen: (SingleMeal, Color) -> Unit
) {
//    viewModel.onReceiveMeals(meals = meals)
    val uiState by viewModel.uiState.collectAsState()

    ScreenContent(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        isMealsReachingTheEnd = { viewModel.getRandomMeals() },
        meals = meals,
        title = title,
        onBackIconClicked = onBackIconClicked,
        onItemClicked = onNavigateToSingleRecipeScreen
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    uiState: AllRecipesScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    isMealsReachingTheEnd: () -> Unit = {},
    meals: List<SingleMeal>,
    title: String,
    onBackIconClicked: () -> Unit = {},
    onItemClicked: (SingleMeal, Color) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderSection(title = title, onBackIconClicked = onBackIconClicked)
        }
        item {
            AllRecipesScreenSearchBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChanged = onSearchQueryChanged
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        mealsSectionBody(
            meals = meals + uiState.meals,
            isMealsReachingTheEnd = isMealsReachingTheEnd,
            isLoading = uiState.isLoading,
            onItemClicked = onItemClicked
        )

    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    title: String,
    onBackIconClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(3.5f)
        ) {
            IconButton(onClick = onBackIconClicked) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back")
            }
            Text(
                text = "Back", style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(6.5f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AllRecipesScreenSearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
) {
    MainTextField(
        modifier = Modifier.padding(horizontal = 16.dp),
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = Constants.CustomTextFieldLabel.SEARCH,
        leadingIcon = Icons.Default.Search,
    )
}


fun LazyListScope.mealsSectionBody(
    listOfColors: List<Color> = listOf(
        tertiaryDark,
        primaryDark,
        randomColor,
        randomColor1,
        randomColor2
    ),
    meals: List<SingleMeal> = emptyList(),
    isLoading: Boolean = false,
    isMealsReachingTheEnd: () -> Unit = {},
    onFavIconClicked: (Boolean) -> Unit = {},
    onItemClicked: (SingleMeal, Color) -> Unit
) {
    items(meals.size) { index: Int ->
        Log.d(TAG, "MealsSectionBody: Index$index")
        val num = index % listOfColors.size
        SingleMealCard(
            modifier = Modifier.padding(horizontal = 16.dp),
            meal = meals[index],
            backgroundColor = listOfColors[num],
            width = 345.dp,
            height = 405.dp,
            favIcon = painterResource(id = R.drawable.fav),
            onFacIconClicked = onFavIconClicked,
            onItemClicked = { onItemClicked(meals[index], listOfColors[num]) }
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


@Preview()
@Composable
private fun AllRecipesScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AllRecipesScreen(
            meals = emptyList(),
            title = "",
            onNavigateToSingleRecipeScreen = { _, _ -> })
    }
}