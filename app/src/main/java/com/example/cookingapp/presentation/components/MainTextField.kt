package com.example.cookingapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.cookingapp.utils.Constants
import errorLight
import primary
import onPrimary


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
    label: Constants.CustomTextFieldLabel = Constants.CustomTextFieldLabel.EMAIL,
    trailingText: String? = null,
    leadingIcon: ImageVector? = null,
    focusRequester: FocusRequester = FocusRequester(),
    onSearch: (KeyboardActionScope.() -> Unit)? = null,
    trailingIcon: ImageVector = Icons.Default.Close,
    onTrailingIconClicked: () -> Unit = {}

) {

    var isShowPassword: Boolean by remember { mutableStateOf(false) }
    var visualTransformation: VisualTransformation by remember { mutableStateOf(VisualTransformation.None) }
    visualTransformation =
        if (isPassword && !isShowPassword) PasswordVisualTransformation() else VisualTransformation.None
    var isFocus by remember { mutableStateOf(false) }

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
            .clip(roundedShape)
            .focusRequester(focusRequester)
            .onFocusChanged { isFocus = it.isFocused },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primary,
            focusedTextColor = Color.Black,
            focusedLabelColor = primary,
            unfocusedBorderColor = onPrimary,
            unfocusedTextColor = onPrimary,
            unfocusedLabelColor = onPrimary,
            errorContainerColor = errorLight,
            errorLabelColor = errorLight,
            errorTextColor = errorLight,
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(text = label.name)
        },
        keyboardActions = KeyboardActions(
            onSearch = onSearch
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = when (label) {
                Constants.CustomTextFieldLabel.EMAIL -> KeyboardType.Email
                Constants.CustomTextFieldLabel.PASSWORD -> KeyboardType.Password
                else -> KeyboardType.Text
            },
            imeAction = imeAction
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

            if (label == Constants.CustomTextFieldLabel.SEARCH && value.isNotEmpty()) {
                IconButton(modifier = Modifier.size(16.dp), onClick = onTrailingIconClicked) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = "",
                        tint = if (isFocus) primary else onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

        },
        visualTransformation = visualTransformation,
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "",
                    tint = if (isFocus) primary else onPrimary
                )
            }
        } else null
    )


}
