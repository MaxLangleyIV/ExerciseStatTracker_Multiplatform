package com.langley.exercisestattracker.core.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier

@Composable
fun DropdownToggle(
    modifier: Modifier = Modifier,
    toggled: MutableState<Boolean> = mutableStateOf(false)
){
    if (!toggled.value){
        IconButton(
            modifier = modifier,
            onClick = {
                toggled.value = !toggled.value
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Toggle Dropdown"
            )
        }
    }
    else {
        IconButton(
            modifier = modifier,
            onClick = {
                toggled.value = !toggled.value
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropUp,
                contentDescription = "Close Dropdown"
            )
        }
    }
}