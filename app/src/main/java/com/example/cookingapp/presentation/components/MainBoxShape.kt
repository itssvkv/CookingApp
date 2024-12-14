
package com.example.cookingapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    shapeRadius: Dp = 4.5.dp,
    shape: RoundedCornerShape = RoundedCornerShape(shapeRadius),
    blackColorRadius: Dp = 3.dp,
    content: @Composable () -> Unit

) {
    Box(
        modifier = modifier
            .width(boxWidth + blackColorRadius)
            .height(boxHeight + blackColorRadius),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .width(width = boxWidth)
                .height(height = boxHeight)
                .border(border = borderStroke, shape = shape)
                .clip(shape = shape)
                .background(backgroundBottomBoxColor)
                .align(Alignment.BottomStart),
        )
        Box(
            modifier = Modifier
                .width(width = boxWidth)
                .height(height = boxHeight)
                .border(border = borderStroke, shape = shape)
                .clip(shape = shape)
                .background(backgroundTopBoxColor)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

}