package com.example.cookingapp.presentation.screen.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.data.remote.api.dto.CategoriesDto
import com.example.cookingapp.model.RandomMeal
import com.example.cookingapp.presentation.components.MainBoxShape
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.presentation.components.SingleMealCard
import com.example.cookingapp.presentation.components.shimmerBrush
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.TAG
import dots
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
                searchQuery = uiState.searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                isFocusedChanged = isFocusedChanged,
                focusRequester = focusRequester,
                isFocused = uiState.isFocused
            )
        }

        item {
            HomeScreenCategoriesSection(
                categories = uiState.categories?.categories ?: emptyList(),
                isLoading = uiState.categories == null
            )
        }



        item {
            MealsSection(
                title = "Random Recipes",
                onSeeAllClicked = { },
                meals = uiState.meals ?: emptyList(),
                isLoading = uiState.meals == null
            )


        }
        item {
            GenerateRecipeSection()
        }

    }

}

@Composable
fun HomeScreenSearchBar(
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
    categories: List<CategoriesDto.Categories>,
    onItemClicked: (Int) -> Unit = {},
    isLoading: Boolean
) {

    val loadingContentAlpha = animateFloatAsState(
        targetValue = if (isLoading) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = ""
    )
    val realContentAlpha = animateFloatAsState(
        targetValue = if (isLoading) 0f else 1f,
        animationSpec = tween(delayMillis = 600, easing = LinearEasing),
        label = ""
    )
    Box {
        HomeScreenCategoriesSectionLoadingState(contentAlpha = loadingContentAlpha.value)
        LazyRow(
            modifier = modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .graphicsLayer { alpha = realContentAlpha.value },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(categories.size) { index: Int ->
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
                        text = categories[index].strCategory!!,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }

}

@Composable
fun HomeScreenCategoriesSectionLoadingState(
    modifier: Modifier = Modifier,
    contentAlpha: Float
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .graphicsLayer { alpha = contentAlpha },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(7) { _: Int ->
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(30.dp)
                    .padding(end = 8.dp)
                    .border(
                        width = 1.dp,
                        color = primary.copy(alpha = .5f),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center

            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(brush = shimmerBrush())

                )
            }
        }
    }
}

@Composable
fun MealsSection(
    modifier: Modifier = Modifier,
    title: String,
    onSeeAllClicked: () -> Unit,
    meals: List<RandomMeal> = emptyList(),
    isLoading: Boolean
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
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
        MealsSectionBody(
            meals = meals,
            isLoading = isLoading
        )

    }
}

@Composable
fun MealsSectionBody(
    modifier: Modifier = Modifier,
    listOfColors: List<Color> = listOf(
        tertiaryDark,
        primaryDark,
        randomColor,
        randomColor1,
        randomColor2
    ),
    meals: List<RandomMeal> = emptyList(),
    isLoading: Boolean
) {
    val loadingContentAlpha = animateFloatAsState(
        targetValue = if (isLoading) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = ""
    )
    val realContentAlpha = animateFloatAsState(
        targetValue = if (isLoading) 0f else 1f,
        animationSpec = tween(delayMillis = 600, easing = LinearEasing),
        label = ""
    )
    Box {
        MealsSectionLoadingState(contentAlpha = loadingContentAlpha.value)
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .graphicsLayer { alpha = realContentAlpha.value },
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

@Composable
fun MealsSectionLoadingState(
    modifier: Modifier = Modifier,
    contentAlpha: Float
) {
    Log.d(TAG, "MealsSectionLoadingState: Shimmer")
    LazyRow(
        Modifier
            .fillMaxWidth()
            .graphicsLayer { alpha = contentAlpha }
    ) {
        items(count = 5) {
            Column(
                modifier = modifier
                    .width(200.dp)
                    .height(240.dp)
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(primary.copy(alpha = .05f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(brush = shimmerBrush())
                )
                Spacer(modifier = Modifier.height(20.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(brush = shimmerBrush())
                )

                Spacer(modifier = Modifier.height(20.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                        .padding(horizontal = 6.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(brush = shimmerBrush())

                )
                Spacer(modifier = Modifier.height(20.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(brush = shimmerBrush())

                )
            }
        }
    }
}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GenerateRecipeSection(
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        val weight = maxWidth
        val height = maxHeight
        val blackColorRadius = 7.dp
        MainBoxShape(
            boxWidth = weight - blackColorRadius,
            boxHeight = height - blackColorRadius,
            blackColorRadius = blackColorRadius,
            shapeRadius = 12.dp
        ) {
            GenerateRecipeBody()
        }
    }
}

@Composable
fun GenerateRecipeBody(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(5f)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .width(30.dp)
                        .height(20.dp),
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(12) {
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .size(2.5.dp)
                                .background(dots)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Don't know what to eat?",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Generate recipe",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "*based on your preferences",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                MainButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(end = 16.dp),
                    text = "Generate",
                    onButtonClicked = {

                    }
                )
            }

            Image(
                painter = painterResource(id = R.drawable.gernerate_woman),
                contentDescription = "woman",
                modifier = Modifier
                    .weight(5f)
                    .size(140.dp)
            )
        }
    }
}

@Composable
fun Test(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier
            .width(30.dp)
            .height(20.dp),
        columns = GridCells.Fixed(4),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(12) {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .size(2.5.dp)
                    .background(dots)
            )
        }
    }

}

@Preview
@Composable
private fun TestPreview() {
    Box(
        modifier = Modifier
            .width(50.dp)
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {

        Test()
    }
}