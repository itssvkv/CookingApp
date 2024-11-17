package com.example.cookingapp.utils

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.cookingapp.model.uistate.MainButtonState
import com.example.cookingapp.model.uistate.MainButtonStateValue
import com.example.cookingapp.navigation.Screen
import com.example.cookingapp.utils.Common.TAG
import mainButtonColor
import primaryContainerLight

@Composable
fun MainBoxShape(
    modifier: Modifier = Modifier,
    backgroundBottomBoxColor: Color = Color.Black,
    backgroundTopBoxColor: Color =
        primaryContainerLight,
    boxHeight: Dp = 40.dp,
    boxWidth: Dp = 40.dp,
    borderStroke: BorderStroke = BorderStroke(width = 1.dp, color = Color.Black),
    content: @Composable () -> Unit

) {
    Box(
        modifier = modifier
            .width(boxWidth + 3.dp)
            .height(boxHeight + 3.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .width(width = boxWidth)
                .height(height = boxHeight)
                .border(border = borderStroke, shape = RoundedCornerShape(4.5.dp))
                .clip(shape = RoundedCornerShape(4.5.dp))
                .background(backgroundBottomBoxColor)
                .align(Alignment.BottomStart),

            )
        Box(
            modifier = Modifier
                .width(width = boxWidth)
                .height(height = boxHeight)
                .border(border = borderStroke, shape = RoundedCornerShape(4.5.dp))
                .clip(shape = RoundedCornerShape(4.5.dp))
                .background(backgroundTopBoxColor)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

}

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit,
    contentColor: Color = Color.White,
    containerColor: Color = mainButtonColor,
    textColor: Color = Color.White,
    mainButtonState: MainButtonStateValue = MainButtonStateValue(),
    onSuccess: () -> Unit = {},
    onFailure: () -> Unit = {}
) {
    var isClickedButton by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    if (mainButtonState.isLoading) {
        isLoading = true
    } else if (mainButtonState.onSuccess) {
        Log.d(TAG, "MainButton: successssssssssssss")
    } else if (mainButtonState.onFailure) {

        Log.d(TAG, "MainButton: failed")

    } else {

        Log.d(TAG, "MainButton: empty")
    }
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = ButtonDefaults
            .buttonColors(
                contentColor = Color.White,
                containerColor = mainButtonColor,
                disabledContainerColor = mainButtonColor.copy(alpha = .5f)
            ),
        shape = RoundedCornerShape(6.dp),
        onClick = {
            onButtonClicked()
        },
        enabled = !mainButtonState.isLoading
    ) {
        if (mainButtonState.isLoading) {
            CircularProgressIndicator(
                modifier
                    .padding(8.dp)
                    .size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Round
            )
        } else {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun MainBoxShapePreview() {
    MainBoxShape() {

    }
}