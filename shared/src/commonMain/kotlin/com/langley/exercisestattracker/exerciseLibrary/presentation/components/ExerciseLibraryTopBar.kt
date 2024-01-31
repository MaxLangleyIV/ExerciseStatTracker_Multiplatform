package com.langley.exercisestattracker.exerciseLibrary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryEvent
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryFilterType
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryState

@Composable
fun ExerciseLibraryTopBar(

    state: ExerciseLibraryState,
    onEvent: (ExerciseLibraryEvent) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager

){
    var dropdownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ){

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(12.dp)
        ) {
            Box(
            ) {
                IconButton(onClick = { dropdownExpanded = true }) {
                    Icon(
                        modifier = Modifier.size(400.dp),
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Filter",
                    )
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = {dropdownExpanded = false}
                ){
                    DropdownMenuItem(
                        text = { Text("Barbell") },
                        onClick = {
                            onEvent(
                                ExerciseLibraryEvent
                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Barbell())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Dumbbell") },
                        onClick = {
                            onEvent(
                                ExerciseLibraryEvent
                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Dumbbell())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Favorite") },
                        onClick = {
                            onEvent(
                                ExerciseLibraryEvent
                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Favorite())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("None") },
                        onClick = {
                            onEvent(
                                ExerciseLibraryEvent.ClearFilterType
                            )
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            BasicSearchBar(
                state,
                modifier = Modifier.width(256.dp),
                query = state.searchString,
                onEvent = onEvent,
                isDropdownOpen = state.isSearchDropdownOpen
            )
        }

        if (state.searchString != "" || state.searchFilterType != null){
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            onEvent(
                                ExerciseLibraryEvent.ClearFilterType
                            )
                            onEvent(ExerciseLibraryEvent.OnSearchStringChanged(""))
                        },
                    text = "Clear",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
        }
    }
}