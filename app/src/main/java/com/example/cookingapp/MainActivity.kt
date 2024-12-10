package com.example.cookingapp

import AppTheme
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookingapp.navigation.HomeScreens
import com.example.cookingapp.navigation.MainScreens
import com.example.cookingapp.navigation.RootNavGraph
import com.example.cookingapp.presentation.components.BottomNavigationBar
import com.example.cookingapp.presentation.screen.home.HomeScreen
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.BOTTOM_BAR_GRAPH_ROUTE
import com.example.cookingapp.utils.Constants.MAIN_GRAPH_ROUTE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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


                val bottomBarState = remember { (mutableStateOf(false)) }
                val topBarState = remember { (mutableStateOf(true)) }
                var selectedItem by remember {
                    mutableIntStateOf(0)
                }
                // Control TopBar and BottomBar
                when (currentRoute) {
                    MainScreens.SplashScreen.route -> {
                        Log.d("bdan", "onCreate: Splash")
                        bottomBarState.value = false
                        topBarState.value = false
                    }

                    HomeScreens.HomeScreen.route -> {
                        Log.d("bdan", "onCreate: home")
                        bottomBarState.value = true
                        topBarState.value = true
                        selectedItem = 0
                    }

                    HomeScreens.LibraryScreen.route -> {
                        bottomBarState.value = true
                        topBarState.value = true
                        selectedItem = 1
                    }

                    HomeScreens.GenerateRecipesScreen.route -> {
                        bottomBarState.value = true
                        topBarState.value = true
                        selectedItem = 2
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

                    HomeScreens.ProfileScreen.route -> {
                        bottomBarState.value = true
                        topBarState.value = true
                        selectedItem = 4
                    }
                    else -> {
                        bottomBarState.value = false
                        topBarState.value = false
                    }
                }
//                Scaffold(
//                    snackbarHost = {
//                        SnackbarHost(hostState = snackbarHostState)
//                    },
//                    bottomBar = {
//                        if (bottomBarState.value) {
//                            BottomNavigationBar(
//                                navController = navController,
//                                currentRoute = currentRoute,
//                                selectedItem = selectedItem
//                            )
//                        }
//                    },
//                    containerColor = Color.White
//                ) { paddingValues ->
//                    Box(
//                        modifier = Modifier.padding(paddingValues)
//                    ) {
//                        RootNavGraph(
//                            navController = navController,
//                            startGraph = if (Constants.isLoggedIn) BOTTOM_BAR_GRAPH_ROUTE else MAIN_GRAPH_ROUTE
//                        )
//                    }
//                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)

                    ) {
                        RootNavGraph(
                            navController = navController,
                            startGraph = if (Constants.isLoggedIn) BOTTOM_BAR_GRAPH_ROUTE else MAIN_GRAPH_ROUTE
                        )
                    }

                    AnimatedVisibility(
                        visible = bottomBarState.value,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        enter = fadeIn(
                            animationSpec = tween(durationMillis = 2000)
                        ),
                        exit = fadeOut(animationSpec = tween(durationMillis = 2000))
                    ) {
                        BottomNavigationBar(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            navController = navController,
                            currentRoute = currentRoute,
                            selectedItem = selectedItem
                        )

                    }

                }


            }


        }
    }
}
























