package com.example.cookingapp.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.Constants.TAG
import errorLight
import onPrimary
import primary

@Composable
fun NewTextField(
    modifier: Modifier = Modifier,
    isCounter: Boolean = false,
    counterValue: Int? = 0,
    onValueChange: (String) -> Unit,
    value: String = "",
    title: String = "",
    roundedShape: RoundedCornerShape = RoundedCornerShape(4.dp),
    error: Boolean = false,
    singleLine: Boolean = true,
    height: Dp = if (singleLine) 60.dp else 110.dp,
    enabled: Boolean = true,
    isFocusedChanged: (Boolean) -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
    isFocused: Boolean = false,
    onPlusCounter: () -> Unit = {},
    onMinusCounter: () -> Unit = {},
    trailingIcon: ImageVector = Icons.Default.Close,
    onTrailingIconClicked: () -> Unit = {},
    imeAction: ImeAction = ImeAction.Next
) {
    var isFocus by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            isError = error,
            singleLine = singleLine,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .height(height)
                .clip(roundedShape)
                .onFocusChanged {
                    Log.d(TAG, "MainTextField: $isFocused")
                    isFocus = it.isFocused
                }
                .focusRequester(focusRequester),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primary,
                focusedTextColor = Color.Black,
                focusedLabelColor = primary,
                unfocusedBorderColor = onPrimary,
                unfocusedTextColor = onPrimary,
                unfocusedLabelColor = onPrimary,
                errorLabelColor = errorLight,

                ),
            textStyle = MaterialTheme.typography.bodyLarge,
            suffix = {
                if (isCounter) {
                    Column(
                        modifier = Modifier.height(28.dp),
                    ) {
                        IconButton(
                            onClick = onPlusCounter,
                            modifier = Modifier.weight(5f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "KeyboardArrowUp",
                                tint = Color.Black
                            )
                        }
                        IconButton(
                            onClick = onMinusCounter,
                            modifier = Modifier.weight(5f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "KeyboardArrowUp",
                                tint = Color.Black
                            )
                        }

                    }
                } else {
                    if (isFocus) {
                        IconButton(
                            modifier = Modifier.size(16.dp),
                            onClick = { onValueChange("") }
                        ) {
                            Icon(
                                imageVector = trailingIcon,
                                contentDescription = "",
                                tint = if (isFocus) primary else onPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = if (isCounter) KeyboardType.Number else KeyboardType.Text
            )
        )
    }
}

@Preview
@Composable
private fun NewTextFieldPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), contentAlignment = Alignment.Center
    ) {

    }
}