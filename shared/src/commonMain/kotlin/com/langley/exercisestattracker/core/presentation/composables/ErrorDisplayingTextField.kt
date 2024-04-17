package com.langley.exercisestattracker.core.presentation.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorDisplayingTextField(
    value: String,
    placeholder: String,
    error: String?,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable() (() -> Unit)? = null
){
    OutlinedTextField(
        value = value,
        label = label,
        placeholder = {
            Text(text = placeholder)
        },
        onValueChange = onValueChanged,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    )
    if (error != null){
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error
        )
    }
}