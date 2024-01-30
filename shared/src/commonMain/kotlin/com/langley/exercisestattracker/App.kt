package com.langley.exercisestattracker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.langley.exercisestattracker.core.presentation.ExerciseStatTrackerTheme
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.exerciseLibrary.data.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryEvent
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryScreen
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryState
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    appModule: AppModule
){
    ExerciseStatTrackerTheme(
        isDarkTheme,
        isDynamicColor = false
    ){
        val exerciseLibraryViewModel = getViewModel(
            key = "exerciseLibraryScreen",
            factory = viewModelFactory {
                ExerciseLibraryViewModel(appModule.exerciseDefinitionDataSource)
            }
        )

        val state by exerciseLibraryViewModel.state.collectAsState(ExerciseLibraryState())

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }

        // Initialize dummy data for exercise library.

//        val exerciseDummyData = ExerciseDefinitionDummyData()
//        val exerciseDefinitionList = exerciseDummyData
//            .convertDummyDataToExerciseDef(exerciseDummyData.dummyDefinitionData)
//
//        for (exerciseDefinition in exerciseDefinitionList){
//            exerciseLibraryViewModel.onEvent(ExerciseLibraryEvent.SaveExerciseDefinition(exerciseDefinition))
//        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            ExerciseLibraryScreen(
                state = state,
                newExerciseDefinition = exerciseLibraryViewModel.newExerciseDefinition,
                onEvent = exerciseLibraryViewModel::onEvent,
                focusRequester = focusRequester,
                focusManager = focusManager,
                interactionSource = interactionSource
            )

        }
    }
}