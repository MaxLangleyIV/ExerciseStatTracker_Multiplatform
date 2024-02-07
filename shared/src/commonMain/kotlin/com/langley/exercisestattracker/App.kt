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
import com.langley.exercisestattracker.exerciseLibrary.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.exerciseLibrary.data.dummyData.ExerciseRoutineDummyData
import com.langley.exercisestattracker.exerciseLibrary.data.dummyData.getListOfDummyExerciseRecords
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
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }

        val exerciseLibraryViewModel = getViewModel(
            key = "exerciseLibraryScreen",
            factory = viewModelFactory {
                ExerciseLibraryViewModel(appModule.exerciseAppDataSource)
            }
        )

        val libraryState by exerciseLibraryViewModel.state.collectAsState(ExerciseLibraryState())

        // Initialize dummy data for exercise library.
        val exerciseDefDummyData = ExerciseDefinitionDummyData()
        val exerciseDefList = exerciseDefDummyData
            .convertDummyDataToExerciseDef(exerciseDefDummyData.dummyDefinitionData)

        val exerciseRecordList = exerciseDefDummyData.getListOfDummyExerciseRecords()

        val exerciseRoutineDummyData = ExerciseRoutineDummyData(exerciseDefList)
        val exerciseRoutineList = exerciseRoutineDummyData.getRoutines()

        println("OUTPUTTING RECORDS: $exerciseRecordList")

        println("OUTPUTTING ROUTINES: $exerciseRoutineList")

        //Add definitions to SQLDelight db.
//        for (exerciseDefinition in exerciseDefList){
//            exerciseLibraryViewModel.onEvent(ExerciseLibraryEvent.SaveExerciseDefinition(exerciseDefinition))
//        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            ExerciseLibraryScreen(
                state = libraryState,
                newExerciseDefinition = exerciseLibraryViewModel.newExerciseDefinition,
                onEvent = exerciseLibraryViewModel::onEvent,
                focusRequester = focusRequester,
                focusManager = focusManager,
                interactionSource = interactionSource
            )

        }
    }
}