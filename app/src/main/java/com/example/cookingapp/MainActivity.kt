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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookingapp.navigation.HomeScreens
import com.example.cookingapp.navigation.RootNavGraph
import com.example.cookingapp.presentation.components.BottomNavigationBar
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.BOTTOM_BAR_GRAPH_ROUTE
import com.example.cookingapp.utils.Constants.MAIN_GRAPH_ROUTE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val isKeepSplashScreen = MutableLiveData(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        delaySplashScreen()
        installSplashScreen().setKeepOnScreenCondition {
            isKeepSplashScreen.value ?: true
        }
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
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(), // Use fillMaxSize to prevent overflow
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.weight(1f) // This ensures the RootNavGraph does not consume all space
                        ) {
                            RootNavGraph(
                                navController = navController,
                                startGraph = if (Constants.isLoggedIn) BOTTOM_BAR_GRAPH_ROUTE else MAIN_GRAPH_ROUTE
                            )
                        }

                        AnimatedVisibility(
                            visible = bottomBarState.value,
                            enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
                            exit = fadeOut(animationSpec = tween(durationMillis = 2000))
                        ) {
                            BottomNavigationBar(
                                modifier = Modifier,
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

    private fun delaySplashScreen() {
        lifecycleScope.launch {
            delay(2000)
            isKeepSplashScreen.value = false
        }
    }
}
























