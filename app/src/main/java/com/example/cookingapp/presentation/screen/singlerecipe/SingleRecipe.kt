package com.example.cookingapp.presentation.screen.singlerecipe

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.cookingapp.model.RandomMeal
import com.example.cookingapp.utils.Constants.TAG
import timeColor

@Composable
fun SingleRecipeScreen(
    modifier: Modifier = Modifier,
    mealInfo: RandomMeal,
    onBackIconClicked: () -> Unit,
    mealColor: Color
) {
    ScreenContent(
        mealInfo = mealInfo,
        onBackIconClicked = onBackIconClicked,
        onFavIconClicked = {  },
        mealColor = mealColor
    )
}


@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    mealInfo: RandomMeal,
    onBackIconClicked: () -> Unit,
    onFavIconClicked: () -> Unit,
    mealColor: Color
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ScreenHeader(
                onBackIconClicked = onBackIconClicked,
                onFavIconClicked = onFavIconClicked
            )
        }
        item {
            ScreenBody(mealInfo = mealInfo, mealColor = mealColor)
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
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
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
    mealInfo: RandomMeal,
    mealColor: Color
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(mealColor),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mealInfo.strMealThumb)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.cr7),
                onSuccess = {
                    Log.d(TAG, "SingleMealCard: Success")
                },
                onError = {
                    Log.d(TAG, "SingleMealCard: ${it.result.throwable.message}")
                },
                contentDescription = mealInfo.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        mealInfo.strMeal?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "Star",
                tint = Color.Yellow
            )
            Text(text = "4.8", style = MaterialTheme.typography.labelSmall, color = Color.Black)
            Spacer(modifier = Modifier.width(24.dp))
            mealInfo.strArea?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            mealInfo.strCategory?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
                    0 -> mealInfo.prepTime
                    1 -> mealInfo.cookTime
                    2 -> mealInfo.totalTime
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

        mealInfo.strMealThumb?.let {
            Text(text = it, style = MaterialTheme.typography.bodySmall)

        }
    }
}