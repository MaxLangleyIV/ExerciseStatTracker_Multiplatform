package com.langley.exercisestattracker.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DividerColumn(
    modifier: Modifier =
        Modifier
            .fillMaxHeight()
            .width(6.dp)
            .background(MaterialTheme.colorScheme.outline)
){
    Column(modifier = modifier) { }
}