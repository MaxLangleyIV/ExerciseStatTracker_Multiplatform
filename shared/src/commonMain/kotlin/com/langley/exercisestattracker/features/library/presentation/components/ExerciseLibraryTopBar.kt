package com.langley.exercisestattracker.features.library.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Search Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Filter
            Box(
                modifier = Modifier
                    .weight(0.2F)
                    .padding(8.dp)
            ) {
                IconButton(onClick = { dropdownExpanded = true }) {
                    Icon(
                        modifier = Modifier.size(100.dp),
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

            // Search Bar
            Column(
                modifier = Modifier.weight(0.6F)
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
                        .weight(0.2F)
                        .padding(8.dp)
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
            else {
                Spacer(Modifier.weight(0.2F))
            }
        }

        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

        // Nav Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){

            // Definitions
            Column(
                modifier = Modifier
                    .weight(0.33f)
                    .background(
                        if (state.isShowingExercises) {
                            MaterialTheme.colorScheme.secondary
                        }
                        else {
                            MaterialTheme.colorScheme.background
                        }
                    )
                    .padding(4.dp)
                    .clickable { onEvent(LibraryEvent.SelectDefinitionsTab) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Exercises",
                    textAlign = TextAlign.Center,
                    color =
                    if (state.isShowingExercises) {
                        MaterialTheme.colorScheme.onSecondary
                    }
                    else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }

            Divider(modifier = Modifier.fillMaxHeight().width(1.dp), thickness = 1.dp)

            // Routines
            Column(
                modifier = Modifier
                    .weight(0.33f)
                    .background(
                        if (state.isShowingRoutines) {
                            MaterialTheme.colorScheme.secondary
                        }
                        else {
                            MaterialTheme.colorScheme.background
                        }
                    )
                    .padding(4.dp)
                    .clickable { onEvent(LibraryEvent.SelectRoutinesTab) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Routines",
                    textAlign = TextAlign.Center,
                    color =
                    if (state.isShowingRoutines) {
                        MaterialTheme.colorScheme.onSecondary
                    }
                    else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }

            Divider(modifier = Modifier.fillMaxHeight().width(1.dp), thickness = 1.dp)

            // Schedules
            Column(
                modifier = Modifier
                    .weight(0.33f)
                    .background(
                        if (state.isShowingSchedules) {
                            MaterialTheme.colorScheme.secondary
                        }
                        else {
                            MaterialTheme.colorScheme.background
                        }
                    )
                    .padding(4.dp)
                    .clickable { onEvent(LibraryEvent.SelectSchedulesTab) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Schedules",
                    textAlign = TextAlign.Center,
                    color =
                    if (state.isShowingSchedules) {
                        MaterialTheme.colorScheme.onSecondary
                    }
                    else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }

        }

    }
//    Box(
//        modifier = modifier
//    ){
//
//        // Filter
//        Column(
//            modifier = Modifier
//                .align(Alignment.CenterStart)
//                .padding(12.dp)
//        ) {
//            IconButton(onClick = { navController.navigateBack() }){
//                Icon(
//                    modifier = Modifier.size(200.dp),
//                    imageVector = Icons.Default.ArrowBackIos,
//                    contentDescription = "Back Button"
//                )
//            }
//
//            Box {
//                IconButton(onClick = { dropdownExpanded = true }) {
//                    Icon(
//                        modifier = Modifier.size(400.dp),
//                        imageVector = Icons.Default.Menu,
//                        contentDescription = "Filter",
//                    )
//                }
//
//                DropdownMenu(
//                    expanded = dropdownExpanded,
//                    onDismissRequest = {dropdownExpanded = false}
//                ){
//
//                    DropdownMenuItem(
//                        text = { Text("Favorite") },
//                        onClick = {
//                            dropdownExpanded = false
//                            onEvent(
//                                LibraryEvent
//                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Favorite())
//                            )
//                        }
//                    )
//
//                    Divider()
//
//                    DropdownMenuItem(
//                        text = { Text("Barbell") },
//                        onClick = {
//                            dropdownExpanded = false
//                            onEvent(
//                                LibraryEvent
//                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Barbell())
//                            )
//                        }
//                    )
//
//                    Divider()
//
//                    DropdownMenuItem(
//                        text = { Text("Dumbbell") },
//                        onClick = {
//                            dropdownExpanded = false
//                            onEvent(
//                                LibraryEvent
//                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Dumbbell())
//                            )
//                        }
//                    )
//
//                    Divider()
//
//                    DropdownMenuItem(
//                        text = { Text("Cardio") },
//                        onClick = {
//                            dropdownExpanded = false
//                            onEvent(
//                                LibraryEvent
//                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Cardio())
//                            )
//                        }
//                    )
//
//                    Divider()
//
//                    DropdownMenuItem(
//                        text = { Text("Calisthenics") },
//                        onClick = {
//                            dropdownExpanded = false
//                            onEvent(
//                                LibraryEvent
//                                    .SetCurrentFilterType(ExerciseLibraryFilterType.Calisthenic())
//                            )
//                        }
//                    )
//
//                    Divider()
//
//                    DropdownMenuItem(
//                        text = { Text("None") },
//                        onClick = {
//                            dropdownExpanded = false
//                            onEvent(
//                                LibraryEvent.ClearFilterType
//                            )
//                        }
//                    )
//                }
//            }
//        }
//
//        // Search Bar
//        Column(
//            modifier = Modifier.align(Alignment.Center)
//        ) {
//            BasicSearchBar(
//                state = state,
//                modifier = Modifier.width(256.dp),
//                onEvent = onEvent,
//                isDropdownOpen = state.isSearchDropdownOpen
//            )
//        }
//
//        // Clear button
//        if (state.searchString != "" || state.searchFilterType != null){
//            Column(
//                modifier = Modifier
//                    .align(Alignment.CenterEnd)
//                    .padding(16.dp)
//            ) {
//                Text(
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .clickable {
//                            onEvent(
//                                LibraryEvent.ClearFilterType
//                            )
//                            onEvent(LibraryEvent.OnSearchStringChanged(""))
//                        },
//                    text = "Clear",
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                )
//            }
//        }
//    }
}