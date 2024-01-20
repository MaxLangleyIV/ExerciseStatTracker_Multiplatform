package com.langley.exercisestattracker.exerciseLibrary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryEvent
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryState

@Composable
fun BasicSearchBar(
    state: ExerciseLibraryState,
    modifier: Modifier = Modifier,
    query: String = "",
    onEvent: (ExerciseLibraryEvent) -> Unit,
    isDropdownOpen: Boolean = false,
){
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ){
            TextField(
                value = query,
                onValueChange = {
                    onEvent(ExerciseLibraryEvent.OnSearchStringChanged(it))
                },
                shape = RoundedCornerShape(20.dp),
            )
        }

        Row {
            DropdownMenu(
            expanded = isDropdownOpen,
            onDismissRequest = { onEvent(ExerciseLibraryEvent.ToggleIsDropdownOpen) }
            ){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier
                ){
                    if (state.previousSearches == null){
                        item {
                            Text(text = "No previous searches found.")
                        }
                    }
                    else {
                        items(state.previousSearches){
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = it
                            )
                        }
                    }
                }
            }
        }
    }
}