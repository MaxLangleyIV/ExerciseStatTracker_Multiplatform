package com.langley.exercisestattracker.features.records.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.features.records.RecordsEvent
import com.langley.exercisestattracker.features.records.RecordsState

@Composable
fun RecordsSearchBar(
    state: RecordsState,
    modifier: Modifier = Modifier,
    query: String = "",
    onEvent: (RecordsEvent) -> Unit,
    isDropdownOpen: Boolean = false,
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ){
            OutlinedTextField(
                modifier = Modifier.focusRequester(FocusRequester())
                    .clickable { onEvent(RecordsEvent.ToggleIsSearchDropdownOpen) },
                value = query,
                onValueChange = {
                    onEvent(RecordsEvent.OnSearchStringChanged(it))
                },
                shape = RoundedCornerShape(20.dp),
                maxLines = 1
            )
        }

        Row {
            DropdownMenu(
                expanded = isDropdownOpen,
                onDismissRequest = { onEvent(RecordsEvent.ToggleIsSearchDropdownOpen) }
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