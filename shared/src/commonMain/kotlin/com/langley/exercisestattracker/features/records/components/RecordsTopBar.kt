package com.langley.exercisestattracker.features.records.components

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
import com.langley.exercisestattracker.features.records.RecordsEvent
import com.langley.exercisestattracker.features.records.RecordsFilterType
import com.langley.exercisestattracker.features.records.RecordsState
import com.langley.exercisestattracker.navigation.ExerciseAppNavController


@Composable
fun RecordsTopBar(

    state: RecordsState,
    onEvent: (RecordsEvent) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    navController: ExerciseAppNavController

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
            IconButton(onClick = { navController.navigateBack() }){
                Icon(
                    modifier = Modifier.size(200.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button"
                )
            }
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
                                RecordsEvent
                                    .SetCurrentFilterType(RecordsFilterType.Barbell())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("Dumbbell") },
                        onClick = {
                            onEvent(
                                RecordsEvent
                                    .SetCurrentFilterType(RecordsFilterType.Dumbbell())
                            )
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = { Text("None") },
                        onClick = {
                            onEvent(
                                RecordsEvent.ClearFilterType
                            )
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            RecordsSearchBar(
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
                                RecordsEvent.ClearFilterType
                            )
                            onEvent(RecordsEvent.OnSearchStringChanged(""))
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