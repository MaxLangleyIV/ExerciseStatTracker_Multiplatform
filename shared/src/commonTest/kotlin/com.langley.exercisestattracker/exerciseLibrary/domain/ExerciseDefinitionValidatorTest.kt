package com.langley.exercisestattracker.exerciseLibrary.domain

import kotlin.test.Test
import kotlin.test.assertNotNull


class ExerciseDefinitionValidatorTest {
    @Test
    fun validateExerciseDefinition_NameIsBlank_nameErrorStringNotNull(){
        val exerciseDefinition = ExerciseDefinition (
            null,
            "",
            "a",
            "b",
            "c",
            null,
            null
        )
        assertNotNull(
            ExerciseDefinitionValidator
                .validateExerciseDefinition(exerciseDefinition).nameErrorString
        )
    }

    @Test
    fun validateExerciseDefinition_bodyRegionIsBlank_bodyRegionErrorStringNotNull(){
        val exerciseDefinition = ExerciseDefinition (
            null,
            "a",
            "",
            "b",
            "c",
            null,
            null
        )
        assertNotNull(
            ExerciseDefinitionValidator
                .validateExerciseDefinition(exerciseDefinition).bodyRegionErrorString
        )
    }

    @Test
    fun validateExerciseDefinition_targetMusclesIsBlank_targetMusclesErrorStringNotNull(){
        val exerciseDefinition = ExerciseDefinition (
            null,
            "a",
            "b",
            "",
            "c",
            null,
            null
        )
        assertNotNull(
            ExerciseDefinitionValidator
                .validateExerciseDefinition(exerciseDefinition).targetMusclesErrorString
        )
    }
}