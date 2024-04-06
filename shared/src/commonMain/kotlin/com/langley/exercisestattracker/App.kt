package com.langley.exercisestattracker

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.langley.exercisestattracker.core.presentation.ExerciseStatTrackerTheme
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.features.home.HomeScreen
import com.langley.exercisestattracker.features.library.LibraryState
import com.langley.exercisestattracker.features.library.LibraryViewModel
import com.langley.exercisestattracker.features.library.presentation.LibraryScreen
import com.langley.exercisestattracker.features.records.RecordsScreen
import com.langley.exercisestattracker.features.records.RecordsState
import com.langley.exercisestattracker.features.records.RecordsViewModel
import com.langley.exercisestattracker.features.workout.WorkoutState
import com.langley.exercisestattracker.features.workout.WorkoutViewModel
import com.langley.exercisestattracker.features.workout.presentation.WorkoutScreen
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

        // View Models and States
        val libraryViewModel = getViewModel(
            key = "libraryViewModel",
            factory = viewModelFactory { LibraryViewModel(appModule.exerciseAppDataSource) }
        )
        val libraryState by libraryViewModel.state.collectAsState(LibraryState())


        val recordsViewModel = getViewModel(
            key = "recordsViewModel",
            factory = viewModelFactory { RecordsViewModel(appModule.exerciseAppDataSource) }
        )
        val recordsState by recordsViewModel.state.collectAsState(RecordsState())


        val workoutViewModel = getViewModel(
            key = "workoutViewModel",
            factory = viewModelFactory { WorkoutViewModel(
                dataSource = appModule.exerciseAppDataSource,
                prefDataStore = appModule.preferencesDataStore
                )
            }
        )
        val workoutState by workoutViewModel.state.collectAsState(WorkoutState())


//    // INIT DUMMY DATA
//    val exerciseRecordList = ExerciseDefinitionDummyData().getListOfDummyExerciseRecords()
//    for (record in exerciseRecordList){
//        (recordsViewModel::onEvent)(RecordsEvent.SaveRecord(record))
//    }

//    val exerciseDefDummyData = ExerciseDefinitionDummyData()
//    val exerciseDefList = exerciseDefDummyData.definitionList
//    // Add definitions to SQLDelight db.
//    for (exerciseDefinition in exerciseDefList){
//        libraryViewModel.onEvent(LibraryEvent.SaveDefinition(exerciseDefinition))
//    }


        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                modifier = Modifier.fillMaxSize() ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Screens
                Column(
                    modifier = Modifier.weight(0.92F),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    HomeScreen(
                        visible = currentScreen == Screen.Home,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                    LibraryScreen(
                        visible = currentScreen == Screen.Library,
                        dataSource = appModule.exerciseAppDataSource,
                        libraryState = libraryState,
                        onEvent = libraryViewModel::onEvent,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                    RecordsScreen(
                        visible = currentScreen == Screen.Records,
                        recordsState = recordsState,
                        onEvent = recordsViewModel::onEvent,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                    WorkoutScreen(
                        visible = currentScreen == Screen.Workout,
                        workoutState = workoutState,
                        onEvent = workoutViewModel::onEvent,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                }


                // Bottom Nav Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.08F)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    // Home
                    IconButton(
                        onClick = {
                            navController.navigateTo(Screen.Home)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.alpha(
                                if (currentScreen == Screen.Home){
                                    1F
                                }
                                else{
                                    0.6F
                                }
                            ),
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Workout
                    IconButton(
                        onClick = {
                            navController.navigateTo(Screen.Workout)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.alpha(
                                if (currentScreen == Screen.Workout){
                                    1F
                                }
                                else{
                                    0.6F
                                }
                            ),
                            imageVector = Icons.Filled.FitnessCenter,
                            contentDescription = "Workout",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Library Button
                    IconButton(
                        onClick = {
                            navController.navigateTo(Screen.Library)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.alpha(
                                if (currentScreen == Screen.Library){
                                    1F
                                }
                                else{
                                    0.6F
                                }
                            ),
                            imageVector = Icons.Filled.MenuBook,
                            contentDescription = "Library",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }

            }




//            when (currentScreen) {
//
//                Screen.Home -> {
//
//                    HomeScreen(
//                        state = HomeState(), // This state is a placeholder
//                        focusRequester = focusRequester,
//                        focusManager = focusManager,
//                        interactionSource = interactionSource,
//                        navController = navController
//                    )
//
//                }
//
//                Screen.Library -> {
//
//                    LibraryScreen(
//                        dataSource = appModule.exerciseAppDataSource,
//                        libraryState = libraryState,
//                        onEvent = libraryViewModel::onEvent,
//                        focusRequester = focusRequester,
//                        focusManager = focusManager,
//                        interactionSource = interactionSource,
//                        navController = navController
//                    )
//
//                }
//
//                Screen.Records -> {
//
//                    RecordsScreen(
//                        recordsState = recordsState,
//                        onEvent = recordsViewModel::onEvent,
//                        focusRequester = focusRequester,
//                        focusManager = focusManager,
//                        interactionSource = interactionSource,
//                        navController = navController
//                    )
//
//                }
//
//                Screen.Workout -> {
//
//                    WorkoutScreen(
//                        workoutState = workoutState,
//                        onEvent = workoutViewModel::onEvent,
//                        focusRequester = focusRequester,
//                        focusManager = focusManager,
//                        interactionSource = interactionSource,
//                        navController = navController
//                    )
//
//                }
//            }
        }
    }
}