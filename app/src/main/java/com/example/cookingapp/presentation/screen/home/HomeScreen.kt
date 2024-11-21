package com.example.cookingapp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cookingapp.R
import com.example.cookingapp.model.HomeCategoriesResponse
import com.example.cookingapp.model.RandomMeal
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.presentation.components.SingleMealCard
import com.example.cookingapp.utils.Constants
import primary
import primaryDark
import randomColor
import randomColor1
import randomColor2
import tertiaryDark
import kotlin.random.Random

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember {
        FocusRequester()
    }
    HomeScreenContent(
        modifier = modifier,
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChange,
        isFocusedChanged = viewModel::isFocusedChanged,
        focusRequester = focusRequester
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    isFocusedChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HomeScreenSearchBar(
                modifier = modifier,
                searchQuery = uiState.searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                isFocusedChanged = isFocusedChanged,
                focusRequester = focusRequester,
                isFocused = uiState.isFocused
            )
        }
        if (!uiState.isLoading && uiState.categories != null) {
            item {
                HomeScreenCategoriesSection(
                    categories = uiState.categories
                )
            }
        }

        if (!uiState.isLoading && uiState.meals != null) {
            item {
                MealsSection(
                    title = "Random Recipes",
                    onSeeAllClicked = { },
                    meals = uiState.meals
                )

            }
        }

    }

}

@Composable
fun HomeScreenSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    isFocusedChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    isFocused: Boolean = false
) {
    MainTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = Constants.CustomTextFieldLabel.SEARCH,
        leadingIcon = Icons.Default.Search,
        isFocusedChanged = isFocusedChanged,
        focusRequester = focusRequester,
        isFocused = isFocused
    )
}

@Composable
fun HomeScreenCategoriesSection(
    modifier: Modifier = Modifier,
    categories: HomeCategoriesResponse,
    onItemClicked: (Int) -> Unit = {}
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(categories.categories.size) { index: Int ->
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .border(
                        width = 1.dp,
                        color = primary.copy(alpha = .5f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable {
                        onItemClicked(index)
                    }
                    .padding(vertical = 4.dp, horizontal = 12.dp)

            ) {
                Text(
                    text = categories.categories[index].strCategory!!,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun MealsSection(
    modifier: Modifier = Modifier,
    title: String,
    listOfColors: List<Color> = listOf(
        tertiaryDark,
        primaryDark,
        randomColor,
        randomColor1,
        randomColor2
    ),
    onSeeAllClicked: () -> Unit,
    meals: List<RandomMeal> = emptyList()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See all",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    onSeeAllClicked()
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            items(meals.size) { index: Int ->
                val num = Random.nextInt(0, 5)
                SingleMealCard(
                    meal = meals[index],
                    backgroundColor = listOfColors[num]
                )
            }
        }
    }
}

