package com.langley.exercisestattracker.features.library.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TestComposable() {
    Column {
        Row {
            Text(text = "Test 1")
            Text(text = "Test 2")
        }
        Row {
            Text(text = "Test 3")
            Text(text = "Test 4")
        }
    }
}
@Preview
@Composable
fun Preview() {
    TestComposable()
}