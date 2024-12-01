package com.example.cookingapp.presentation.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cookingapp.R
import com.example.cookingapp.navigation.MainScreens
import com.example.cookingapp.utils.Constants.BOTTOM_BAR_GRAPH_ROUTE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import primaryContainerLight

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(primaryContainerLight),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo"
        )
    }
    LaunchedEffect(key1 = true) {
        delay(2000L)
        splashViewModel.splashScreenState.collectLatest { state ->
            if (state.navigateToHomeScreen) {
                navHostController.popBackStack()
                navHostController.navigate(route = BOTTOM_BAR_GRAPH_ROUTE)
            } else if (state.navigateToLoginScreen) {
                navHostController.popBackStack()
                navHostController.navigate(MainScreens.LoginScreen.route)
            } else {
                navHostController.popBackStack()
                navHostController.navigate(MainScreens.OnBoardingScreen.route)
                splashViewModel.saveFirstOpenStateToDataStore()
            }

        }
//        navHostController.navigate(Screen.OnBoardingScreen)
    }

}