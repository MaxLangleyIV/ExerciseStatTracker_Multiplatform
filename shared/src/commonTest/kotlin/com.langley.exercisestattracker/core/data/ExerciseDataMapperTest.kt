package com.langley.exercisestattracker.core.data

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ExerciseDataMapperTest {

    private lateinit var definitions: List<ExerciseDefinition>
    private lateinit var routines: List<ExerciseRoutine>

    private lateinit var routine0: ExerciseRoutine
    private lateinit var routine1: ExerciseRoutine
    private lateinit var routine2: ExerciseRoutine

    private lateinit var schedule0: ExerciseSchedule
    private lateinit var schedule1: ExerciseSchedule
    private lateinit var schedule2: ExerciseSchedule

    private lateinit var  testCSV0: String
    private lateinit var  testCSV1: String
    private lateinit var  testCSV2: String

    private lateinit var  exerciseCSV0Result: List<ExerciseDefinition>
    private lateinit var  exerciseCSV1Result: List<ExerciseDefinition>
    private lateinit var  exerciseCSV2Result: List<ExerciseDefinition>

    private lateinit var  routineCSV0Result: List<ExerciseRoutine>
    private lateinit var  routineCSV1Result: List<ExerciseRoutine>
    private lateinit var  routineCSV2Result: List<ExerciseRoutine>

    private lateinit var  testRepsCSV: String



    @BeforeTest
    fun setup(){
        definitions = listOf(
            ExerciseDefinition(exerciseDefinitionId = 0),
            ExerciseDefinition(exerciseDefinitionId = 1),
            ExerciseDefinition(exerciseDefinitionId = 2),
            ExerciseDefinition(exerciseDefinitionId = 3),
            ExerciseDefinition(exerciseDefinitionId = 4),
            ExerciseDefinition(exerciseDefinitionId = 5),
            ExerciseDefinition(exerciseDefinitionId = 6),
            ExerciseDefinition(exerciseDefinitionId = 7),
            ExerciseDefinition(exerciseDefinitionId = 8),
        )

        routines = listOf(
            ExerciseRoutine(exerciseRoutineId = 0),
            ExerciseRoutine(exerciseRoutineId = 1),
            ExerciseRoutine(exerciseRoutineId = 2),
            ExerciseRoutine(exerciseRoutineId = 3),
            ExerciseRoutine(exerciseRoutineId = 4),
            ExerciseRoutine(exerciseRoutineId = 5),
            ExerciseRoutine(exerciseRoutineId = 6),
            ExerciseRoutine(exerciseRoutineId = 7),
            ExerciseRoutine(exerciseRoutineId = 8),
        )

        testCSV0 = "0,1,2,3"
        testCSV1 = "0,2,4,6"
        testCSV2 = "1,3,5,7"


        exerciseCSV0Result = listOf(
            ExerciseDefinition(exerciseDefinitionId = 0),
            ExerciseDefinition(exerciseDefinitionId = 1),
            ExerciseDefinition(exerciseDefinitionId = 2),
            ExerciseDefinition(exerciseDefinitionId = 3),
        )
        exerciseCSV1Result = listOf(
            ExerciseDefinition(exerciseDefinitionId = 0),
            ExerciseDefinition(exerciseDefinitionId = 2),
            ExerciseDefinition(exerciseDefinitionId = 4),
            ExerciseDefinition(exerciseDefinitionId = 6),
        )
        exerciseCSV2Result = listOf(
            ExerciseDefinition(exerciseDefinitionId = 1),
            ExerciseDefinition(exerciseDefinitionId = 3),
            ExerciseDefinition(exerciseDefinitionId = 5),
            ExerciseDefinition(exerciseDefinitionId = 7),
        )

        routineCSV0Result = listOf(
            ExerciseRoutine(exerciseRoutineId = 0),
            ExerciseRoutine(exerciseRoutineId = 1),
            ExerciseRoutine(exerciseRoutineId = 2),
            ExerciseRoutine(exerciseRoutineId = 3),
        )
        routineCSV1Result = listOf(
            ExerciseRoutine(exerciseRoutineId = 0),
            ExerciseRoutine(exerciseRoutineId = 2),
            ExerciseRoutine(exerciseRoutineId = 4),
            ExerciseRoutine(exerciseRoutineId = 6),
        )
        routineCSV2Result = listOf(
            ExerciseRoutine(exerciseRoutineId = 1),
            ExerciseRoutine(exerciseRoutineId = 3),
            ExerciseRoutine(exerciseRoutineId = 5),
            ExerciseRoutine(exerciseRoutineId = 7),
        )



        testRepsCSV = "5,5,5,5"

        routine0 = ExerciseRoutine(exerciseRoutineId = 0, exerciseCSV = testCSV0 )
        routine1 = ExerciseRoutine(exerciseRoutineId = 1, exerciseCSV = testCSV1 )
        routine2 = ExerciseRoutine(exerciseRoutineId = 2, exerciseCSV = testCSV2 )

        schedule0 = ExerciseSchedule(exerciseScheduleId = 0, exerciseRoutineCSV = testCSV0)
        schedule1 = ExerciseSchedule(exerciseScheduleId = 0, exerciseRoutineCSV = testCSV1)
        schedule2 = ExerciseSchedule(exerciseScheduleId = 0, exerciseRoutineCSV = testCSV2)
    }

    @Test
    fun exerciseRoutine_getExercisesFromCSV_correctExercisesAddedToList() {
        var exerciseList = routine0.getExercisesFromCSV(definitions)

        assertEquals(
            expected = exerciseCSV0Result,
            actual = exerciseList,
            message = "Expected $exerciseCSV0Result, got $exerciseList"
        )

        exerciseList = routine1.getExercisesFromCSV(definitions)

        assertEquals(
            expected = exerciseCSV1Result,
            actual = exerciseList,
            message = "Expected $exerciseCSV1Result, got $exerciseList"
        )

        exerciseList = routine2.getExercisesFromCSV(definitions)

        assertEquals(
            expected = exerciseCSV2Result,
            actual = exerciseList,
            message = "Expected $exerciseCSV2Result, got $exerciseList"
        )

    }

    @Test
    fun exerciseSchedule_getRoutinesFromCSV_correctRoutinesAddedToList() {
        var routineList = schedule0.getRoutinesFromCSV(routines)

        assertEquals(
            expected = routineCSV0Result,
            actual = routineList,
            message = "Expected $routineCSV0Result, got $routineList"
        )

        routineList = schedule1.getRoutinesFromCSV(routines)

        assertEquals(
            expected = routineCSV1Result,
            actual = routineList,
            message = "Expected $routineCSV1Result, got $routineList"
        )

        routineList = schedule2.getRoutinesFromCSV(routines)

        assertEquals(
            expected = routineCSV2Result,
            actual = routineList,
            message = "Expected $routineCSV2Result, got $routineList"
        )

    }

}