package com.example.cookingapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mainButtonColor


@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Int? = null,
    cornerRadius: Dp = 6.dp,
    onButtonClicked: () -> Unit = {},
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    activeColor: Color = mainButtonColor,
    inActiveColor: Color = mainButtonColor.copy(alpha = .5f),
    contentColor: Color = Color.White
) {

    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = ButtonDefaults
            .buttonColors(
                contentColor = contentColor,
                containerColor = activeColor,
                disabledContainerColor = inActiveColor
            ),
        shape = RoundedCornerShape(cornerRadius),
        onClick = onButtonClicked,
        enabled = isEnabled
    ) {
        if (text != null && !isLoading) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier
                    .padding(8.dp)
                    .size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Round
            )
        }
    }
}
