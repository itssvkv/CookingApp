package com.example.cookingapp.presentation.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookingapp.R
import com.example.cookingapp.navigation.MainScreens
import com.example.cookingapp.presentation.components.MainBoxShape
import inversePrimaryLight
import mainButtonColor
import primaryContainerLight

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    LaunchedEffect(key1 = true) {
        viewModel.isOnBoardingCompleted()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(primaryContainerLight)
            .navigationBarsPadding()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                MainBoxShape(
                    boxWidth = 38.dp,
                    boxHeight = 38.dp,
                    blackColorRadius = 2.dp,
                    shapeRadius = 6.dp,
                    backgroundTopBoxColor = primaryContainerLight
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo"
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sonphy",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(48.dp))

        Image(
            modifier = Modifier
                .width(515.dp)
                .height(385.dp)
                .scale(scale = 1.3f),
            painter = painterResource(id = R.drawable.on_boarding_woman),
            contentDescription = "Welcome woman"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .padding(horizontal = 90.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(5) {
                    Icon(

                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "star icon",
                        tint = inversePrimaryLight
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(85.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .weight(4.75f)
                    .height(40.dp),
                colors = ButtonDefaults
                    .buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                shape = RoundedCornerShape(6.dp),
                onClick = {
                    navHostController.popBackStack()
                    navHostController.navigate(MainScreens.LoginScreen.route)
                }
            ) {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(.5f))
            Button(
                modifier = Modifier
                    .weight(4.75f)
                    .height(40.dp),
                colors = ButtonDefaults
                    .buttonColors(
                        contentColor = Color.White,
                        containerColor = mainButtonColor
                    ),
                shape = RoundedCornerShape(6.dp),
                onClick = {
                    navHostController.popBackStack()
                    navHostController.navigate(MainScreens.SignupScreen.route)
                }
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }

}

@Preview
@Composable
private fun Test() {
    val navController = rememberNavController()
    OnBoardingScreen(navHostController = navController)
}