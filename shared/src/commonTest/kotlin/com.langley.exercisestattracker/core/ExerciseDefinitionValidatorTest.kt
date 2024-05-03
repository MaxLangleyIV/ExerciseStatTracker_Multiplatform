package com.langley.exercisestattracker.core

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseDefinitionValidator
import kotlin.test.Test
import kotlin.test.assertNotNull


class ExerciseDefinitionValidatorTest {
    @Test
    fun validateExerciseDefinition_NameIsBlank_nameErrorStringNotNull(){
        val exerciseDefinition = ExerciseDefinition (
            exerciseDefinitionId = null,
            exerciseName = "",
            bodyRegion = "a",
            targetMuscles = "b",
            description = "c",
            isWeighted = false,
            hasReps = false,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        )
        assertNotNull(
            ExerciseDefinitionValidator
                .validateExerciseDefinition(exerciseDefinition).nameErrorString
        )
    }

    @Test
    fun validateExerciseDefinition_bodyRegionIsBlank_bodyRegionErrorStringNotNull(){
        val exerciseDefinition = ExerciseDefinition (
            exerciseDefinitionId = null,
            exerciseName = "a",
            bodyRegion = "",
            targetMuscles = "b",
            description = "c",
            isWeighted = false,
            hasReps = false,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        )
        assertNotNull(
            ExerciseDefinitionValidator
                .validateExerciseDefinition(exerciseDefinition).bodyRegionErrorString
        )
    }

    @Test
    fun validateExerciseDefinition_targetMusclesIsBlank_targetMusclesErrorStringNotNull(){
        val exerciseDefinition = ExerciseDefinition (
            exerciseDefinitionId = null,
            exerciseName = "a",
            bodyRegion = "b",
            targetMuscles = "",
            description = "c",
            isWeighted = false,
            hasReps = false,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        )
        assertNotNull(
            ExerciseDefinitionValidator
                .validateExerciseDefinition(exerciseDefinition).targetMusclesErrorString
        )
    }
}