package com.langley.exercisestattracker.features.exerciseBuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderEvent


@Composable
fun SelectableTextBoxWithEvent(
    modifier: Modifier = Modifier,
    text: String,
    textSize: TextUnit = 18.sp,
    boxSize: Dp = 84.dp,
    isClicked: Boolean,
    onEvent: (ExerciseBuilderEvent) -> Unit,
    event: ExerciseBuilderEvent,
){
    Box(
        modifier = modifier
            .size(boxSize)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .clickable { onEvent(event) }
            .border(
                width = 2.dp,

                color = if (isClicked) {
                    MaterialTheme.colorScheme.outline
                } else Color.Transparent,

                shape = RoundedCornerShape(16.dp)
            )
            .padding(4.dp)

    ){
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            text = text,
            fontSize = textSize,
            overflow = TextOverflow.Ellipsis
        )
    }
}




@Composable
fun SelectableTextBoxWithToggle(
    modifier: Modifier = Modifier,
    text: String,
    isClicked: Boolean,
    valueToToggle: MutableState<Boolean>
){
    Box(
        modifier = modifier
            .defaultMinSize(64.dp, 64.dp)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .clickable { valueToToggle.value = !valueToToggle.value }
            .border(
                width = 2.dp,

                color = if (isClicked) {
                    MaterialTheme.colorScheme.outline
                } else Color.Transparent,

                shape = RoundedCornerShape(16.dp)
            )
            .padding(4.dp)

    ){
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            text = text,
            fontSize = 16.sp,

        )
    }
}
