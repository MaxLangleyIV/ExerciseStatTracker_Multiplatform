package com.langley.exercisestattracker.library.features.exerciseBuilder.presentation

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.presentation.composables.ErrorDisplayingTextField
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.library.LibraryEvent
import com.langley.exercisestattracker.library.LibraryState
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderState
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderViewModel
import com.langley.exercisestattracker.library.features.exerciseBuilder.presentation.components.MetricsAndTagsView
import com.langley.exercisestattracker.library.features.exerciseBuilder.presentation.components.BodyRegionAndTargetMusclesView
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun AddNewExerciseDefView(
    appModule: AppModule,
    libraryOnEvent: (LibraryEvent) -> Unit,
    initialBuilderState: ExerciseBuilderState = ExerciseBuilderState(),
    isVisible: Boolean,
    newExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource
){

    val exerciseBuilderViewModel = getViewModel(
        key = "exerciseBuilderViewModel",
        factory = viewModelFactory {
            ExerciseBuilderViewModel(appModule.exerciseAppDataSource)
        }
    )

    val viewModelOnEvent = exerciseBuilderViewModel::onEvent

    val builderState by exerciseBuilderViewModel.state.collectAsState(initialBuilderState)


    BasicBottomSheet(
        visible = isVisible,
        modifier = Modifier.fillMaxSize()
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
                    libraryOnEvent(LibraryEvent.CloseAddDefClicked)
                    viewModelOnEvent(ExerciseBuilderEvent.CloseAddDefClicked)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
        }

        // Title Row
        Row(
            modifier = Modifier.fillMaxWidth(),
        ){
            Text(
                text = "New Exercise:",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Name Input
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            ErrorDisplayingTextField(
                value = newExerciseDefinition.exerciseName,
                placeholder = "Exercise Name",
                error = builderState.exerciseNameError,
                onValueChanged = {
                    viewModelOnEvent(ExerciseBuilderEvent.OnNameChanged(it))
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
        BodyRegionAndTargetMusclesView(
            state = builderState,
            newExerciseDefinition = exerciseBuilderViewModel.newExerciseDef,
            onEvent = viewModelOnEvent,
        )

        MetricsAndTagsView(
            state = builderState,
            newExerciseDefinition = exerciseBuilderViewModel.newExerciseDef,
            onEvent = viewModelOnEvent
        )

        // Bottom Options
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = newExerciseDefinition.description,
            onValueChange = {
                viewModelOnEvent(ExerciseBuilderEvent.OnDescriptionChanged(it))
            },
            placeholder = {
                Text(text = "Exercise Description")
            },
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                viewModelOnEvent(ExerciseBuilderEvent.SaveOrUpdateDef)
                libraryOnEvent(LibraryEvent.CloseAddDefClicked)
            }
        ){
            Text(text = "Save")
        }

        Spacer(Modifier.height(4.dp))

        Button(
            onClick = {
                libraryOnEvent(LibraryEvent.CloseAddDefClicked)
                viewModelOnEvent(ExerciseBuilderEvent.CloseAddDefClicked)
            }
        ){
            Text(text = "Cancel")
        }

    }
}



