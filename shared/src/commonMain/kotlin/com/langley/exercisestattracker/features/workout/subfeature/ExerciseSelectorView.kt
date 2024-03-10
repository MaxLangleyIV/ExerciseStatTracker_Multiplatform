package com.langley.exercisestattracker.features.workout.subfeature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import com.langley.exercisestattracker.features.workout.WorkoutViewModel

@Composable
fun ExerciseSelectorView(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .focusable(true)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() },
    ){
//        ExerciseLibraryTopBar(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(0.dp,16.dp),
//            state = libraryState,
//            onEvent = libraryViewModel::onEvent,
//            focusManager = focusManager,
//            navController = navController
//        )

//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp)
//                .background(MaterialTheme.colorScheme.background),
//            contentPadding = PaddingValues(vertical = 8.dp),
//
//            content = {
//                items(
//                    items = libraryState.exerciseDefinitions,
//                    key = {item: ExerciseDefinition ->  item.exerciseDefinitionId!!}
//                )
//                { exerciseDefinition: ExerciseDefinition ->
//                    ExerciseDefinitionListItem(
//                        exerciseDefinition,
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(8.dp)
//                            .focusable(true)
//                            .clickable {
//                                focusManager.clearFocus()
//                                (libraryViewModel::onEvent)(
//                                    LibraryEvent.DefinitionSelected(exerciseDefinition)
//                                )
//                            },
//                    )
//                }
//            }
//        )
    }

}