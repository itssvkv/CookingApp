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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.data.remote.api.dto.CategoriesDto
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.components.MainBoxShape
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.presentation.components.SingleMealCard
import com.example.cookingapp.presentation.components.shimmerBrush
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
    isFavorite: Boolean = false,
    indexes: List<Int?> = emptyList(),
    favIndexesListAndValue: List<Pair<Boolean, Int?>>?
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember {
        FocusRequester()
    }

    val context = LocalContext.current
    val isOnline = isInternetAvailable(context)

    LaunchedEffect(isOnline) {
        if (isOnline) {
            viewModel.getRandomMeals()
        } else {
            viewModel.getAllRandomMealsFromRoom()
        }
    }

    LaunchedEffect(key1 = true) {
        favIndexesListAndValue?.forEach {
            val isFavIconClicked = it.first
            val index = it.second
            if (isFavIconClicked) {
                viewModel.onFavIconClicked(isFavIconClicked = true, index = index!!)
            } else {
                viewModel.onFavIconClicked(isFavIconClicked = false, index = index!!)
            }
        }
//        if (isFavorite) {
//            indexes.forEach { index ->
//                viewModel.onFavIconClicked(isFavIconClicked = true, index = index!!)
//            }
//        } else {
//            indexes.forEach { index ->
//                viewModel.onFavIconClicked(isFavIconClicked = false, index = index!!)
//            }
//        }
    }

    HomeScreenContent(
        modifier = modifier,
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChange,
        isFocusedChanged = viewModel::isFocusedChanged,
        focusRequester = focusRequester,
        isMealsReachingTheEnd = {
            viewModel.getRandomMeals()
        },
        onNavigateToAllRecipesScreen = onNavigateToAllRecipesScreen,
        onItemClicked = onNavigateToSingleRecipeScreen,
        onButtonClicked = { },

        )


}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    onSearchQueryChanged: (String) -> Unit,
    isFocusedChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    isMealsReachingTheEnd: () -> Unit,
    onNavigateToAllRecipesScreen: (List<SingleMealLocal>, String) -> Unit,
    onItemClicked: (SingleMealLocal, Color, Int) -> Unit,
    onButtonClicked: () -> Unit = {},
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
                title = "Random recipes",
                onSeeAllClicked = { onNavigateToAllRecipesScreen(uiState.meals, "Random recipes") },
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
                title = "Favourite recipes",
                onSeeAllClicked = { },
                meals = uiState.meals,
                isLoading = uiState.meals.isEmpty(),
                isMealsReachingTheEnd = isMealsReachingTheEnd,
                isLoadingMoreMeals = uiState.isLoadingMoreMeals,
                onItemClicked = onItemClicked
            )
        }

    }
//    HomeTabsFooter(
//        tabViewIcons = listOf(
//            painterResource(id = R.drawable.home),
//            painterResource(id = R.drawable.liberary),
//            painterResource(id = R.drawable.repeat),
//            painterResource(id = R.drawable.fav),
//            painterResource(id = R.drawable.profile),
//        )
//    ) {
//        when (it) {
//            0 -> isNavigateToHome()
//            1 -> isNavigateToLibrary()
//            2 -> isNavigateToGenerateRecipe()
//            3 -> isNavigateToFavorite()
//            4 -> isNavigateToProfile()
//            else -> isNavigateToHome()
//        }
//    }


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
                        .clip(RoundedCornerShape(20.dp))
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
    painter: Painter = painterResource(id = R.drawable.gernerate_woman)
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
fun HomeTabsFooter(
    modifier: Modifier = Modifier,
    tabViewIcons: List<Painter>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val inactiveColor = Color(0xFF777777)

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = modifier
    ) {
        tabViewIcons.forEachIndexed { index, item ->
            Tab(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    onTabSelected(index)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = primary
            ) {
                Icon(
                    painter = item,
                    contentDescription = "",
                    tint = if (selectedIndex == index) Color.Black else primary,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
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