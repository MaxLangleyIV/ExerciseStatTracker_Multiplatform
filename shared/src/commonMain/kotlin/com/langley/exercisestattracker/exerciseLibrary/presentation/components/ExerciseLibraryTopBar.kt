package com.langley.exercisestattracker.exerciseLibrary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryEvent
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryState

@Composable
fun ExerciseLibraryTopBar(
    state: ExerciseLibraryState,
    onEvent: (ExerciseLibraryEvent) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager

){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){

        

        BasicSearchBar(
            state,
            modifier = Modifier,
            query = state.searchString,
            onEvent = onEvent,
            isDropdownOpen = state.isSearchDropdownOpen
        )
    }
}