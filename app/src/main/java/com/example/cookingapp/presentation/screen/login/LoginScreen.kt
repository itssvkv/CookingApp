package com.example.cookingapp.presentation.screen.login

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import errorLight
import focusedTextFieldColor
import inverseSurfaceLight
import secondaryLight
import unfocusedTextFieldColor

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    var emailTextValue by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()
//        Spacer(modifier = Modifier.height(40.dp))
        CustomTextField(
            value = emailTextValue,
            onValueChange = { newValue ->
                emailTextValue = newValue
            },
            label = "Email",
            isShowTextView = true
        )
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
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
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 80.dp),
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
    label: String
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
        colors = TextFieldDefaults.colors(
            focusedContainerColor = focusedContainerColor,
            focusedTextColor = focusedTextColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedContainerColor = unfocusedContainerColor,
            unfocusedTextColor = unfocusedTextColor,
            unfocusedLabelColor = unfocusedLabelColor,
            errorContainerColor = errorContainerColor,
            errorLabelColor = errorLabelColor,
            errorTextColor = errorTextColor
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(text = label)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = when (label) {
                "Email" -> KeyboardType.Email
                "Password" -> KeyboardType.Password
                else -> {
                    KeyboardType.Text
                }
            }
        ),
        suffix = {
            if (isShowTextView) {
                Text(
                    text = "Show",
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    )


}