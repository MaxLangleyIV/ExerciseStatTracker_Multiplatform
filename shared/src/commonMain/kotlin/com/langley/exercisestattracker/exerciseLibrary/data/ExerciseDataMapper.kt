package com.langley.exercisestattracker.exerciseLibrary.data


fun database.ExerciseDefinition.toExerciseDefinition():
        com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition {

    return com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition(
        exerciseDefinitionId,
        exerciseName,
        bodyRegion,
        targetMuscles,
        description,
        isFavorite,
        dateCreated
    )
}

fun database.ExerciseRoutine.toExerciseRoutine():
        com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseRoutine {

    return com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseRoutine(
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
        com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseSchedule {

    return com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseSchedule(
        exerciseScheduleId,
        exerciseScheduleName,
        exerciseRoutineCSV,
        isFavorite,
        dateCreated
    )
}

fun database.ExerciseRecord.toExerciseRecord():
        com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseRecord {

    return com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseRecord(
        exerciseRecordId,
        dateCompleted,
        exerciseName,
        weightUsed,
        repsCompleted.toInt(),
        rpe.toInt(),
        description,
        notes,
        userId
    )

}