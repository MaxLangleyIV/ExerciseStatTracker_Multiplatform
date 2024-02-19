package com.langley.exercisestattracker.library.features.exerciseBuilder.presentation

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.library.LibraryEvent
import com.langley.exercisestattracker.library.LibraryState
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderState
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderViewModel
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseType
import com.langley.exercisestattracker.library.features.exerciseBuilder.presentation.components.CardioExerciseBuilderView
import com.langley.exercisestattracker.library.features.exerciseBuilder.presentation.components.StrengthExerciseBuilderView
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddNewExerciseDefView(
    appModule: AppModule,
    libraryState: LibraryState,
    libraryOnEvent: (LibraryEvent) -> Unit,
    initialBuilderState: ExerciseBuilderState = ExerciseBuilderState(),
    isVisible: Boolean,
    newExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource
){

    val exerciseBuilderViewModel = getViewModel(
        key = "exerciseBuilderViewModel",
        factory = viewModelFactory {
            ExerciseBuilderViewModel(appModule.exerciseAppDataSource)
        }
    )

    val builderState by exerciseBuilderViewModel.state.collectAsState(initialBuilderState)


    val viewModelOnEvent = exerciseBuilderViewModel::onEvent

    //Intro Column Visibility
    val introColumnVisible = remember { mutableStateOf(true) }

    // Main Exercise Builders
    val builderVisible = remember { mutableStateOf(false) }
    val strengthBuilderVisible = remember { mutableStateOf(false) }
    val cardioBuilderVisible = remember { mutableStateOf(false) }

    // Builder View Options
    val upperBodySelected = remember {
        mutableStateOf(newExerciseDefinition.bodyRegion.lowercase().contains("upper"))
    }
    val lowerBodySelected = remember {
        mutableStateOf(newExerciseDefinition.bodyRegion.lowercase().contains("lower"))
    }
    val coreSelected = remember {
        mutableStateOf(newExerciseDefinition.bodyRegion.lowercase().contains("core"))
    }

    // Tags Section Visibility
    val tagsSectionVisible = remember { mutableStateOf(true) }

    BasicBottomSheet(
        visible = isVisible,
        modifier = Modifier.fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() }
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
//                    introColumnVisible.value = true
//                    builderVisible.value = false
//                    strengthBuilderVisible.value = false
//                    cardioBuilderVisible.value = false
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
        }

        // Intro Column
        if (builderState.introColumnVisible){
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "I want to define a new..."
                )
                Button(
                    onClick = {
                        viewModelOnEvent(ExerciseBuilderEvent.ExerciseTypeSelected(ExerciseType.Strength))
                    }
                ){
                    Text( text = "Strength Exercise" )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        viewModelOnEvent(ExerciseBuilderEvent.ExerciseTypeSelected(ExerciseType.Cardio))
                    }
                ){
                    Text( text = "Cardio Exercise" )
                }
            }
        }

        // Builder Column
        if (builderState.builderVisible){

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

            // Content Body
            if (builderState.strengthBuilderVisible){
                StrengthExerciseBuilderView(
                    state = builderState,
                    newExerciseDefinition = exerciseBuilderViewModel.newExerciseDef,
                    onEvent = viewModelOnEvent,
                    upperBodySelectedState = upperBodySelected,
                    lowerBodySelectedState = lowerBodySelected,
                    coreBodySelectedState = coreSelected
                )

            }

            if (builderState.cardioBuilderVisible){

                CardioExerciseBuilderView(
                    state = builderState,
                    newExerciseDefinition = exerciseBuilderViewModel.newExerciseDef,
                    onEvent = viewModelOnEvent,
                    tagsSectionVisibleState = tagsSectionVisible
                )
            }

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
}



