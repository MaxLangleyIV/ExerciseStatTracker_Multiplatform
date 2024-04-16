package com.langley.exercisestattracker.features.library.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.LibraryState
import com.langley.exercisestattracker.navigation.ExerciseAppNavController

@Composable
fun LibraryTopBar(

    searchString: String = "",
    onSearchStringChanged: (String) -> Unit = {},
    filterType: ExerciseLibraryFilterType? = null,
    onFilterTypeChanged: (ExerciseLibraryFilterType?) -> Unit = {},
    isShowingExercises: Boolean = true,
    isShowingRoutines: Boolean = false,
    isShowingSchedules: Boolean = false,
    onShowExercisesSelected: () -> Unit = {},
    onShowRoutinesSelected: () -> Unit = {},
    onShowSchedulesSelected: () -> Unit = {},
    onDefSelected: (ExerciseDefinition) -> Unit = {},
    onRoutineSelected: (ExerciseRoutine) -> Unit = {},
    onScheduleSelected: (ExerciseSchedule) -> Unit = {},
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    showCloseButton: Boolean = false,
    onClose: () -> Unit = {},
    showSchedulesTab: Boolean = true

){
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Library")
//        }

        // Search Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
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
                            onFilterTypeChanged(ExerciseLibraryFilterType.Favorite())
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Barbell") },
                        onClick = {
                            dropdownExpanded = false
                            onFilterTypeChanged(ExerciseLibraryFilterType.Barbell())
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Dumbbell") },
                        onClick = {
                            dropdownExpanded = false
                            onFilterTypeChanged(ExerciseLibraryFilterType.Dumbbell())
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Cardio") },
                        onClick = {
                            dropdownExpanded = false
                            onFilterTypeChanged(ExerciseLibraryFilterType.Cardio())
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Calisthenics") },
                        onClick = {
                            dropdownExpanded = false
                            onFilterTypeChanged(ExerciseLibraryFilterType.Calisthenic())
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("None") },
                        onClick = {
                            dropdownExpanded = false
                            onFilterTypeChanged(null)
                        }
                    )
                }
            }

            // Search Bar
            OutlinedTextField(
                modifier = Modifier.focusRequester(FocusRequester())
                    .weight(0.5F),
                value = searchString,
                onValueChange = { onSearchStringChanged(it) },
                shape = RoundedCornerShape(20.dp),
                maxLines = 1
            )

            // Clear button
            if (searchString != "" || filterType != null){
                Column(
                    modifier = Modifier
                        .weight(0.2F)
                        .padding(8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                onFilterTypeChanged(null)
                                onSearchStringChanged("")
                            },
                        text = "Clear",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
            }

            // Close Button
            if (showCloseButton){
                IconButton(
                    modifier = Modifier.weight(0.2F),
                    onClick = {
                        onClose()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Close"
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
                        if (isShowingExercises) {
                            MaterialTheme.colorScheme.secondary
                        }
                        else {
                            MaterialTheme.colorScheme.background
                        }
                    )
                    .padding(4.dp)
                    .clickable { onShowExercisesSelected() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Exercises",
                    textAlign = TextAlign.Center,
                    color =
                    if (isShowingExercises) {
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
                        if (isShowingRoutines) {
                            MaterialTheme.colorScheme.secondary
                        }
                        else {
                            MaterialTheme.colorScheme.background
                        }
                    )
                    .padding(4.dp)
                    .clickable { onShowRoutinesSelected() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Routines",
                    textAlign = TextAlign.Center,
                    color =
                    if (isShowingRoutines) {
                        MaterialTheme.colorScheme.onSecondary
                    }
                    else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }

            if (showSchedulesTab){
                Divider(modifier = Modifier.fillMaxHeight().width(1.dp), thickness = 1.dp)

                // Schedules
                Column(
                    modifier = Modifier
                        .weight(0.33f)
                        .background(
                            if (isShowingSchedules) {
                                MaterialTheme.colorScheme.secondary
                            }
                            else {
                                MaterialTheme.colorScheme.background
                            }
                        )
                        .padding(4.dp)
                        .clickable { onShowSchedulesSelected() },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Schedules",
                        textAlign = TextAlign.Center,
                        color =
                        if (isShowingSchedules) {
                            MaterialTheme.colorScheme.onSecondary
                        }
                        else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                }
            }

        }

    }
}