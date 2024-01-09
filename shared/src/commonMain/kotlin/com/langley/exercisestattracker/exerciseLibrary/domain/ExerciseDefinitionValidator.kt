package com.langley.exercisestattracker.exerciseLibrary.domain

object ExerciseDefinitionValidator {

    fun validateExerciseDefinition(exerciseDefinition: ExerciseDefinition): ValidationResult{
        var validationResult = ValidationResult()

        if (exerciseDefinition.exerciseName.isBlank()){
            validationResult = validationResult.copy(
                nameErrorString = "Exercise name can't be empty."
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