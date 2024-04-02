package com.langley.exercisestattracker.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedTextContainer(
    modifier: Modifier = Modifier,
    text: String,
    textSize: TextUnit = 18.sp,
    textColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    textAlign: TextAlign = TextAlign.Center,
    boxMinWidth: Dp = 0.dp,
    boxMaxWidth: Dp = 200.dp,
    boxMinHeight: Dp = 0.dp,
    boxMaxHeight: Dp = 200.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    borderColor: Color = MaterialTheme.colorScheme.outlineVariant,
    borderWidth: Dp = 2.dp,
    roundedCornerSize: Dp = 16.dp,
    padding: Dp = 4.dp,
    maxLines: Int = 2,
    overFlow: TextOverflow = TextOverflow.Ellipsis
){
    Box(
        modifier = modifier
            .sizeIn(
                minWidth = boxMinWidth,
                maxWidth = boxMaxWidth,
                minHeight = boxMinHeight,
                maxHeight = boxMaxHeight
            )
            .clip(
                RoundedCornerShape(roundedCornerSize)
            )
            .background(backgroundColor)
            .border(
                width = borderWidth,

                color = borderColor,

                shape = RoundedCornerShape(roundedCornerSize)
            )
            .padding(padding)

    ){
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = textAlign,
            color = textColor,
            text = text,
            fontSize = textSize,
            maxLines = maxLines,
            overflow = overFlow
        )
    }
}