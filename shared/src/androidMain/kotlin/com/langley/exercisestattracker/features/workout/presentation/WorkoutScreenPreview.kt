package com.langley.exercisestattracker.features.workout.presentation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import com.langley.exercisestattracker.navigation.Screen

@Preview
@Composable
fun WorkoutScreenPreview() {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val navController = remember { ExerciseAppNavController(Screen.Workout) }
    val currentScreen by navController.currentScreen.collectAsState()
//    WorkoutScreen(
//        dataSource = ,
//        focusRequester = focusRequester,
//        focusManager = focusManager,
//        interactionSource = interactionSource,
//        navController = navController
//    )
}