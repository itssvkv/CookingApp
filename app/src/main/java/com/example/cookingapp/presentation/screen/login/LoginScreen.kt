package com.example.cookingapp.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.utils.Constants
import inverseSurfaceLight
import secondaryLight


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToForgetPassword: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember {
        FocusRequester()
    }

    LoginScreenContent(
        modifier = modifier,
        uiState = uiState,
        onEmailChange = viewModel::onEmailChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onLoginClicked = viewModel::onLoginButtonClicked,
        onForgetPasswordClicked = onNavigateToForgetPassword,
        onNavigateToRegister = onNavigateToRegister,
        isFocusedChanged = viewModel::isFocusedChanged,
        focusRequester = focusRequester
    )

    LaunchedEffect(key1 = uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onNavigateToHome()
        }
    }

}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginScreenUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onForgetPasswordClicked: () -> Unit,
    onNavigateToRegister: () -> Unit = {},
    isFocusedChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderSection()
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            LoginDataSection(
                email = uiState.email,
                password = uiState.password,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                isButtonLoading = uiState.isLoading,
                onLoginClicked = onLoginClicked,
                onForgetPasswordClicked = onForgetPasswordClicked,
                onNavigateToRegister = onNavigateToRegister,
                isError = uiState.isError,
                errorType = uiState.errorType,
                isFocusedChanged = isFocusedChanged,
                focusRequester = focusRequester,
                isFocused = uiState.isFocused
            )
        }

    }
}

@Composable
fun LoginDataSection(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isButtonLoading: Boolean,
    onLoginClicked: () -> Unit,
    onForgetPasswordClicked: () -> Unit,
    onNavigateToRegister: () -> Unit = {},
    isError: Boolean,
    errorType: String? = null,
    isFocusedChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    isFocused: Boolean = false
) {

    Column(modifier = modifier.fillMaxWidth()) {
        MainTextField(
            value = email,
            onValueChange = onEmailChange,
            label = Constants.CustomTextFieldLabel.EMAIL,
            focusRequester = focusRequester,
        )
        Spacer(modifier = Modifier.height(20.dp))
        MainTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = Constants.CustomTextFieldLabel.PASSWORD,
            isPassword = true,
            trailingText = "Show",
            focusRequester = focusRequester,
        )
        if (isError) {
            Text(text = errorType!!, style = MaterialTheme.typography.bodySmall, color = Color.Red)
        }
        ForgetPasswordSection(onForgetPasswordClicked = onForgetPasswordClicked)
        MainButton(
            modifier = modifier
                .height(60.dp),
            text = "Log In",
            isLoading = isButtonLoading,
            isEnabled = !isButtonLoading && email.isNotEmpty() && password.isNotEmpty(),
            onButtonClicked = onLoginClicked
        )
        LoginScreenFooter(
            onRegisterClicked = onNavigateToRegister
        )

    }

}


@Composable
fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Welcome back!",
                style = MaterialTheme.typography.bodyLarge,
                color = secondaryLight,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Good to see you back",
                style = MaterialTheme.typography.titleSmall,
                color = inverseSurfaceLight
            )
        }
    }
}


@Composable
fun ForgetPasswordSection(
    modifier: Modifier = Modifier,
    onForgetPasswordClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 12.dp)
                .clickable {
                    onForgetPasswordClicked()
                },
            text = "Forget Password?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun LoginScreenFooter(
    modifier: Modifier = Modifier,
    onRegisterClicked: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Don't have an account?",
            modifier = Modifier
                .clickable { onRegisterClicked() },
            style = MaterialTheme.typography.bodySmall
        )
        Image(
            painter = painterResource(id = R.drawable.login_door), contentDescription = "door",
            modifier = Modifier
                .padding(vertical = 24.dp)
                .size(200.dp)
        )

    }

}

@Preview
@Composable
private fun CustomTextFieldPreview() {

}