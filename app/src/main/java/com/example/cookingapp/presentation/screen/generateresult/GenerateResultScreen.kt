package com.example.cookingapp.presentation.screen.generateresult

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cookingapp.R
import com.example.cookingapp.model.Meal
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.presentation.components.CommonHeaderSection
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.utils.Constants.TAG
import kotlinx.coroutines.launch
import primaryDark
import randomColor
import randomColor1
import randomColor2
import tertiaryDark

@Composable
fun GenerateResultScreen(
    modifier: Modifier = Modifier,
    viewModel: GenerateResultScreenViewModel = hiltViewModel(),
    onBackIconClicked: () -> Unit = {},
    meals: List<Meal>,
    onButtonClicked: (SingleMealLocal?, Color) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    GenerateResultScreenContent(
        modifier = modifier,
        onBackIconClicked = onBackIconClicked,
        onButtonClicked = { meal, color, _ ->
            scope.launch {
                viewModel.onItemClicked(meal = meal)
                onButtonClicked(uiState.singleMealInfo, color)
            }
        },
        isLoading = false,
        meals = meals
    )
}

@Composable
fun GenerateResultScreenContent(
    modifier: Modifier = Modifier,
    onBackIconClicked: () -> Unit = {},
    onButtonClicked: (Meal, Color, Int) -> Unit,
    isLoading: Boolean,
    meals: List<Meal>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            CommonHeaderSection(
                title = "Generated Recipes",
                onBackIconClicked = onBackIconClicked
            )

        }

        generateResultBody(
            onButtonClicked = onButtonClicked,
            isLoading = isLoading,
            meals = meals
        )

    }
}

//@Composable
//fun GenerateResultSection(
//    modifier: Modifier = Modifier,
//    isLoading: Boolean,
//    onButtonClicked: (Meal, Color, Int) -> Unit,
//    meals: List<Meal> = emptyList()
//) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        generateResultBody(
//            isLoading = isLoading,
//            onButtonClicked = onButtonClicked,
//            meals = meals
//        )
//    }
//}

fun LazyListScope.generateResultBody(
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
    onButtonClicked: (Meal, Color, Int) -> Unit
) {
    if (!isLoading) {

        items(meals.size) { index: Int ->
            Log.d(TAG, "MealsSectionBody: Index$index")
            val num = index % listOfColors.size
            Column(
                modifier = modifier
                    .width(345.dp)
                    .height(200.dp)
                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(listOfColors[num])
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(meals[index].strMealThumb)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.cr7),
                    onSuccess = {
                        Log.d(TAG, "SingleMealCard: Success")
                    },
                    onError = {
                        Log.d(TAG, "SingleMealCard: ${it.result.throwable.message}")
                    },
                    contentDescription = meals[index].strMeal,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = meals[index].strMeal ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                MainButton(
                    text = "Check out",
                    onButtonClicked = {
                        onButtonClicked(meals[index], listOfColors[num], index)
                    },
                    modifier = Modifier
                        .height(60.dp)
                        .width(245.dp)
                )
            }
        }

    }
}


