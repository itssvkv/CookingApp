@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cookingapp.presentation.screen.generaterecipes

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cookingapp.R
import com.example.cookingapp.model.Meal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.components.MainBoxShape
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.components.NewTextField
import com.example.cookingapp.presentation.components.SingleMealCard
import com.example.cookingapp.utils.Constants.TAG
import dots
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import primaryDark
import randomColor
import randomColor2
import randomColor1
import tertiaryDark

@Composable
fun GenerateRecipesScreen(
    modifier: Modifier = Modifier,
    viewModel: GenerateRecipesScreenViewModel = hiltViewModel(),
    onSheetButtonClicked: (List<Meal>) -> Unit = {},
    onNavigateToSingleRecipeScreen: (SingleMealLocal?, Color) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    GenerateRecipeScreenContent(
        modifier = modifier,
        onButtonClicked = {
            viewModel.updateIsShowBottomSheet(value = true)
        },
        title = "Previous generated recipes",
        onSeeAllClicked = {},
        uiState = uiState,
        onItemClicked = { meal, color, index ->
            scope.launch {
                viewModel.onItemClicked(meal = meal)
                onNavigateToSingleRecipeScreen(uiState.singleMealInfo, color)
            }
        },
        sheetState = sheetState,
        onDismissRequest = {
            viewModel.updateIsShowBottomSheet(value = false)
        },
        onIngredientValueChange = viewModel::onIngredientValueChange,
        onCategoryValueChange = viewModel::onCategoryValueChange,
        onAreaValueChange = viewModel::onAreaValueChange,
        onSheetButtonClicked = {
            scope.launch {
                // Ensure each ViewModel function is complete before proceeding
                viewModel.getAllMealsWithMainIngredient(ingredient = uiState.ingredient)
                viewModel.getAllMealsWithMainCategory(category = uiState.category)
                viewModel.getAllMealsWithMainArea(area = uiState.area)
                if (!(uiState.isIngredientError || uiState.isCategoryError || uiState.isAreaError)) {
                    val meals: List<Meal> =
                        uiState.ingredientMeals!! + uiState.categoryMeals!! + uiState.areaMeals!!
                    sheetState.hide() // Assuming it's a suspending function
                    if (!sheetState.isVisible) {
                        viewModel.updateIsShowBottomSheet(value = false)
                    }
                    onSheetButtonClicked(meals)
                } else {
                    Log.d("generate", "GenerateRecipesScreenLast: ")
                    Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show()
                }
            }
        }

    )
    LaunchedEffect(key1 = uiState.resultMeals) {
        viewModel.getALLPreviousMeals()
    }

}

@Composable
fun GenerateRecipeScreenContent(
    modifier: Modifier = Modifier,
    uiState: GenerateRecipesScreenUiState,
    onButtonClicked: () -> Unit = {},
    title: String,
    onSeeAllClicked: () -> Unit,
    onItemClicked: (Meal, Color, Int) -> Unit,
    sheetState: SheetState,
    onDismissRequest: () -> Unit = {},
    onIngredientValueChange: (String) -> Unit = {},
    onCategoryValueChange: (String) -> Unit = {},
    onAreaValueChange: (String) -> Unit = {},
    onSheetButtonClicked: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            GenerateRecipeSection(
                onButtonClicked = onButtonClicked
            )
        }
        item {

            PreviousMealsSection(
                title = title,
                onSeeAllClicked = onSeeAllClicked,
                meals = uiState.previousMeals,
                isLoading = uiState.isIngredientLoading || uiState.isCategoryLoading || uiState.isAreaLoading,
                onItemClicked = onItemClicked
            )


        }
        item {
            GenerateRecipesBottomSheet(
                sheetState = sheetState,
                onDismissRequest = onDismissRequest,
                isShowBottomSheet = uiState.isShowBottomSheet,
                ingredientValue = uiState.ingredient,
                onIngredientValueChange = onIngredientValueChange,
                categoryValue = uiState.category,
                onCategoryValueChange = onCategoryValueChange,
                areaValue = uiState.area,
                onAreaValueChange = onAreaValueChange,
                onSheetButtonClicked = onSheetButtonClicked,
                isLoading = uiState.isIngredientLoading || uiState.isCategoryLoading || uiState.isAreaLoading,
                isIngredientError = uiState.isIngredientError,
                isCategoryError = uiState.isCategoryError,
                isAreaError = uiState.isAreaError
            )

        }
    }
}

@Composable
fun GenerateRecipeSection(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit = {},
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
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
            backgroundTopBoxColor = Color.White
        ) {
            GenerateRecipesBody(
                modifier = modifier,
                onButtonClicked = onButtonClicked
            )
        }
    }
}

@Composable
fun GenerateRecipesBody(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
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
        }
        Image(
            painter = painterResource(id = R.drawable.gernerate_screen_women),
            contentDescription = "woman",
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Generate recipes",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Don’t know what to eat. No worries, " +
                        "we will help you find exactly what " +
                        "you want based on your food preferences.",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            MainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                text = "Generate",
                onButtonClicked = onButtonClicked
            )
        }

    }
}

@Composable
fun PreviousMealsSection(
    modifier: Modifier = Modifier,
    title: String,
    onSeeAllClicked: () -> Unit,
    meals: List<Meal> = emptyList(),
    isLoading: Boolean,
    onItemClicked: (Meal, Color, Int) -> Unit
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
        PreviousMealsSectionBody(
            meals = meals,
            isLoading = isLoading,
            onItemClicked = onItemClicked
        )

    }
}

@Composable
fun PreviousMealsSectionBody(
    modifier: Modifier = Modifier,
    listOfColors: List<Color> = listOf(
        tertiaryDark,
        primaryDark,
        randomColor,
        randomColor1,
        randomColor2
    ),
    meals: List<Meal> = emptyList(),
    isLoading: Boolean,
    onItemClicked: (Meal, Color, Int) -> Unit
) {
    if (!isLoading) {
        Box {
            LazyRow(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                items(meals.size) { index: Int ->
                    Log.d(TAG, "MealsSectionBody: Index$index")
                    val num = index % listOfColors.size
                    PreviousSingleCard(
                        meal = meals[index],
                        backgroundColor = listOfColors[num],
                        onItemClicked = {
                            onItemClicked(
                                meals[index],
                                listOfColors[num],
                                index
                            )
                        }
                    )
                }

            }
        }
    }

}

@Composable
fun PreviousSingleCard(
    modifier: Modifier = Modifier,
    meal: Meal,
    backgroundColor: Color,
    onItemClicked: () -> Unit,
    ingredient: String = "",
    category: String = "",
    area: String = ""
) {
    Column(
        modifier = modifier
            .width(200.dp)
            .height(200.dp)
            .padding(end = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable {
                onItemClicked()
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(meal.strMealThumb)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.cr7),
            onSuccess = {
                Log.d(TAG, "SingleMealCard: Success")
            },
            onError = {
                Log.d(TAG, "SingleMealCard: ${it.result.throwable.message}")
            },
            contentDescription = meal.strMeal,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = meal.strMeal ?: "",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateRecipesBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit = {},
    isShowBottomSheet: Boolean = false,
    ingredientValue: String = "",
    onIngredientValueChange: (String) -> Unit = {},
    categoryValue: String = "",
    onCategoryValueChange: (String) -> Unit = {},
    areaValue: String = "",
    onAreaValueChange: (String) -> Unit = {},
    onSheetButtonClicked: () -> Unit = {},
    isLoading: Boolean,
    isIngredientError: Boolean = false,
    isCategoryError: Boolean = false,
    isAreaError: Boolean = false,
) {
    if (isShowBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                NewTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = "Main ingredient",
                    onValueChange = onIngredientValueChange,
                    value = ingredientValue,
                    error = isIngredientError
                )
                Spacer(modifier = Modifier.height(4.dp))
                NewTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = "Category",
                    onValueChange = onCategoryValueChange,
                    value = categoryValue,
                    error = isCategoryError
                )
                Spacer(modifier = Modifier.height(4.dp))
                NewTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = "Area",
                    onValueChange = onAreaValueChange,
                    value = areaValue,
                    error = isAreaError
                )
                Spacer(modifier = Modifier.height(8.dp))
                MainButton(
                    modifier = Modifier
                        .height(60.dp),
                    text = "Generate",
                    onButtonClicked = onSheetButtonClicked,
                    isLoading = isLoading,
                    isEnabled = ingredientValue.isNotEmpty() || categoryValue.isNotEmpty() || areaValue.isNotEmpty()
                )
            }

        }
    }
}