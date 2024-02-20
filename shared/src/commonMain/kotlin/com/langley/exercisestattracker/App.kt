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
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.ExerciseRoutineDummyData
import com.langley.exercisestattracker.core.data.dummyData.getListOfDummyExerciseRecords
import com.langley.exercisestattracker.core.presentation.ExerciseStatTrackerTheme
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.features.home.HomeScreen
import com.langley.exercisestattracker.features.home.HomeState
import com.langley.exercisestattracker.features.library.LibraryState
import com.langley.exercisestattracker.features.library.LibraryViewModel
import com.langley.exercisestattracker.features.library.presentation.LibraryScreen
import com.langley.exercisestattracker.features.records.RecordsScreen
import com.langley.exercisestattracker.features.records.RecordsState
import com.langley.exercisestattracker.features.records.RecordsViewModel
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import com.langley.exercisestattracker.navigation.Screen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App(
    isDarkTheme: Boolean,
    appModule: AppModule
){
    ExerciseStatTrackerTheme(
        isDarkTheme,
        isDynamicColor = false
    ){

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }

        val navController = remember { ExerciseAppNavController() }
        val currentScreen by navController.currentScreen.collectAsState()



        // View models and related states.
        val libraryViewModel = getViewModel(
            key = "libraryViewModel",
            factory = viewModelFactory {
                LibraryViewModel(appModule.exerciseAppDataSource)
            }
        )
        val libraryState by libraryViewModel.state.collectAsState(LibraryState())

        val recordsViewModel = getViewModel(
            key = "recordsViewModel",
            factory = viewModelFactory {
                RecordsViewModel(appModule.exerciseAppDataSource)
            }
        )
        val recordsState by recordsViewModel.state.collectAsState(RecordsState())


        // Initialize dummy data for exercise library.
        val exerciseDefDummyData = ExerciseDefinitionDummyData()
        val exerciseDefList = exerciseDefDummyData.definitionList

        val exerciseRecordList = exerciseDefDummyData.getListOfDummyExerciseRecords()

        val exerciseRoutineDummyData = ExerciseRoutineDummyData(exerciseDefList)
        val exerciseRoutineList = exerciseRoutineDummyData.getRoutines()

        println("OUTPUTTING RECORDS: $exerciseRecordList")

        println("OUTPUTTING ROUTINES: $exerciseRoutineList")

        // Add definitions to SQLDelight db.
//        for (exerciseDefinition in exerciseDefList){
//            libraryViewModel.onEvent(LibraryEvent.SaveDefinition(exerciseDefinition))
//        }
        // Add records to SQLDelight db.
//        for (exerciseRecord in exerciseRecordList){
//            recordsViewModel.onEvent(RecordsEvent.SaveRecord(exerciseRecord))
//        }


        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            when (currentScreen) {

                Screen.Home -> {

                    HomeScreen(
                        // This state is a placeholder
                        state = HomeState(),
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                }

                Screen.Library -> {

                    LibraryScreen(
                        appModule = appModule,
                        state = libraryState,
                        newExerciseDefinition = libraryViewModel.newExerciseDefinition,
                        onEvent = libraryViewModel::onEvent,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                }

                Screen.Records -> {

                    RecordsScreen(
                        state = recordsState,
                        onEvent = recordsViewModel::onEvent,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                }

                Screen.ExerciseBuilder -> {}
            }
        }
    }
}