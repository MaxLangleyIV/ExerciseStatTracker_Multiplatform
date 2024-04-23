package com.langley.exercisestattracker.features.library.routines

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.ExerciseRoutineDummyData
import com.langley.exercisestattracker.core.data.dummyData.getListOfDummyExerciseRecords
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.features.library.MainDispatcherRule
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoutineBuilderViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RoutineBuilderViewModel
    private lateinit var state: RoutineBuilderState

    private val dummyExerciseData = ExerciseDefinitionDummyData()

    private val testDataSource = TestExerciseAppDataSource(
        dummyDefinitions = dummyExerciseData.definitionList,
        dummyRoutines = ExerciseRoutineDummyData(dummyExerciseData.definitionList).getRoutines(),
        dummyRecords = dummyExerciseData.getListOfDummyExerciseRecords()
    )

    private lateinit var testRecord0: ExerciseRecord
    private lateinit var testRecord1: ExerciseRecord
    private lateinit var testRecord2: ExerciseRecord
    private lateinit var testRecord3: ExerciseRecord
    private lateinit var testDef0: ExerciseDefinition
    private lateinit var testDef1: ExerciseDefinition
    private lateinit var testDef2: ExerciseDefinition
    private lateinit var testRoutine0: ExerciseRoutine


    private fun setupViewModel( initialState: RoutineBuilderState){

        viewModel = viewModelFactory {

            RoutineBuilderViewModel(

                dataSource = testDataSource,
                initialState = initialState,

                )
        }.createViewModel()

    }

    @Before
    fun setup() = runTest {
        testRecord0 = ExerciseRecord(
            exerciseRecordId = 0,
            exerciseName = "Test0",
            completed = false
        )
        testRecord1 = ExerciseRecord(
            exerciseRecordId = 1,
            exerciseName = "Test1",
            completed = false
        )
        testRecord2 = ExerciseRecord(
            exerciseRecordId = 2,
            exerciseName = "Test2",
            completed = false
        )
        testRecord3 = ExerciseRecord(
            exerciseRecordId = 3,
            exerciseName = "Test3",
            completed = false
        )

        testDef0 = ExerciseDefinition( exerciseName = "Test0" )
        testDef1 = ExerciseDefinition( exerciseName = "Test1" )
        testDef2 = ExerciseDefinition( exerciseName = "Test2" )

        testRoutine0 = ExerciseRoutine(
            routineName = "Test",
            exerciseCSV = "0,0,0,1,1,1,2,2,2",
            repsCSV = "5,5,5,5,5,5,5,5,5"
        )

        setupViewModel(RoutineBuilderState())
        state = viewModel.state.first()
    }

    @Test
    fun onEvent_AddToListOfExercises_exercisesFoundInState() = runTest {
        assertFalse(
            actual = state.exerciseList.contains(testDef0),
            message = "exerciseList should not already contain testDef0"
        )

        viewModel.onEvent(RoutineBuilderEvent.AddToListOfExercises(listOf(testDef0)))

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseList.contains(testDef0),
            message = "testDef0 not found in exerciseList after adding"
        )
    }

    @Test
    fun onEvent_RemoveExercise_exercisesInStateDoesNotContainRemovedItem() = runTest {
        setupViewModel(
            RoutineBuilderState(
                exerciseList = listOf(testDef0,testDef1,testDef2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseList.contains(testDef0),
            message = "exerciseList should contain testDef0"
        )

        viewModel.onEvent(RoutineBuilderEvent.RemoveExercise(0))

        state = viewModel.state.first()

        assertFalse(
            actual = state.exerciseList.contains(testDef0),
            message = "exerciseList should not contain testDef0"
        )
    }

    @Test
    fun onEvent_OpenSelector_isSelectorOpenTrue() = runTest {
        assertFalse(
            actual = state.isSelectorOpen,
            message = "isSelectorOpen is true before event."
        )

        viewModel.onEvent(RoutineBuilderEvent.OpenSelector)

        state = viewModel.state.first()

        assertTrue(
            actual = state.isSelectorOpen,
            message = "isSelectorOpen is false after OpenSelector event."
        )
    }

    @Test
    fun onEvent_CloseSelector_isSelectorOpenFalse() = runTest {
        setupViewModel(
            RoutineBuilderState(
                isSelectorOpen = true
            )
        )
        state = viewModel.state.first()

        assertTrue(
            actual = state.isSelectorOpen
        )

        viewModel.onEvent(RoutineBuilderEvent.CloseSelector)

        state = viewModel.state.first()

        assertFalse(state.isSelectorOpen)
    }

    @Test
    fun onEvent_OnSearchStringChanged_searchStringUpdatedInState() = runTest {
        viewModel.onEvent(RoutineBuilderEvent.OnSearchStringChanged("Test"))

        val state = viewModel.state.first()

        assertEquals(
            state.searchString,
            "Test",
            "searchString does not equal 'Test'"
        )
    }

    @Test
    fun onEvent_InsertOrReplaceRoutine_routineFoundInDB() = runTest {

        viewModel.onEvent(RoutineBuilderEvent.InsertOrReplaceRoutine(testRoutine0))

        state = viewModel.state.first()

        var routineFound = false

        for (routine in testDataSource.getRoutines().first()){
            if (routine.routineName == testRoutine0.routineName){ routineFound = true }
        }

        assertTrue(routineFound)

    }

    @Test
    fun onEvent_DeleteExerciseDefinition_stateProperlyUpdated() = runTest {

        val routineToDelete = testDataSource.getRoutines().first()[0]


        viewModel.onEvent(RoutineBuilderEvent.DeleteRoutine(routineToDelete))


        assertFalse(
            testDataSource.getRoutines().first().contains(routineToDelete),
            "${routineToDelete.routineName} " +
                    "found in definitions after deletion event."
        )

    }

    @Test
    fun onEvent_AddRecord_recordFoundInState() = runTest {

        viewModel.onEvent(RoutineBuilderEvent.AddRecords(listOf(testRecord0)))

        state = viewModel.state.first()

        assertTrue(state.recordList.contains(testRecord0))

    }

    @Test
    fun onEvent_UpdateRepsFromString_recordsListProperlyUpdated() = runTest {

        setupViewModel(
            RoutineBuilderState(
                recordList = listOf(testRecord0, testRecord1, testRecord2)
            )
        )

        state = viewModel.state.first()

        viewModel.onEvent(RoutineBuilderEvent.UpdateRepsFromString(index = 0, string = "5"))

        state = viewModel.state.first()

        assertEquals(
            expected = 5,
            actual = state.recordList[0].repsCompleted,
            message = "recordsList[0].repsCompleted should equal 5"
        )

        viewModel.onEvent(RoutineBuilderEvent.UpdateRepsFromString(index = 0, string = ""))

        state = viewModel.state.first()

        assertEquals(
            expected = 0,
            actual = state.recordList[0].repsCompleted,
            message = "recordsList[0].repsCompleted should equal 0 when input is an empty string"
        )
    }

    @Test
    fun onEvent_RemoveFromListOfRecordsAtFront_recordsListUpdatedCorrectly() = runTest {

        setupViewModel(
            RoutineBuilderState(
                recordList = listOf(testRecord0,testRecord1,testRecord2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordList.contains(testRecord0),
            message = "recordsList should contain testRecord0"
        )

        viewModel.onEvent(RoutineBuilderEvent.RemoveRecord(0))

        state = viewModel.state.first()

        assertFalse(
            actual = state.recordList.contains(testRecord0),
            message = "recordsList should not contain testRecord0"
        )

    }

    @Test
    fun onEvent_RemoveFromListOfRecordsInMiddle_recordsListUpdatedCorrectly() = runTest {

        setupViewModel(
            RoutineBuilderState(
                recordList = listOf(testRecord0,testRecord1,testRecord2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordList.contains(testRecord1),
            message = "recordsList should contain testRecord1"
        )

        viewModel.onEvent(RoutineBuilderEvent.RemoveRecord(1))

        state = viewModel.state.first()

        assertFalse(
            actual = state.recordList.contains(testRecord1),
            message = "recordsList should not contain testRecord1"
        )
    }

    @Test
    fun onEvent_RemoveFromListOfRecordsAtEnd_recordsListUpdatedCorrectly() = runTest {

        setupViewModel(
            RoutineBuilderState(
                recordList = listOf(testRecord0,testRecord1,testRecord2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordList.contains(testRecord2),
            message = "recordsList should contain testRecord2"
        )

        viewModel.onEvent(RoutineBuilderEvent.RemoveRecord(2))

        state = viewModel.state.first()

        assertFalse(
            actual = state.recordList.contains(testRecord2),
            message = "recordsList should not contain testRecord2"
        )
    }


}