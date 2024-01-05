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

//        val tempExerciseList = listOf(
//            "Barbell Squat", "Deadlift", "Bench Press", "Overhead Press",
//            "Bent-over Rows", "Pull-ups", "Chin-ups", "Push-ups", "Dips",
//            "Lunges", "Leg Press", "Leg Curl", "Leg Extension", "Calf Raises",
//            "Plank", "Russian Twists", "Sit-ups", "Crunches", "Hanging Leg Raise",
//            "Flutter Kicks", "Mountain Climbers", "Burpees", "Box Jumps",
//            "Kettlebell Swings", "Thrusters", "Clean and Jerk", "Snatch",
//            "Turkish Get-up", "Romanian Deadlift", "Front Squat", "Box Squat",
//            "Incline Bench Press", "Decline Bench Press", "Lat Pulldown",
//            "Face Pulls", "Tricep Dips", "Skull Crushers", "Barbell Curl",
//            "Hammer Curl", "Dumbbell Flyes", "Side Lateral Raises", "Reverse Flyes",
//            "Seated Shoulder Press", "Leg Raises", "Hyperextensions", "Good Mornings",
//            "Side Plank", "Woodchoppers", "Battle Ropes", "Jump Rope", "Rowing Machine",
//            "Cycling", "Swimming", "Running", "Hiking", "Elliptical Trainer",
//            "Sled Push", "Sled Pull", "Tire Flips", "Bodyweight Squats",
//            "Wall Sit", "Farmers Walk", "Sprints", "High Knees", "Jumping Jacks",
//            "Plank Jacks", "Cycling", "Stair Climbing", "Bicycles", "Reverse Crunches",
//            "Sissy Squat", "Leg Raises on Roman Chair", "Plyometric Push-ups",
//            "One-Arm Dumbbell Row", "Machine Shoulder Press", "Machine Leg Press",
//            "Smith Machine Squat", "Smith Machine Bench Press", "Barbell Hip Thrust",
//            "Barbell Step-ups", "Assisted Pull-ups", "Cable Crunch",
//            "Face Pulls with Rope Attachment", "Landmine Press", "Reverse Lunges",
//            "Zercher Squat", "Dumbbell Pullover", "Battle Ropes", "Rowing Machine"
//        )
//
//        for (exercise in tempExerciseList){
//            val exerciseDefinition = ExerciseDefinition(
//                exerciseDefinitionId = null,
//                exerciseName = exercise,
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