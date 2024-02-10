package com.langley.exercisestattracker.records

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.library.LibraryEvent
import com.langley.exercisestattracker.library.components.EditExerciseDefDetailsView
import com.langley.exercisestattracker.library.components.ExerciseDefDetailsView
import com.langley.exercisestattracker.library.components.ExerciseDefinitionListItem
import com.langley.exercisestattracker.library.components.ExerciseLibraryTopBar
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import com.langley.exercisestattracker.navigation.Screen

@Composable
fun RecordsScreen(
    modifier: Modifier = Modifier,
    state: RecordsState,
    onEvent: (RecordsEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .focusable(true)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() },
    ){
//        RecordsTopBar(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(0.dp,16.dp),
//            state = state,
//            onEvent = onEvent,
//            focusManager = focusManager
//        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(vertical = 8.dp),

            content = {
                items(state.exerciseRecords){ exerciseRecord: ExerciseRecord ->
                    ExerciseRecordListItem(
                        exerciseRecord,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .focusable(true)
                            .clickable {
                                focusManager.clearFocus()
                                onEvent(RecordsEvent.RecordSelected(exerciseRecord))
                            },
                    )
                }
            }
        )
    }

//    RecordDetailsView(
//        isVisible = state.isRecordDetailsSheetOpen,
//        onEvent = onEvent,
//        selectedExerciseDefinition = state.selectedRecord
//    )
//
//    EditRecordDetailsView(
//        isVisible = state.isEditRecordDetailsSheetOpen,
//        state = state,
//        onEvent = onEvent,
//    )

}

@Composable
fun RecordsTopBar(
    modifier: Modifier,
    state: RecordsState,
    onEvent: (RecordsEvent) -> Unit,
    focusManager: FocusManager
) {
    TODO("Not yet implemented")
}

@Composable
fun EditRecordDetailsView(
    isVisible: Boolean,
    state: RecordsState,
    onEvent: (RecordsEvent) -> Unit,
) {
    TODO("Not yet implemented")
}

@Composable
fun RecordDetailsView(
    isVisible: Boolean,
    onEvent: (RecordsEvent) -> Unit,
    selectedExerciseDefinition: ExerciseRecord?
) {
    TODO("Not yet implemented")
}

@Composable
fun ExerciseRecordListItem(exerciseRecord: ExerciseRecord, modifier: Modifier) {
    TODO("Not yet implemented")
}
