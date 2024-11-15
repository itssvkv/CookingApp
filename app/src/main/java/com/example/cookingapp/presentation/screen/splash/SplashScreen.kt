package com.example.cookingapp.presentation.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cookingapp.R
import com.example.cookingapp.navigation.Screen
import com.example.cookingapp.presentation.screen.home.HomeScreen
import com.example.cookingapp.utils.Common.isFirstTime
import com.example.cookingapp.utils.Common.isLoggedIn
import dagger.hilt.android.lifecycle.HiltViewModel
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
        Text(text = "hhhhhhh")
    }
    LaunchedEffect(key1 = true) {
        delay(2000L)
        splashViewModel.splashScreenState.collectLatest { state ->
            if (state.navigateToHomeScreen) {
                navHostController.navigate(Screen.HomeScreen)
            } else if (state.navigateToLoginScreen) {
                navHostController.navigate(Screen.LoginScreen)
            } else {
                navHostController.navigate(Screen.OnBoardingScreen)
                splashViewModel.saveFirstOpenStateToDataStore()
            }

        }
    }


//    LaunchedEffect(key1 = true) {
//        delay(2000L)
//        if (!isFirstTime) {
//            if (isLoggedIn) {
//                navHostController.navigate(Screen.HomeScreen)
//            } else {
//                navHostController.navigate(Screen.LoginScreen)
//            }
//        } else {
//            navHostController.navigate(Screen.OnBoardingScreen)
//            splashViewModel.saveFirstOpenStateToDataStore()
//
//        }
//    }

}