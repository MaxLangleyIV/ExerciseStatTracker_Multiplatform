package com.langley.exercisestattracker.core

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseDefinitionValidator
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
            false,
            false,
            false,
            false,
            false,
            0,
            false,
            0,
            false,
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
            false,
            false,
            false,
            false,
            false,
            0,
            false,
            0,
            false,
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
            false,
            false,
            false,
            false,
            false,
            0,
            false,
            0,
            false,
            null
        )
        assertNotNull(
            ExerciseDefinitionValidator
                .validateExerciseDefinition(exerciseDefinition).targetMusclesErrorString
        )
    }
}