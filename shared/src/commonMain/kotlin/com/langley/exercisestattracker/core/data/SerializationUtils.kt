package com.langley.exercisestattracker.core.data

import com.langley.exercisestattracker.features.workout.WorkoutState
import kotlinx.serialization.json.Json

fun getWorkoutStateFromString(string: String): WorkoutState {
    return if (string.isBlank()){
        println("RETURNING AS BLANK")
        WorkoutState()
    } else {
        val workout = Json.decodeFromString<WorkoutState>(string)
        println("RETURNING AS: $workout")
        workout
    }
}