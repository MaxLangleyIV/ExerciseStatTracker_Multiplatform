package com.langley.exercisestattracker.core.domain

object ExerciseDefinitionValidator {

    fun validateExerciseDefinition(exerciseDefinition: ExerciseDefinition): ValidationResult {
        var validationResult = ValidationResult()

        if (exerciseDefinition.exerciseName.isBlank()){
            validationResult = validationResult.copy(
                nameErrorString = "Exercise name can't be empty."
            )
        }

        if (exerciseDefinition.bodyRegion.isBlank()){
            validationResult = validationResult.copy(
                bodyRegionErrorString = "Exercise body region can't be empty."
            )
        }

        if (exerciseDefinition.targetMuscles.isBlank()){
            validationResult = validationResult.copy(
                targetMusclesErrorString = "Exercise target muscles can't be empty."
            )
        }

        return validationResult
    }

    data class ValidationResult(
        val nameErrorString: String? = null,
        val bodyRegionErrorString: String? = null,
        val targetMusclesErrorString: String? = null
    )
}