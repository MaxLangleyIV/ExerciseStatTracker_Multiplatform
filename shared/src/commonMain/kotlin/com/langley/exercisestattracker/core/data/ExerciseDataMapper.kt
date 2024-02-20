package com.langley.exercisestattracker.core.data

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule

fun database.ExerciseDefinition.toExerciseDefinition():
        ExerciseDefinition {

    return ExerciseDefinition(
        exerciseDefinitionId = exerciseDefinitionId,
        exerciseName = exerciseName,
        bodyRegion = bodyRegion,
        targetMuscles = targetMuscles,
        description = description,
        isWeighted = isWeighted.toInt() == 1,
        hasReps = hasReps.toInt() == 1,
        isCardio = isCardio.toInt() == 1,
        isCalisthenic = isCalisthenic.toInt() == 1,
        isTimed = isTimed.toInt() == 1,
        defaultDuration = defaultDuration,
        hasDistance = hasDistance.toInt() == 1,
        defaultDistance = defaultDistance,
        isFavorite = isFavorite.toInt() == 1,
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