package com.langley.exercisestattracker.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun DividerRow(
    modifier: Modifier =
        Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp)
        .height(2.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colorScheme.outline)
){

    Row(
        modifier = modifier
    ){}

}