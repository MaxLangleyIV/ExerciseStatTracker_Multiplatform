package com.langley.exercisestattracker.features.exerciseBuilder.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.core.presentation.composables.ErrorDisplayingTextField
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderViewModel
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.components.BodyRegionView
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.components.MetricsView
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.components.TagsView
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.components.TargetMusclesView
import com.langley.exercisestattracker.features.library.LibraryEvent
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun ExerciseBuilderScreen(
    dataSource: ExerciseAppDataSource,
    selectedExercise: ExerciseDefinition? = null,
    libraryOnEvent: (LibraryEvent) -> Unit,
    initialBuilderState: ExerciseBuilderState = ExerciseBuilderState(),
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    isVisible: Boolean
){

    val builderViewModel = getViewModel(
        key = "exerciseBuilderViewModel",
        factory = viewModelFactory {
            ExerciseBuilderViewModel(
                exerciseAppDataSource = dataSource,
                libraryOnEvent = libraryOnEvent,
            )
        }
    )

    val builderState by builderViewModel.state.collectAsState(initialBuilderState)

    if (selectedExercise != null && isVisible){
        if (!builderState.initialized){
            println("INITIALIZING BUILD SCREEN")
            (builderViewModel::onEvent)(ExerciseBuilderEvent.InitializeDefinition(selectedExercise))
            (builderViewModel::onEvent)(ExerciseBuilderEvent.DeclareAsInitialized)
        }
    }


    BasicBottomSheet(
        visible = isVisible,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() },
        verticalArrangement = Arrangement.Center
    )
    {
        // Top Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            IconButton(
                onClick = {
                    (builderViewModel::onEvent)(ExerciseBuilderEvent.CloseAddDef)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }

            if (selectedExercise != null){
                Text(
                    text = "Delete",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                        .clickable {
                            (builderViewModel::onEvent)(ExerciseBuilderEvent.DeleteDefinition)
                            (builderViewModel::onEvent)(ExerciseBuilderEvent.CloseAddDef)
                                   },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        }

        // Title Row
        Row(
            modifier = Modifier.fillMaxWidth(),
        ){
            if (selectedExercise == null){
                Text(
                    text = "New Exercise:",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    text = "Edit Exercise:",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Name Input
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            ErrorDisplayingTextField(
                value = builderState.newExerciseDefinition.exerciseName,
                placeholder = "Exercise Name",
                error = builderState.exerciseNameError,
                onValueChanged = {
                    (builderViewModel::onEvent)(ExerciseBuilderEvent.OnNameChanged(it))
                },
            )

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.tertiaryContainer)

            ){}
        }

        Spacer(Modifier.height(8.dp))

        // Content Body
        BodyRegionView(
            state = builderState,
            onEvent = builderViewModel::onEvent
        )


        if (!builderState.primaryTargetList.isNullOrEmpty()){
            TargetMusclesView(
                state = builderState,
                onEvent = builderViewModel::onEvent
            )
        }

        MetricsView(
            state = builderState,
            onEvent = builderViewModel::onEvent
        )

        TagsView(
            state = builderState,
            onEvent = builderViewModel::onEvent
        )

        Spacer(Modifier.height(8.dp))

        // Bottom Options
        OutlinedTextField(
            value = builderState.newExerciseDefinition.description,
            onValueChange = {
                (builderViewModel::onEvent)(ExerciseBuilderEvent.OnDescriptionChanged(it))
            },
            placeholder = {
                Text(text = "Exercise Description")
            },
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                (builderViewModel::onEvent)(ExerciseBuilderEvent.SaveOrUpdateDef)
            }
        ){
            Text(text = "Save")
        }

        Spacer(Modifier.height(4.dp))

        Button(
            onClick = {
                (builderViewModel::onEvent)(ExerciseBuilderEvent.CloseAddDef)
            }
        ){
            Text(text = "Cancel")
        }
    }
}



