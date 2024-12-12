package com.example.cookingapp.presentation.screen.signup

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.utils.Constants
import secondaryLight

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupScreenViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onBackClicked: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember {
        FocusRequester()
    }
    SignupScreenContent(
        uiState = uiState,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
        onSignupClicked = { viewModel.onSignupButtonClicked() },
        onLoginClicked = onNavigateToLogin,
        onBackClicked = onBackClicked,
        isFocusedChanged = viewModel::isFocusedChanged,
        focusRequester = focusRequester
    )

    LaunchedEffect(key1 = uiState.isRegisterSuccessful) {
        if (uiState.isRegisterSuccessful) {
            onNavigateToHome()
        }
    }
}


@Composable
fun SignupScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignupScreenUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onSignupClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    onBackClicked: () -> Unit,
    isFocusedChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderSection(
                onBackClicked = onBackClicked
            )
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            SignupScreenDataSection(
                email = uiState.email,
                password = uiState.password,
                confirmPassword = uiState.confirmPassword,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onConfirmPasswordChanged = onConfirmPasswordChanged,
                onSignupClicked = onSignupClicked,
                onLoginClicked = onLoginClicked,
                isButtonLoading = uiState.isLoading,
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
fun HeaderSection(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            IconButton(
                onClick = onBackClicked
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back Arrow"
                )
            }

            Text(
                text = "Back",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Welcome to Snophy!",
                style = MaterialTheme.typography.bodyLarge,
                color = secondaryLight,
                fontWeight = FontWeight.Bold
            )


        }
    }
}

@Composable
fun SignupScreenDataSection(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    isButtonLoading: Boolean,
    onSignupClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    isError: Boolean,
    errorType: String? = null,
    isFocusedChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    isFocused: Boolean = false
) {

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        MainTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = Constants.CustomTextFieldLabel.EMAIL,
            isFocusedChanged = isFocusedChanged,
            focusRequester = focusRequester,
            isFocused = isFocused
        )
        Spacer(modifier = Modifier.height(20.dp))
        MainTextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = Constants.CustomTextFieldLabel.PASSWORD,
            isPassword = true,
            trailingText = "Show",
            isFocusedChanged = isFocusedChanged,
            focusRequester = focusRequester,
            isFocused = isFocused
        )
        Spacer(modifier = Modifier.height(20.dp))
        MainTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChanged,
            label = Constants.CustomTextFieldLabel.PASSWORD,
            isPassword = true,
            trailingText = "Show",
            isFocusedChanged = isFocusedChanged,
            focusRequester = focusRequester,
            isFocused = isFocused
        )
        if (isError) {
            Text(text = errorType!!, style = MaterialTheme.typography.bodySmall, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(20.dp))
        MainButton(
            modifier = modifier
                .height(60.dp),
            text = "Sign up",
            isLoading = isButtonLoading,
            isEnabled = !isButtonLoading && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty(),
            onButtonClicked = onSignupClicked
        )
        SignupScreenFooter(
            onLoginClicked = onLoginClicked
        )

    }

}

@Composable
fun SignupScreenFooter(
    modifier: Modifier = Modifier, onLoginClicked: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Already have an account?",
            modifier = Modifier.clickable { onLoginClicked() },
            style = MaterialTheme.typography.bodySmall
        )
        Image(
            painter = painterResource(id = R.drawable.signup_door),
            contentDescription = "door",
            modifier = Modifier
                .padding(vertical = 24.dp)
                .size(200.dp)
        )

    }

}