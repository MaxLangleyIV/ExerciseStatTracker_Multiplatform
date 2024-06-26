package com.langley.exercisestattracker.core.data.dummyData

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import kotlinx.datetime.Clock
import kotlin.random.Random

class ExerciseRoutineDummyData(
    exerciseDefinitions: List<ExerciseDefinition>,
    amountOfRoutinesToGenerate: Int = 5
) {

    private val exerciseRoutines = mutableListOf<ExerciseRoutine>()
    private val exerciseSchedules = mutableListOf<ExerciseSchedule>()
    fun getRoutines(): List<ExerciseRoutine>{
        return exerciseRoutines.toList()
    }

    fun getSchedules(amount: Int): List<ExerciseSchedule> {
        for (i in 1..amount){
            val schedule = ExerciseSchedule(
                exerciseScheduleName = "Test Schedule $i",
            )

            exerciseSchedules.add(schedule)
        }

        return exerciseSchedules
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
    private var currentRepsString = ""

    private lateinit var currentRoutine: ExerciseRoutine

    init {

        for (i in 1..amountOfRoutinesToGenerate){

            currentExerciseString = ""

            for (j in 1..amountOfExercises){

                randomIndex = Random.nextInt(exerciseDefinitionsSize)

                for (k in 1..3){
                    if (currentExerciseString.isBlank()){
                        currentExerciseString =
                            exerciseDefinitions[randomIndex].exerciseDefinitionId.toString()
                        currentRepsString = "5"
                    }
                    else {
                        currentExerciseString =
                            currentExerciseString + "," +
                                    exerciseDefinitions[randomIndex].exerciseDefinitionId.toString()
                        currentRepsString = "$currentRepsString,5"
                    }

                }

            }

            currentRoutine = ExerciseRoutine(
                exerciseRoutineId = (i - 1).toLong(),
                routineName = "Test Routine $i",
                exerciseCSV = currentExerciseString,
                repsCSV = currentRepsString,
                description = "A test exercise routine.",
                isFavorite = false,
                dateCreated = Clock.System.now().toEpochMilliseconds()
            )

            exerciseRoutines.add(currentRoutine)
        }
    }
}
