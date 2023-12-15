package com.langley.exercisestattracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.langley.exercisestattracker.core.presentation.ExerciseStatTrackerTheme
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryEvent
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryScreen
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryState
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.datetime.Clock

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


//        for (i in 1..100){
//            val exerciseDefinition = ExerciseDefinition(
//                exerciseDefinitionId = null,
//                exerciseName = "Unique Exercise $i",
//                bodyRegion = "Dummy Text",
//                targetMuscles = "Dummy Text",
//                description = "Dummy Text",
//                isFavorite = 0,
//                dateCreated = null,
//                )
//            exerciseLibraryViewModel.onEvent(ExerciseLibraryEvent.SaveExerciseDefinition(exerciseDefinition))
//        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            ExerciseLibraryScreen(
                state = state,
                newExerciseDefinition = exerciseLibraryViewModel.newExerciseDefinition,
                onEvent = exerciseLibraryViewModel::onEvent
            )

        }
    }
}