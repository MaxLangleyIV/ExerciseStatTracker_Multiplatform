package com.langley.exercisestattracker.features.library.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.LibraryState
import com.langley.exercisestattracker.navigation.ExerciseAppNavController

@Composable
fun ExerciseLibraryTopBar(

    state: LibraryState,
    onEvent: (LibraryEvent) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    navController: ExerciseAppNavController

){
    var dropdownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ){

        // Filter
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(12.dp)
        ) {
            IconButton(onClick = { navController.navigateBack() }){
                Icon(
                    modifier = Modifier.size(200.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button"
                )
            }

            Box {
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
                        text = { Text("Favorite") },
                        onClick = {
                            dropdownExpanded = false
                            onEvent(
                                LibraryEvent
                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Favorite())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Barbell") },
                        onClick = {
                            dropdownExpanded = false
                            onEvent(
                                LibraryEvent
                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Barbell())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Dumbbell") },
                        onClick = {
                            dropdownExpanded = false
                            onEvent(
                                LibraryEvent
                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Dumbbell())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Cardio") },
                        onClick = {
                            dropdownExpanded = false
                            onEvent(
                                LibraryEvent
                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Cardio())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Calisthenics") },
                        onClick = {
                            dropdownExpanded = false
                            onEvent(
                                LibraryEvent
                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Calisthenic())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("None") },
                        onClick = {
                            dropdownExpanded = false
                            onEvent(
                                LibraryEvent.ClearFilterType
                            )
                        }
                    )
                }
            }
        }

        // Search Bar
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            BasicSearchBar(
                state = state,
                modifier = Modifier.width(256.dp),
                onEvent = onEvent,
                isDropdownOpen = state.isSearchDropdownOpen
            )
        }

        // Clear button
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
                                LibraryEvent.ClearFilterType
                            )
                            onEvent(LibraryEvent.OnSearchStringChanged(""))
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