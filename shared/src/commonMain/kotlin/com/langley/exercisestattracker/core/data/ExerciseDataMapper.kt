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
        isWeighted = isWeighted,
        isCardio = isCardio,
        isCalisthenic = isCalisthenic,
        isTimed = isTimed,
        hasDistance = 0,
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
        weightUsed.toFloat(),
        isCardio == 1L,
        isCalisthenic == 1L,
        duration.toFloat(),
        distance.toFloat(),
        repsCompleted.toInt(),
        rpe.toInt(),
        notes,
        userId,
        currentBodyWeight.toInt()
    )

}