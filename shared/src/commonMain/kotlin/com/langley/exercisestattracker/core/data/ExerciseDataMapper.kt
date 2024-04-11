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
        exerciseRoutineId = exerciseRoutineId,
        routineName = routineName,
        exerciseCSV = exercisesCSV,
        repsCSV = repsCSV,
        description = description,
        isFavorite = isFavorite.toInt() == 1,
        dateCreated = dateCreated
    )
}

fun database.ExerciseSchedule.toExerciseSchedule():
        ExerciseSchedule {

    return ExerciseSchedule(
        exerciseScheduleId = exerciseScheduleId,
        exerciseScheduleName = exerciseScheduleName,
        exerciseRoutineCSV = exerciseRoutineCSV,
        isFavorite = isFavorite,
        dateCreated = dateCreated
    )
}

fun database.ExerciseRecord.toExerciseRecord():
        ExerciseRecord {

    return ExerciseRecord(
        exerciseRecordId =exerciseRecordId,
        dateCompleted = dateCompleted,
        exerciseName = exerciseName,
        weightUsed = weightUsed.toFloat(),
        weightUnit = weightUnit,
        isCardio = isCardio == 1L,
        isCalisthenic = isCalisthenic == 1L,
        duration = duration,
        distance = distance.toFloat(),
        distanceUnit = distanceUnit,
        repsCompleted = repsCompleted.toInt(),
        rir = rir.toInt(),
        notes = notes,
        userId = userId,
        currentBodyWeight = currentBodyWeight.toInt()
    )

}

fun ExerciseDefinition.toBlankRecord(): ExerciseRecord {
    return ExerciseRecord(
        exerciseName = exerciseName,
        isCardio = isCardio,
        isCalisthenic = isCalisthenic,
    )
}
