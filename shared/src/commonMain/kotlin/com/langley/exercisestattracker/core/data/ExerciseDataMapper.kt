package com.langley.exercisestattracker.core.data

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import com.langley.exercisestattracker.core.domain.ExerciseRecord


fun database.ExerciseDefinition.toExerciseDefinition():
        ExerciseDefinition {

    return ExerciseDefinition(
        exerciseDefinitionId = exerciseDefinitionId,
        exerciseName = exerciseName,
        bodyRegion = bodyRegion,
        targetMuscles = targetMuscles,
        description = description,
        isCalisthenic = isCalisthenic,
        isTimed = isTimed,
        duration = duration,
        isFavorite = isFavorite,
        dateCreated = dateCreated
    )
}

fun database.ExerciseRoutine.toExerciseRoutine():
        ExerciseRoutine {

    return ExerciseRoutine(
        exerciseRoutineId,
        routineName,
        exercisesCSV,
        repsCSV,
        description,
        isFavorite,
        dateCreated
    )
}

fun database.ExerciseSchedule.toExerciseSchedule():
        ExerciseSchedule {

    return ExerciseSchedule(
        exerciseScheduleId,
        exerciseScheduleName,
        exerciseRoutineCSV,
        isFavorite,
        dateCreated
    )
}

fun database.ExerciseRecord.toExerciseRecord():
        ExerciseRecord {

    return ExerciseRecord(
        exerciseRecordId,
        dateCompleted,
        exerciseName,
        isCalisthenic,
        isTimed,
        duration,
        weightUsed,
        repsCompleted.toInt(),
        rpe.toInt(),
        description,
        notes,
        userId,
        currentBodyWeight
    )

}