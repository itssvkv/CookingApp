package com.example.cookingapp.presentation.screen.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.model.uistate.MainButtonStateValue
import com.example.cookingapp.utils.Common
import com.example.cookingapp.utils.MainButton
import errorLight
import focusedTextFieldColor
import inverseSurfaceLight
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import secondaryLight
import unfocusedTextFieldColor

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
) {
    var emailTextValue by remember {
        mutableStateOf("")
    }
    var passwordTextValue by remember {
        mutableStateOf("")
    }
    var hidePassword by remember {
        mutableStateOf(true)
    }
    val coroutineScope = rememberCoroutineScope()
    var mainButtonState by remember {
        mutableStateOf(LoginScreenUiState())
    }
    val uiState by loginScreenViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(40.dp))
        CustomTextField(
            value = emailTextValue,
            onValueChange = { newValue ->
                emailTextValue = newValue
            },
            label = Common.CustomTextFieldLabel.EMAIL
        )
        Spacer(modifier = Modifier.height(20.dp))
        PasswordTextField(
            password = passwordTextValue,
            onPasswordChange = { newPassword ->
                passwordTextValue = newPassword
            },
            onTrailingTextClicked = { hidePassword = !hidePassword },
            hidePassword = hidePassword
        )
        ForgetPasswordSection {

        }
        MainButton(onButtonClicked = {
            loginScreenViewModel.checkTheLoginProcess(
                email = emailTextValue,
                password = passwordTextValue
            )
            coroutineScope.launch {
                loginScreenViewModel.uiState.collectLatest {
                    mainButtonState = it
                }
            }
        })
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back Arrow")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Back",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )

        }
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
fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChange: (String) -> Unit,
    onTrailingTextClicked: () -> Unit,
    hidePassword: Boolean
) {
    val visualTransformation =
        if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None

    val textOnPasswordTextField = if (hidePassword) "Show" else "Hide"
    CustomTextField(
        value = password,
        onValueChange = { newPassword ->
            onPasswordChange(newPassword)
        },
        label = Common.CustomTextFieldLabel.PASSWORD,
        visualTransformation = visualTransformation,
        trailingText = {
            Text(
                text = textOnPasswordTextField,
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        onTrailingTextClicked()
                    }
            )
        }
    )
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String = "",
    isShowTextView: Boolean = false,
    backgroundColor: Color = Color.Transparent,
    roundedShape: RoundedCornerShape = RoundedCornerShape(4.dp),
    focusedContainerColor: Color = focusedTextFieldColor,
    focusedTextColor: Color = Color.Black,
    focusedLabelColor: Color = focusedTextFieldColor,
    unfocusedContainerColor: Color = unfocusedTextFieldColor,
    unfocusedTextColor: Color = unfocusedTextFieldColor,
    unfocusedLabelColor: Color = unfocusedTextFieldColor,
    errorContainerColor: Color = errorLight,
    errorTextColor: Color = errorLight,
    errorLabelColor: Color = errorLight,
    label: Common.CustomTextFieldLabel = Common.CustomTextFieldLabel.EMAIL,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingText: @Composable() (() -> Unit)? = null
) {


    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(roundedShape),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedContainerColor,
            focusedTextColor = focusedTextColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedBorderColor = unfocusedContainerColor,
            unfocusedTextColor = unfocusedTextColor,
            unfocusedLabelColor = unfocusedLabelColor,
            errorContainerColor = errorContainerColor,
            errorLabelColor = errorLabelColor,
            errorTextColor = errorTextColor,

            ),
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(text = if (label.name == "EMAIL") "Email" else "Password")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = when (label) {
                Common.CustomTextFieldLabel.EMAIL -> KeyboardType.Email
                Common.CustomTextFieldLabel.PASSWORD -> KeyboardType.Password
            }
        ),
        suffix = {
            if (trailingText != null) {
                trailingText()
            }
        },
        visualTransformation = visualTransformation
    )


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

@Preview
@Composable
private fun CustomTextFieldPreview() {

}