package com.example.cookingapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cookingapp.R
import primary


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedItem: Int = 0,
    isNavigateToHome: () -> Unit = {},
    isNavigateToLibrary: () -> Unit = {},
    isNavigateToGenerateRecipe: () -> Unit = {},
    isNavigateToFavorite: () -> Unit = {},
    isNavigateToProfile: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0..4) {
            Column(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    when (i) {
                        0 -> isNavigateToHome()
                        1 -> isNavigateToLibrary()
                        2 -> isNavigateToGenerateRecipe()
                        3 -> isNavigateToFavorite()
                        4 -> isNavigateToProfile()
                    }
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(
                        id = when (i) {
                            0 -> R.drawable.home
                            1 -> R.drawable.liberary
                            2 -> R.drawable.repeat
                            3 -> R.drawable.fav
                            4 -> R.drawable.profile
                            else -> R.drawable.home
                        }
                    ),
                    contentDescription = "",
                    tint = if (i == selectedItem) Color.Black else primary
                )
            }
        }


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