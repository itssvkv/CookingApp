package com.example.cookingapp

import AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookingapp.navigation.HomeScreens
import com.example.cookingapp.navigation.RootNavGraph
import com.example.cookingapp.presentation.components.BottomNavigationBar
import com.example.cookingapp.presentation.screen.home.HomeScreen
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.BOTTOM_BAR_GRAPH_ROUTE
import com.example.cookingapp.utils.Constants.MAIN_GRAPH_ROUTE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                val bottomBarState = remember { (mutableStateOf(true)) }
                val topBarState = remember { (mutableStateOf(true)) }
                var selectedItem by remember {
                    mutableIntStateOf(0)
                }
                // Control TopBar and BottomBar
                when (navBackStackEntry?.destination?.route) {
                    HomeScreens.HomeScreen.route -> {
                        bottomBarState.value = true
                        topBarState.value = true
                        selectedItem = 0
                    }

                    HomeScreens.LibraryScreen.route -> {
                        bottomBarState.value = true
                        topBarState.value = true
                        selectedItem = 1
                    }

                    HomeScreens.AllRecipesScreen.route -> {
                        bottomBarState.value = true
                        topBarState.value = true
                    }

                    HomeScreens.FavoriteScreen.route -> {
                        bottomBarState.value = true
                        topBarState.value = true
                        selectedItem = 3
                    }


                    else -> {
                        bottomBarState.value = false
                        topBarState.value = false
                    }
                }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    },
                    bottomBar = {
                        if (bottomBarState.value) {
                            BottomNavigationBar(
                                navController = navController,
                                currentRoute = currentRoute,
                                selectedItem = selectedItem
                            )
                        }
                    },
                    containerColor = Color.White
                ) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        RootNavGraph(
                            navController = navController,
                            startGraph = if (Constants.isLoggedIn) BOTTOM_BAR_GRAPH_ROUTE else MAIN_GRAPH_ROUTE
                        )
                    }
                }

            }
        }
    }


}


