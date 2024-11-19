package com.example.cookingapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.cookingapp.utils.Constants
import errorLight
import focusedTextFieldColor
import unfocusedTextFieldColor


@Composable
fun MainTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String = "",
    roundedShape: RoundedCornerShape = RoundedCornerShape(4.dp),
    error: String? = null,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: Constants.CustomTextFieldLabel = Constants.CustomTextFieldLabel.EMAIL,
    trailingText: String? = null,


    ) {

    var isFocused: Boolean by remember { mutableStateOf(false) }
    var isShowPassword: Boolean by remember { mutableStateOf(false) }
    var visualTransformation: VisualTransformation by remember { mutableStateOf(VisualTransformation.None) }
    visualTransformation =
        if (isPassword && !isShowPassword) PasswordVisualTransformation() else VisualTransformation.None


    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        enabled = enabled,
        isError = error != null,
        singleLine = singleLine,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(roundedShape)
            .onFocusChanged { isFocused = it.isFocused },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedTextFieldColor,
            focusedTextColor = Color.Black,
            focusedLabelColor = focusedTextFieldColor,
            unfocusedBorderColor = unfocusedTextFieldColor,
            unfocusedTextColor = unfocusedTextFieldColor,
            unfocusedLabelColor = unfocusedTextFieldColor,
            errorContainerColor = errorLight,
            errorLabelColor = errorLight,
            errorTextColor = errorLight,

            ),
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(text = if (label.name == "EMAIL") "Email" else "Password")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = when (label) {
                Constants.CustomTextFieldLabel.EMAIL -> KeyboardType.Email
                Constants.CustomTextFieldLabel.PASSWORD -> KeyboardType.Password
            }
        ),
        suffix = {
            if (trailingText != null || isPassword) {
                if (trailingText != null) {
                    if (isShowPassword) {
                        visualTransformation = PasswordVisualTransformation()
                        Text(
                            text = "Show",
                            style = MaterialTheme.typography.bodyMedium,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .clickable {
                                    isShowPassword = !isShowPassword
                                })
                    } else {
                        visualTransformation = VisualTransformation.None
                        Text(
                            text = "Show",
                            style = MaterialTheme.typography.bodyMedium,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .clickable {
                                    isShowPassword = !isShowPassword
                                })
                    }
                }
            }
        },
        visualTransformation = visualTransformation
    )


}
