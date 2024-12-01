package com.example.cookingapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cookingapp.R
import com.example.cookingapp.navigation.HomeScreens
import com.example.cookingapp.navigation.MainScreens
import com.example.cookingapp.presentation.screen.home.HomeScreen
import primary


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isNavigateToHome: () -> Unit = {},
    isNavigateToLibrary: () -> Unit = {},
    isNavigateToGenerateRecipe: () -> Unit = {},
    isNavigateToFavorite: () -> Unit = {},
    isNavigateToProfile: () -> Unit = {}
) {

    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    val isSelected by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0..4) {
            Column(
                modifier = Modifier
                    .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    selectedItem = i
                    when (i) {
                        0 -> navController.navigate(HomeScreens.HomeScreen.route)
                        1 -> navController.navigate(HomeScreens.LibraryScreen.route)
                        2 -> isNavigateToGenerateRecipe()
                        3 -> isNavigateToFavorite()
                        4 -> isNavigateToProfile()
                    }

                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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
