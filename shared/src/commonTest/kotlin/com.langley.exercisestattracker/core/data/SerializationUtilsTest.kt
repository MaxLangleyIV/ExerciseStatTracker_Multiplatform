package com.langley.exercisestattracker.core.data

import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.features.workout.WorkoutState
import kotlin.test.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class SerializationUtilsTest {
    private val workoutState = WorkoutState(
        routine = ExerciseRoutine(
            exerciseRoutineId = 0,
            routineName = "TEST"
        )
    )

    private val workoutJson = Json.encodeToString(workoutState)

    @Test
    fun testGetWorkoutStateFromString_BlankString() {
        val result = getWorkoutStateFromString(" ")

        assertEquals(WorkoutState(), result)
    }

    @Test
    fun testGetWorkoutStateFromString_ValidJson() {
        val result = getWorkoutStateFromString(workoutJson)
        // Checking with the expected WorkoutState as returned by the mocked Json.decodeFromString
        assertEquals(workoutState, result)
    }

    @Test
    fun testGetWorkoutStateFromString_InvalidJson() {
        // Assuming the function throws an error or returns a default state if JSON is invalid
        val result = getWorkoutStateFromString("invalid json")

        assertEquals(WorkoutState(), result)
    }

}