package com.langley.exercisestattracker.core.data

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ExerciseDataMapperTest {

    private lateinit var definitions: List<ExerciseDefinition>

    private lateinit var  testCSV0: String
    private lateinit var  testCSV0Result: List<ExerciseDefinition>

    private lateinit var  testCSV1: String
    private lateinit var  testCSV1Result: List<ExerciseDefinition>

    private lateinit var  testCSV2: String
    private lateinit var  testCSV2Result: List<ExerciseDefinition>

    private lateinit var  testRepsCSV: String

    private lateinit var testRoutine0: ExerciseRoutine
    private lateinit var testRoutine1: ExerciseRoutine
    private lateinit var testRoutine2: ExerciseRoutine

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

        testCSV0 = "0,1,2,3"
        testCSV0Result = listOf(
            ExerciseDefinition(exerciseDefinitionId = 0),
            ExerciseDefinition(exerciseDefinitionId = 1),
            ExerciseDefinition(exerciseDefinitionId = 2),
            ExerciseDefinition(exerciseDefinitionId = 3),
        )

        testCSV1 = "0,2,4,6"
        testCSV1Result = listOf(
            ExerciseDefinition(exerciseDefinitionId = 0),
            ExerciseDefinition(exerciseDefinitionId = 2),
            ExerciseDefinition(exerciseDefinitionId = 4),
            ExerciseDefinition(exerciseDefinitionId = 6),
        )
        testCSV2 = "1,3,5,7"
        testCSV2Result = listOf(
            ExerciseDefinition(exerciseDefinitionId = 1),
            ExerciseDefinition(exerciseDefinitionId = 3),
            ExerciseDefinition(exerciseDefinitionId = 5),
            ExerciseDefinition(exerciseDefinitionId = 7),
        )

        testRepsCSV = "5,5,5,5"

        testRoutine0 = ExerciseRoutine(exerciseRoutineId = 0, exerciseCSV = testCSV0 )
        testRoutine1 = ExerciseRoutine(exerciseRoutineId = 1, exerciseCSV = testCSV1 )
        testRoutine2 = ExerciseRoutine(exerciseRoutineId = 2, exerciseCSV = testCSV2 )
    }

    @Test
    fun exerciseRoutine_getExercisesFromCSV_correctExercisesAddedToList() {
        var exerciseList = testRoutine0.getExercisesFromCSV(definitions)

        assertEquals(
            expected = testCSV0Result,
            actual = exerciseList,
            message = "Expected $testCSV0Result, got $exerciseList"
        )

        exerciseList = testRoutine1.getExercisesFromCSV(definitions)

        assertEquals(
            expected = testCSV1Result,
            actual = exerciseList,
            message = "Expected $testCSV1Result, got $exerciseList"
        )

        exerciseList = testRoutine2.getExercisesFromCSV(definitions)

        assertEquals(
            expected = testCSV2Result,
            actual = exerciseList,
            message = "Expected $testCSV2Result, got $exerciseList"
        )

    }

}