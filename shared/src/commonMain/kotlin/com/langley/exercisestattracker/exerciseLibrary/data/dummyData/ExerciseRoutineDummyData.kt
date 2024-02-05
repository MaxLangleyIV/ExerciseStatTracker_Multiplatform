package com.langley.exercisestattracker.exerciseLibrary.data.dummyData

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseRoutine
import kotlinx.datetime.Clock
import kotlin.random.Random

class ExerciseRoutineDummyData(
    exerciseDefinitions: List<ExerciseDefinition>,
    amountOfRoutinesToGenerate: Int = 5
) {

    private val exerciseRoutines = mutableListOf<ExerciseRoutine>()
    fun getRoutines(): List<ExerciseRoutine>{
        return exerciseRoutines.toList()
    }


    private val exerciseDefinitionsSize = exerciseDefinitions.size
    private var amountOfExercises = 5
    private var randomIndex = 0

    private val repSchemesCSV = listOf(
        "5, 5, 5, 5, 5",
        "5, 4, 3, 2, 1",
        "10, 10, 10, 10, 10"
    )
    private var currentExerciseString = ""

    private lateinit var currentRoutine: ExerciseRoutine

    init {

        for (i in 1..amountOfRoutinesToGenerate){

            currentExerciseString = ""

            for (j in 1..amountOfExercises){

                randomIndex = Random.nextInt(exerciseDefinitionsSize)

                if (currentExerciseString !== ""){
                    currentExerciseString =
                        currentExerciseString + ", " + exerciseDefinitions[randomIndex].exerciseName
                }
                else {
                    currentExerciseString = exerciseDefinitions[randomIndex].exerciseName
                }

            }

            currentRoutine = ExerciseRoutine(
                exerciseRoutineId = (i - 1).toLong(),
                routineName = "Test Routine $i",
                exerciseCSV = currentExerciseString,
                repsCSV = repSchemesCSV[Random.nextInt(repSchemesCSV.size)],
                description = "A test exercise routine.",
                isFavorite = 0,
                dateCreated = Clock.System.now().toEpochMilliseconds()
            )

            exerciseRoutines.add(currentRoutine)
        }
    }
}
