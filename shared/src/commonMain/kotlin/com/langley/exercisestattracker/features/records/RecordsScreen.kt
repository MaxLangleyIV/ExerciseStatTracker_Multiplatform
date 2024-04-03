package com.langley.exercisestattracker.features.records

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.features.records.components.RecordDetailsView
import com.langley.exercisestattracker.features.records.components.RecordListItem
import com.langley.exercisestattracker.features.records.components.RecordsTopBar
import com.langley.exercisestattracker.navigation.ExerciseAppNavController

@Composable
fun RecordsScreen(
    modifier: Modifier = Modifier,
    recordsState: RecordsState = RecordsState(),
    onEvent: (RecordsEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController
){


    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .focusable(true)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { focusManager.clearFocus() },
        ){
            RecordsTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp,16.dp),
                state = recordsState,
                onEvent = onEvent,
                focusManager = focusManager,
                navController = navController
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(vertical = 8.dp),

                content = {
                    items(
                        items = recordsState.exerciseRecords,
                        key = {item: ExerciseRecord ->  item.exerciseRecordId!!}
                    ){ exerciseRecord: ExerciseRecord ->
                        RecordListItem(
                            exerciseRecord,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .focusable(true)
                                .clickable {
                                    focusManager.clearFocus()
                                    onEvent(
                                        RecordsEvent.RecordSelected(exerciseRecord)
                                    )
                                },
                        )
                    }
                }
            )
        }

    RecordDetailsView(
        isVisible = recordsState.isRecordDetailsSheetOpen,
        onEvent = onEvent,
        selectedRecord = recordsState.selectedRecord ?: ExerciseRecord()
    )
//
//    EditRecordDetailsView(
//        isVisible = state.isEditRecordDetailsSheetOpen,
//        state = state,
//        onEvent = onEvent,
//    )
    }



}


@Composable
fun EditRecordDetailsView(
    isVisible: Boolean,
    state: RecordsState,
    onEvent: (RecordsEvent) -> Unit,
) {
    TODO("Not yet implemented")
}



