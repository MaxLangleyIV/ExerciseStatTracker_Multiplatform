package com.langley.exercisestattracker.features.library.features.exerciseBuilder.presentation

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
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.core.presentation.composables.ErrorDisplayingTextField
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.features.library.LibraryViewModel
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.ExerciseBuilderState
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.ExerciseBuilderViewModel
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.presentation.components.BodyRegionView
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.presentation.components.MetricsView
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.presentation.components.TagsView
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.presentation.components.TargetMusclesView
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun ExerciseBuilderScreen(
    isVisible: Boolean,
    appModule: AppModule,
    libraryViewModel: LibraryViewModel,
//    libraryOnEvent: (LibraryEvent) -> Unit,
    initialBuilderState: ExerciseBuilderState = ExerciseBuilderState(),
//    initialExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
){

    val builderViewModel = getViewModel(
        key = "exerciseBuilderViewModel",
        factory = viewModelFactory {
            ExerciseBuilderViewModel(
                exerciseAppDataSource = appModule.exerciseAppDataSource,
                libraryViewModel = libraryViewModel,
//                libraryOnEvent = libraryOnEvent,
//                initialExerciseDef = initialExerciseDefinition
            )
        }
    )

    val builderState by builderViewModel.state.collectAsState(initialBuilderState)

    if (isVisible){
        println("INITIALIZING BUILD SCREEN")
        (builderViewModel::onEvent)(ExerciseBuilderEvent.InitializeDefinition)
    }



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
//                    libraryOnEvent(LibraryEvent.CloseAddDefClicked)
                    (builderViewModel::onEvent)(ExerciseBuilderEvent.CloseAddDef)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }

            if (libraryViewModel.state.value.selectedExerciseDefinition != null){
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
            if (libraryViewModel.state.value.selectedExerciseDefinition == null){
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
//            newExerciseDefinition = builderViewModel.newExerciseDef,
            onEvent = builderViewModel::onEvent,
        )

//        if (builderState.bodyRegion != null && builderState.bodyRegionSubGroup != null){
        if (!builderState.primaryTargetList.isNullOrEmpty()){
            TargetMusclesView(
                state = builderState,
//                newExerciseDefinition = builderViewModel.newExerciseDef,
                onEvent = builderViewModel::onEvent,
            )
        }

        MetricsView(
            state = builderState,
//            newExerciseDefinition = builderViewModel.newExerciseDef,
            onEvent = builderViewModel::onEvent
        )

        TagsView(
            state = builderState,
//            newExerciseDefinition = builderViewModel.newExerciseDef,
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
//                libraryOnEvent(LibraryEvent.CloseAddDefClicked)
                (builderViewModel::onEvent)(ExerciseBuilderEvent.CloseAddDef)
            }
        ){
            Text(text = "Cancel")
        }

    }
}



