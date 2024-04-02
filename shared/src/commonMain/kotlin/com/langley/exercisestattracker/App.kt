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
import com.langley.exercisestattracker.features.home.HomeScreen
import com.langley.exercisestattracker.features.home.HomeState
import com.langley.exercisestattracker.features.library.presentation.LibraryScreen
import com.langley.exercisestattracker.features.records.RecordsScreen
import com.langley.exercisestattracker.features.workout.presentation.WorkoutScreen
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import com.langley.exercisestattracker.navigation.Screen

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
                        dataSource = appModule.exerciseAppDataSource,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                }

                Screen.Records -> {

                    RecordsScreen(
                        dataSource = appModule.exerciseAppDataSource,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )

                }

                Screen.Workout -> {
                    WorkoutScreen(
                        dataSource = appModule.exerciseAppDataSource,
                        focusRequester = focusRequester,
                        focusManager = focusManager,
                        interactionSource = interactionSource,
                        navController = navController
                    )
                }
            }
        }
    }
}