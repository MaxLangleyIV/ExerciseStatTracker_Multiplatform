package com.langley.exercisestattracker.core.data

import com.langley.exercisestattracker.features.workout.WorkoutState
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

fun getWorkoutStateFromString(string: String): WorkoutState {
    return if (string.isBlank()){
        WorkoutState()
    } else {
        try {
            Json.decodeFromString<WorkoutState>(string)
        }
        catch (error: SerializationException, ){
            println(error)

            return WorkoutState()
        }
        catch (error: IllegalArgumentException){
            println(error)

            return WorkoutState()
        }
    }
}