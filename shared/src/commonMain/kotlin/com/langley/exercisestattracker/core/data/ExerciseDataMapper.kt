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
        description = description,
        exerciseRoutineCSV = exerciseRoutineCSV,
        isFavorite = isFavorite.toInt() == 1,
        dateCreated = dateCreated
    )
}

fun database.ExerciseRecord.toExerciseRecord():
        ExerciseRecord {

    return ExerciseRecord(
        recordId = recordId,
        exerciseDefId = exerciseDefId,
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
        exerciseDefId = exerciseDefinitionId ?: -1,
        exerciseName = exerciseName,
        isCardio = isCardio,
        isCalisthenic = isCalisthenic,
    )
}

fun ExerciseRoutine.getExercisesFromCSV(
    allExercisesAvailable: List<ExerciseDefinition>
): List<ExerciseDefinition> {

    val mutableList = mutableListOf<ExerciseDefinition>()

    val csvAsList = this.exerciseCSV.split(",")

    for (exerciseID in csvAsList){

        val id: Int? =
            try {
                exerciseID.toInt()
            } catch (nfe: NumberFormatException){
                println(nfe)
                null
            }


        if (id != null){

            for (def in allExercisesAvailable){

                val defId = def.exerciseDefinitionId?.toInt() ?: -1


                if (defId == id){
                    mutableList.add(def)
                }
            }
        }

    }

    return mutableList

}

fun ExerciseRoutine.getRecordsFromCSV(
    exerciseLibrary: List<ExerciseDefinition>
): List<ExerciseRecord> {

    val mutableList = mutableListOf<ExerciseRecord>()

    val exerciseCSVAsList = this.exerciseCSV.split(",")
    val repsCSVAsList = this.repsCSV.split(",")

    for ((index,exerciseID) in exerciseCSVAsList.withIndex()){

        val id: Int? =
            try {
                exerciseID.toInt()
            } catch (nfe: NumberFormatException){
                println(nfe)
                null
            }


        if (id != null){

            for (def in exerciseLibrary){

                val defId = def.exerciseDefinitionId?.toInt() ?: -1

                if (defId == id){
                    mutableList.add(
                        def.toBlankRecord().copy(
                            repsCompleted = repsCSVAsList[index].toInt(),
                            completed = false
                        )
                    )
                }
            }
        }

    }

    return mutableList

}

fun ExerciseSchedule.getRoutinesFromCSV(
    allRoutinesAvailable: List<ExerciseRoutine>
): List<ExerciseRoutine> {

    val mutableList = mutableListOf<ExerciseRoutine>()

    val csvAsList = this.exerciseRoutineCSV.split(",")

    for (routineId in csvAsList){

        val id: Int? =
            try {
                routineId.toInt()
            } catch (nfe: NumberFormatException){
                null
            }


        if (id != null){

            for (routine in allRoutinesAvailable){
                val defId = routine.exerciseRoutineId?.toInt() ?: -1

                if (defId == id){
                    mutableList.add(routine)
                }
            }
        }

    }

    return mutableList

}
















