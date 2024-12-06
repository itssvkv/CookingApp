package com.example.cookingapp.presentation.components

import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cookingapp.R
import com.example.cookingapp.model.SingleMealLocal
import com.example.cookingapp.utils.Constants.TAG

@Composable
fun SingleMealCard(
    modifier: Modifier = Modifier,
    width: Dp = 200.dp,
    height: Dp = 240.dp,
    paddingValue: Dp = 16.dp,
    meal: SingleMealLocal,
    mealImageSize: Dp = if (width > 200.dp) 150.dp else 60.dp,
    mealNameTextStyle: TextStyle =
        if (width > 200.dp) MaterialTheme.typography.bodyLarge
        else MaterialTheme.typography.titleMedium,
    mealAreaTextStyle: TextStyle =
        if (width > 200.dp) MaterialTheme.typography.bodyMedium else
            MaterialTheme.typography.bodySmall,
    mealDescription: TextStyle =
        if (width > 200.dp) MaterialTheme.typography.bodySmall else
            MaterialTheme.typography.titleSmall,
    backgroundColor: Color,
    favIcon: Painter? = null,
    onFacIconClicked: (Boolean) -> Unit = {},
    onItemClicked: () -> Unit = {}
) {
    var isFavIconClicked by remember {
        mutableStateOf(false)
    }
    val isLarge = if (width > 200.dp) 16.dp else 0.dp
    val isSmall = if (width <= 200.dp) 16.dp else 0.dp
    Column(
        modifier = modifier
            .width(width)
            .height(height)
            .padding(end = isSmall)
            .padding(bottom = isLarge)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable {
                onItemClicked()
            }
            .padding(paddingValue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (favIcon != null) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                IconButton(onClick = {
                    onFacIconClicked(!meal.isFavorite)
                }) {
                    Icon(
                        painter = favIcon,
                        contentDescription = "love",
                        tint = if (meal.isFavorite) Color.Red else Color.Black
                    )
                }
            }
        }
        Log.d(TAG, "SingleMealCard: ${meal.recipeImageFormDevice.ifEmpty { meal.strMealThumb }}")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(meal.recipeImageFormDevice.ifEmpty { meal.strMealThumb })
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
                .size(mealImageSize)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = meal.strMeal ?: "",
            style = mealNameTextStyle,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "star",
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp)
            )
            Text(text = "4.8", style = MaterialTheme.typography.labelSmall)
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            Text(
                text = meal.strArea ?: "",
                style = mealAreaTextStyle,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = meal.strInstructions ?: "",
            style = mealDescription,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

    }
}