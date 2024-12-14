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
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cookingapp.R
import com.example.cookingapp.data.remote.api.dto.CategoriesDto
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.navigation.LottieAnimationBox
import com.example.cookingapp.presentation.components.MainBoxShape
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.presentation.components.SingleMealCard
import com.example.cookingapp.presentation.components.shimmerBrush
import com.example.cookingapp.presentation.screen.allrecipes.mealsSectionBody
import com.example.cookingapp.utils.Common.isInternetAvailable
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.TAG
import dots
import primary
import primaryContainerLight
import primaryDark
import randomColor
import randomColor1
import randomColor2
import tertiaryDark

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onNavigateToAllRecipesScreen: (List<SingleMealLocal>, String) -> Unit,
    onNavigateToSingleRecipeScreen: (SingleMealLocal, Color, Int) -> Unit,
    onNavigateToGenerateRecipesScreen: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember {
        FocusRequester()
    }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val isOnline = isInternetAvailable(context)

    LaunchedEffect(isOnline) {
        if (isOnline) {
            viewModel.getRandomMeals()
        } else {
            viewModel.getAllRandomMealsFromRoom()
        }
    }
    HomeScreenContent(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { focusManager.clearFocus() },
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChange,
        focusRequester = focusRequester,
        isMealsReachingTheEnd = {
            viewModel.getRandomMeals()
        },
        onNavigateToAllRecipesScreen = onNavigateToAllRecipesScreen,
        onItemClicked = onNavigateToSingleRecipeScreen,
        onButtonClicked = onNavigateToGenerateRecipesScreen,
        onOneCategoryClicked = { category, index ->
            category?.let { viewModel.onOneCategoryClicked(category = category, index = index) }
            Log.d("home", "HomeScreen: ${uiState.categoryMeals}")
        },
        onSearch = {
            viewModel.onSearchImeActionClicked()
        },
        onCloseIconClicked = {
            viewModel.onSearchQueryChange(searchQuery = "")
        },
        onSearchFavIconClicked = viewModel::onSearchFavIconClicked,
        onCategoryFavIconClicked = viewModel::onCategoryFavIconClicked
    )

}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    isMealsReachingTheEnd: () -> Unit,
    onNavigateToAllRecipesScreen: (List<SingleMealLocal>, String) -> Unit,
    onItemClicked: (SingleMealLocal, Color, Int) -> Unit,
    onButtonClicked: () -> Unit = {},
    onOneCategoryClicked: (String?, Int) -> Unit = { _, _ -> },
    onSearch: (KeyboardActionScope.() -> Unit)? = null,
    onCloseIconClicked: () -> Unit = {},
    onSearchFavIconClicked: (Boolean, index: Int) -> Unit = { _, _ -> },
    onCategoryFavIconClicked: (Boolean, index: Int) -> Unit = { _, _ -> }
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HomeScreenSearchBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                focusRequester = focusRequester,
                onSearch = onSearch,
                onCloseIconClicked = onCloseIconClicked,
            )
        }

        item {
            HomeScreenCategoriesSection(
                categories = uiState.categories?.categories ?: emptyList(),
                isLoading = uiState.categories == null,
                isOneCategoryClick = uiState.isOneCategoryClick,
                onOneCategoryClicked = onOneCategoryClicked,
                categoryIndex = uiState.categoryIndex
            )
        }

        if (uiState.isCategoryLoading && uiState.isOneCategoryClick
            || uiState.searchResult.isNullOrEmpty() && uiState.isSearchLoading && uiState.searchQuery.isNotEmpty()
        ) {
            item {
                HomeScreenLoadingOneCategoryMeals()
            }

        } else if (uiState.searchError) {
            item {
                LottieAnimationBox(
                    resId = R.raw.your_empty,
                    animationSize = 150.dp,
                    animationText = "No meal found"
                )
            }
        } else if (uiState.categoryMeals.isNotEmpty() && uiState.isOneCategoryClick) {
            mealsSectionBody(
                meals = uiState.categoryMeals,
                isMealsReachingTheEnd = isMealsReachingTheEnd,
                isLoading = uiState.isLoading,
                onItemClicked = { meal, color ->
                    onItemClicked(meal, color, 0)
                },
                onFavIconClicked = onCategoryFavIconClicked,
            )

        } else if (uiState.searchResult != null && !uiState.isSearchLoading && uiState.searchQuery.isNotEmpty()) {
            mealsSectionBody(
                meals = uiState.searchResult,
                isMealsReachingTheEnd = isMealsReachingTheEnd,
                isLoading = uiState.isLoading,
                onItemClicked = { meal, color ->
                    onItemClicked(meal, color, 0)
                },
                onFavIconClicked = onSearchFavIconClicked,
            )
        } else {
            item {
                MealsSection(
                    title = "Random recipes",
                    onSeeAllClicked = {
                        onNavigateToAllRecipesScreen(
                            uiState.meals,
                            "Random recipes"
                        )
                    },
                    meals = uiState.meals,
                    isLoading = uiState.meals.isEmpty(),
                    isMealsReachingTheEnd = isMealsReachingTheEnd,
                    isLoadingMoreMeals = uiState.isLoadingMoreMeals,
                    onItemClicked = onItemClicked
                )


            }
            item {
                GenerateRecipeSection(
                    firstDescription = "Don't know what to eat?",
                    secondDescription = "*based on your preferences",
                    title = "Generate recipe",
                    buttonText = "Generate",
                    onButtonClicked = onButtonClicked
                )
            }
            item {

                MealsSection(
                    title = "Trending recipes",
                    onSeeAllClicked = { },
                    meals = uiState.meals.shuffled(),
                    isLoading = uiState.meals.isEmpty(),
                    isMealsReachingTheEnd = isMealsReachingTheEnd,
                    isLoadingMoreMeals = uiState.isLoadingMoreMeals,
                    onItemClicked = onItemClicked
                )

            }
        }

    }
}

@Composable
fun HomeScreenSearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    onSearch: (KeyboardActionScope.() -> Unit)? = null,
    onCloseIconClicked: () -> Unit = {},
    error: String? = null
) {
    MainTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = Constants.CustomTextFieldLabel.SEARCH,
        leadingIcon = Icons.Default.Search,
        focusRequester = focusRequester,
        imeAction = ImeAction.Search,
        onSearch = onSearch,
        onTrailingIconClicked = onCloseIconClicked,
        error = error
    )
}

@Composable
fun HomeScreenCategoriesSection(
    modifier: Modifier = Modifier,
    categories: List<CategoriesDto.Categories>,
    onItemClicked: (Int) -> Unit = {},
    isLoading: Boolean,
    isOneCategoryClick: Boolean = false,
    onOneCategoryClicked: (String?, Int) -> Unit = { _, _ -> },
    categoryIndex: Int? = null
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
                Row(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .border(
                            width = 1.dp,
                            color = primary.copy(alpha = .5f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            onItemClicked(index)
                            onOneCategoryClicked(categories[index].strCategory, index)
                        }
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (isOneCategoryClick && categoryIndex == index) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp),
                            imageVector = Icons.Default.Check,
                            contentDescription = "check",
                            tint = Color.Black
                        )
                    }
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
    meals: List<SingleMealLocal> = emptyList(),
    isLoading: Boolean,
    isMealsReachingTheEnd: () -> Unit,
    isLoadingMoreMeals: Boolean,
    onItemClicked: (SingleMealLocal, Color, Int) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
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
            isLoading = isLoading,
            isMealsReachingTheEnd = isMealsReachingTheEnd,
            isLoadingMoreMeals = isLoadingMoreMeals,
            onItemClicked = onItemClicked
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
    meals: List<SingleMealLocal> = emptyList(),
    isLoading: Boolean,
    isMealsReachingTheEnd: () -> Unit,
    isLoadingMoreMeals: Boolean,
    onItemClicked: (SingleMealLocal, Color, Int) -> Unit
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
                Log.d(TAG, "MealsSectionBody: Index$index")
                val num = index % listOfColors.size
                SingleMealCard(
                    meal = meals[index],
                    backgroundColor = listOfColors[num],
                    onItemClicked = { onItemClicked(meals[index], listOfColors[num], index) }
                )
                LaunchedEffect(key1 = meals.size) {
                    if (index == meals.size - 1) {
                        isMealsReachingTheEnd()
                    }
                }

            }
            if (isLoadingMoreMeals) {
                item {
                    CircularProgressIndicator()
                }
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
    modifier: Modifier = Modifier,
    backgroundTopBoxColor: Color = primaryContainerLight,
    title: String = "",
    firstDescription: String = "",
    secondDescription: String = "",
    buttonText: String = "",
    onButtonClicked: () -> Unit = {},
    parentHeight: Dp = 220.dp,
    painter: Painter = painterResource(id = R.drawable.gernerate_woman)
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(parentHeight)
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
            shapeRadius = 12.dp,
            backgroundTopBoxColor = backgroundTopBoxColor
        ) {
            GenerateRecipeBody(
                firstDescription = firstDescription,
                title = title,
                secondDescription = secondDescription,
                buttonText = buttonText,
                onButtonClicked = onButtonClicked,
                painter = painter
            )
        }
    }
}

@Composable
fun GenerateRecipeBody(
    modifier: Modifier = Modifier,
    title: String = "",
    firstDescription: String = "",
    secondDescription: String = "",
    buttonText: String = "",
    onButtonClicked: () -> Unit = {},
    painter: Painter = painterResource(id = R.drawable.gernerate_woman)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 16.dp, bottom = 16.dp),
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
                    .weight(6f),
                verticalArrangement = Arrangement.SpaceBetween
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

                if (firstDescription.isNotEmpty()) {
                    Text(
                        text = firstDescription,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = secondDescription,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                MainButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(end = 16.dp),
                    text = buttonText,
                    onButtonClicked = onButtonClicked
                )
            }
            Image(
                painter = painter,
                contentDescription = "woman",
                modifier = Modifier
                    .weight(4f)
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

@Composable
fun HomeScreenLoadingOneCategoryMeals(
    modifier: Modifier = Modifier
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading_pan
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = Modifier.size(300.dp)
        )
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