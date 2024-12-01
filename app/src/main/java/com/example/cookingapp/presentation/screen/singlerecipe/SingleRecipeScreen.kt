package com.example.cookingapp.presentation.screen.singlerecipe

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cookingapp.R
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Constants.INGREDIENT
import com.example.cookingapp.utils.Constants.TAG
import linkColor
import timeColor
import veryLightWhite

@Composable
fun SingleRecipeScreen(
    modifier: Modifier = Modifier,
    mealInfo: SingleMealLocal,
    onBackIconClicked: () -> Unit,
    mealColor: Color
) {
    val context = LocalContext.current
    ScreenContent(
        mealInfo = mealInfo,
        onBackIconClicked = onBackIconClicked,
        onFavIconClicked = { },
        mealColor = mealColor,
        onTheMealImageLinkClicked = {
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(it))
        },
        onYoutubeLinkClicked = {
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(it))
        },
        onSourceLinkClicked = {
            CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(it))
        }
    )
}


@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    mealInfo: SingleMealLocal,
    onBackIconClicked: () -> Unit,
    onFavIconClicked: () -> Unit,
    mealColor: Color,
    onTheMealImageLinkClicked: (String) -> Unit = {},
    onYoutubeLinkClicked: (String) -> Unit = {},
    onSourceLinkClicked: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ScreenHeader(
                onBackIconClicked = onBackIconClicked,
                onFavIconClicked = onFavIconClicked
            )
        }
        item {
            ScreenBody(
                mealInfo = mealInfo,
                mealColor = mealColor,
                onTheMealImageLinkClicked = onTheMealImageLinkClicked,
                onYoutubeLinkClicked = onYoutubeLinkClicked,
                onSourceLinkClicked = onSourceLinkClicked
            )
        }
    }
}

@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    onBackIconClicked: () -> Unit = {},
    onFavIconClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
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
        Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
            IconButton(onClick = onFavIconClicked) {
                Icon(painter = painterResource(id = R.drawable.fav), contentDescription = "love")
            }
        }

    }
}

@Composable
fun ScreenBody(
    modifier: Modifier = Modifier,
    mealInfo: SingleMealLocal,
    mealColor: Color,
    onTheMealImageLinkClicked: (String) -> Unit = {},
    onYoutubeLinkClicked: (String) -> Unit = {},
    onSourceLinkClicked: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ImageBox(
            mealImageLink = mealInfo.strMealThumb,
            mealColor = mealColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        MainInfoSection(
            mealName = mealInfo.strMeal,
            mealArea = mealInfo.strArea,
            mealCategory = mealInfo.strCategory
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(veryLightWhite)
                .padding(12.dp)
        ) {
            CookingTimeSection(
                prepTime = mealInfo.prepTime,
                cookTime = mealInfo.cookTime,
                totalTime = mealInfo.totalTime
            )
            Spacer(modifier = Modifier.height(8.dp))
            ImageLinkAndSomeInfo(
                mealImageLink = mealInfo.strMealThumb,
                mealTags = mealInfo.strTags,
                onTheMealImageLinkClicked = onTheMealImageLinkClicked
            )
            Spacer(modifier = Modifier.height(8.dp))
            IngredientsSection(
                ingredients = mealInfo.ingredient,
                measure = mealInfo.measure
            )
            Spacer(modifier = Modifier.height(8.dp))
            mealInfo.strYoutube?.let {
                Text(
                    modifier = Modifier.clickable { onYoutubeLinkClicked(it) },
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = linkColor
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            mealInfo.strSource?.let {
                Text(
                    modifier = Modifier.clickable { onSourceLinkClicked(it) },
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = linkColor
                )

            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        mealInfo.strInstructions?.let { InstructionSection(mealInstruction = it) }
    }
}

@Composable
fun ImageBox(
    modifier: Modifier = Modifier,
    mealImageLink: String? = null,
    mealColor: Color
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(mealColor),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(mealImageLink)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.cr7),
            onSuccess = {
                Log.d(TAG, "SingleMealCard: Success")
            },
            onError = {
                Log.d(TAG, "SingleMealCard: ${it.result.throwable.message}")
            },
            contentDescription = mealImageLink,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape),
        )
    }
}

@Composable
fun MainInfoSection(
    modifier: Modifier = Modifier,
    mealName: String? = null,
    mealArea: String? = null,
    mealCategory: String? = null
) {
    mealName?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
    Row {
        Icon(
            modifier = modifier.size(16.dp),
            painter = painterResource(id = R.drawable.star),
            contentDescription = "Star",
            tint = Color.Yellow
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "4.8", style = MaterialTheme.typography.labelSmall, color = Color.Black)
        Spacer(modifier = Modifier.width(24.dp))
        mealArea?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.width(24.dp))
        mealCategory?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}

@Composable
fun CookingTimeSection(
    modifier: Modifier = Modifier,
    prepTime: Int,
    cookTime: Int,
    totalTime: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        (0..2).forEach {
            val timeName = when (it) {
                0 -> "Prep"
                1 -> "Cook"
                2 -> "Total"
                else -> ""
            }
            val time = when (it) {
                0 -> prepTime
                1 -> cookTime
                2 -> totalTime
                else -> 0
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = timeColor
                )
                Text(
                    text = "$time min",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
fun ImageLinkAndSomeInfo(
    mealImageLink: String? = null,
    mealTags: String? = null,
    onTheMealImageLinkClicked: (String) -> Unit
) {
    mealImageLink?.let {
        Text(
            modifier = Modifier.clickable { onTheMealImageLinkClicked(it) },
            text = it,
            style = MaterialTheme.typography.bodySmall,
            color = linkColor
        )

    }
    Spacer(modifier = Modifier.height(8.dp))
    mealTags?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun IngredientsSection(
    modifier: Modifier = Modifier,
    ingredients: List<String?>,
    measure: List<String?>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(ingredients.size) {
            val lastIndex = ingredients.size - 1
            val isLastIndex = lastIndex == it
            if (ingredients[it] != null && measure[it]
                != null && ingredients[it]!!.isNotEmpty()
                && measure[it]!!.isNotEmpty()
            ) {
                SingleIngredientSection(
                    ingredient = ingredients[it],
                    measure = measure[it],
                    isLastIndex = isLastIndex
                )
            }

        }
    }
}

@Composable
fun SingleIngredientSection(
    modifier: Modifier = Modifier,
    ingredient: String?,
    measure: String?,
    isLastIndex: Boolean
) {
    val paddingValue = if (isLastIndex) 0.dp else 8.dp
    Column(
        modifier = modifier
            .padding(end = paddingValue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d(TAG, "IngredientsSection: \"$INGREDIENT$ingredient.png\"")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$INGREDIENT$ingredient.png")
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.cr7),
            onSuccess = {
                Log.d(TAG, "SingleMealCard: Success")
            },
            onError = {
                Log.d(TAG, "SingleMealCard: ${it.result.throwable.message}")
            },
            contentDescription = ingredient,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.height(4.dp))
        ingredient?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
        measure?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
            )
        }


    }
}

@Composable
fun InstructionSection(
    modifier: Modifier = Modifier,
    mealInstruction: String
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Process",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = timeColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = mealInstruction)
    }
}