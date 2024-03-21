package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.getListOfDummyExerciseRecords
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType
import com.langley.exercisestattracker.features.library.MainDispatcherRule
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WorkoutViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var state: WorkoutState
    private lateinit var testRecord0: ExerciseRecord
    private lateinit var testRecord1: ExerciseRecord
    private lateinit var testRecord2: ExerciseRecord
    private lateinit var testRecord3: ExerciseRecord
    private lateinit var testDef0: ExerciseDefinition
    private lateinit var testDef1: ExerciseDefinition
    private lateinit var testDef2: ExerciseDefinition

    private val testDataSource = TestExerciseAppDataSource(
        dummyRecords = ExerciseDefinitionDummyData().getListOfDummyExerciseRecords()
    )

    private fun setupViewModel( initialState: WorkoutState){

        viewModel = viewModelFactory {

            WorkoutViewModel(

                testDataSource,
                initialState

                )
        }.createViewModel()

    }

    @Before
    fun setup() = runTest{

        testRecord0 = ExerciseRecord(
            exerciseRecordId = 0,
            exerciseName = "Test"
        )
        testRecord1 = ExerciseRecord(
            exerciseRecordId = 1,
            exerciseName = "Test"
        )
        testRecord2 = ExerciseRecord(
            exerciseRecordId = 2,
            exerciseName = "Test"
        )
        testRecord3 = ExerciseRecord(
            exerciseRecordId = 3,
            exerciseName = "Test"
        )

        testDef0 = ExerciseDefinition( exerciseName = "Test0" )
        testDef1 = ExerciseDefinition( exerciseName = "Test1" )
        testDef2 = ExerciseDefinition( exerciseName = "Test2" )

        setupViewModel(WorkoutState(
            completedExercises = listOf(testRecord0, testRecord1, testRecord2)
        ))

        state = viewModel.state.first()

    }

    @Test
    fun onEvent_openExerciseSelector_stateUpdated() = runTest {

        assertFalse(
            actual = state.exerciseSelectorVisible,
            message = "ExerciseSelector is visible before event."
        )

        viewModel.onEvent(WorkoutEvent.OpenExerciseSelector)

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseSelectorVisible,
            message = "ExerciseSelectorVisible is false after OpenSelector event."
        )

    }

    @Test
    fun onEvent_CloseExerciseSelector() = runTest {
        setupViewModel(
            WorkoutState(
                exerciseSelectorVisible = true
            )
        )

        viewModel.onEvent(WorkoutEvent.CloseExerciseSelector)

        state = viewModel.state.first()

        assertFalse(state.exerciseSelectorVisible)
    }

    @Test
    fun onEvent_AddRecordToMap_stateUpdatedCorrectly() = runTest {

        assertTrue(
            actual = state.exerciseMap.isEmpty(),
            message = "ExerciseMap should be empty initially."
        )

        viewModel.onEvent(WorkoutEvent.AddRecordToMap(testRecord0))

        state = viewModel.state.first()

        assertNotNull(
            actual = state.exerciseMap[testRecord0.exerciseName],
            message = "Record name not found as key in map."
        )

        assertEquals(
            expected = testRecord0,
            actual = state.exerciseMap[testRecord0.exerciseName]!![0],
            message = "First record in Map[testRecord name] not equal to testRecord."
        )

        viewModel.onEvent(WorkoutEvent.AddRecordToMap(testRecord0.copy(exerciseRecordId = 1)))

        state = viewModel.state.first()

        assertEquals(
            expected = testRecord0.copy(exerciseRecordId = 1),
            actual = state.exerciseMap[testRecord0.exerciseName]!![1],
            message = "Record with id 1 not equal to record at Map[testRecord.exerciseName][1]"
        )




    }

    @Test
    fun onEvent_RemoveRecordFromMap_stateUpdatedCorrectly() = runTest {

        viewModel.onEvent(WorkoutEvent.AddRecordToMap(testRecord0))

        viewModel.onEvent(WorkoutEvent.AddRecordToMap(testRecord0.copy(exerciseRecordId = 1)))

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseMap.isNotEmpty(),
            message = "ExerciseMap should not be empty initially."
        )

        viewModel.onEvent(WorkoutEvent.RemoveRecordFromMap(testRecord0.exerciseName,0))

        state = viewModel.state.first()

        assertNotEquals(
            illegal = testRecord0,
            actual = state.exerciseMap[testRecord0.exerciseName]!![0],
            message = "Map[testRecord.exerciseName][0] should no longer equal testRecord"
        )
    }

    @Test
    fun onEvent_MarkCompleted_completedExercisesListUpdatedCorrectly() = runTest {

        assertFalse(
            actual = state.completedExercises.contains(testRecord3),
            message = "CompletedExercises should not already contain testRecord"
        )

        viewModel.onEvent(WorkoutEvent.MarkCompleted(testRecord3))

        state = viewModel.state.first()

        assertTrue(
            actual = state.completedExercises.contains(testRecord3),
            message = "CompletedExercises does not contain testRecord."
        )

    }

    @Test
    fun onEvent_RemoveFromCompletedInMiddle_completedExercisesListUpdatedCorrectly() = runTest {

        state = viewModel.state.first()

        assertTrue(
            actual = state.completedExercises.contains(testRecord1),
            message = "CompletedExercises should contain testRecord1"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromCompleted(testRecord1))

        state = viewModel.state.first()

        assertFalse(
            actual = state.completedExercises.contains(testRecord1),
            message = "CompletedExercises should not contain testRecord1"
        )
    }

    @Test
    fun onEvent_RemoveFromCompletedAtFront_completedExercisesListUpdatedCorrectly() = runTest {

        state = viewModel.state.first()

        assertTrue(
            actual = state.completedExercises.contains(testRecord0),
            message = "CompletedExercises should contain testRecord0"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromCompleted(testRecord0))

        state = viewModel.state.first()

        assertFalse(
            actual = state.completedExercises.contains(testRecord0),
            message = "CompletedExercises should not contain testRecord0"
        )
    }

    @Test
    fun onEvent_RemoveFromCompletedAtEnd_completedExercisesListUpdatedCorrectly() = runTest {

        state = viewModel.state.first()

        assertTrue(
            actual = state.completedExercises.contains(testRecord2),
            message = "CompletedExercises should contain testRecord2"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromCompleted(testRecord2))

        state = viewModel.state.first()

        assertFalse(
            actual = state.completedExercises.contains(testRecord2),
            message = "CompletedExercises should not contain testRecord2"
        )
    }

    @Test
    fun onEvent_saveWorkout() = runTest {

        viewModel.onEvent(WorkoutEvent.SaveWorkout)

        state = viewModel.state.first()

        for (record in state.completedExercises){

            assertTrue(
                actual = testDataSource.getRecords().first().contains(record),
                message =
                record.exerciseName + "${record.exerciseRecordId} not found in data source."
            )

        }
    }

    @Test
    fun onEvent_DefinitionSelected_defToggledInSelectedDefList() = runTest {

        val testDef = ExerciseDefinition( exerciseName = "Test" )

        viewModel.onEvent(WorkoutEvent.DefinitionSelected(testDef))

        state = viewModel.state.first()

        assertTrue(
            actual = state.selectedExercises.contains(testDef),
            message = "testDef not found in list after being selected"
        )

        viewModel.onEvent(WorkoutEvent.DefinitionSelected(testDef))

        state = viewModel.state.first()

        assertFalse(
            actual = state.selectedExercises.contains(testDef),
            message = "testDef found in list after being selected a second time"
        )




    }

    @Test
    fun onEvent_AddToListOfExercises_addSingleDef_exerciseFoundInList() = runTest {

        assertFalse(
            actual = state.exerciseList.contains(testDef0),
            message = "exerciseList should not already contain testDef0"
        )

        viewModel.onEvent(WorkoutEvent.AddToListOfExercises(listOf(testDef0)))

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseList.contains(testDef0),
            message = "testDef0 not found in exerciseList after adding"
        )

    }

    @Test
    fun onEvent_AddToListOfExercises_addMultipleDef_exercisesFoundInList() = runTest {

        val listToAdd = listOf(testDef0,testDef1,testDef2)

        for (def in listToAdd){
            assertFalse(
                actual = state.exerciseList.contains(def),
                message = "exerciseList should not already contain ${def.exerciseName}"
            )
        }

        viewModel.onEvent(
            WorkoutEvent.AddToListOfExercises(listToAdd)
        )

        state = viewModel.state.first()

        for (def in listToAdd){
            assertTrue(
                actual = state.exerciseList.contains(def),
                message = "${def.exerciseName} not found in exerciseList after adding"
            )
        }

    }

    @Test
    fun onEvent_RemoveFromListOfExercisesAtFront_exerciseListUpdatedCorrectly() = runTest {

        setupViewModel(
            WorkoutState(
                exerciseList = listOf(testDef0,testDef1,testDef2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseList.contains(testDef0),
            message = "exerciseList should contain testDef0"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromListOfExercises(testDef0))

        state = viewModel.state.first()

        assertFalse(
            actual = state.exerciseList.contains(testDef0),
            message = "exerciseList should not contain testDef0"
        )

    }

    @Test
    fun onEvent_RemoveFromListOfExercisesInMiddle_exerciseListUpdatedCorrectly() = runTest {

        setupViewModel(
            WorkoutState(
                exerciseList = listOf(testDef0,testDef1,testDef2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseList.contains(testDef1),
            message = "exerciseList should contain testDef1"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromListOfExercises(testDef1))

        state = viewModel.state.first()

        assertFalse(
            actual = state.exerciseList.contains(testDef1),
            message = "exerciseList should not contain testDef1"
        )

    }

    @Test
    fun onEvent_RemoveFromListOfExercisesAtEnd_exerciseListUpdatedCorrectly() = runTest {

        setupViewModel(
            WorkoutState(
                exerciseList = listOf(testDef0,testDef1,testDef2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseList.contains(testDef2),
            message = "exerciseList should contain testDef2"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromListOfExercises(testDef2))

        state = viewModel.state.first()

        assertFalse(
            actual = state.exerciseList.contains(testDef2),
            message = "exerciseList should not contain testDef2"
        )

    }

    @Test
    fun onEvent_AddToListOfRecords_addSingleRecord_recordFoundInList() = runTest {

        assertFalse(
            actual = state.recordsList.contains(testRecord0),
            message = "recordsList should not already contain testRecord0"
        )

        viewModel.onEvent(
            WorkoutEvent.AddToListOfRecords(listOf(testRecord0))
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordsList.contains(testRecord0),
            message = "testRecord0 not found in recordsList after adding"
        )

    }

    @Test
    fun onEvent_AddToListOfRecords_addMultipleRecords_recordsFoundInList() = runTest {

        val listToAdd = listOf(testRecord0,testRecord1,testRecord2)

        for (record in listToAdd){
            assertFalse(
                actual = state.recordsList.contains(record),
                message = "exerciseList should not already contain ${record.exerciseName}"
            )
        }

        viewModel.onEvent(
            WorkoutEvent.AddToListOfRecords(listToAdd)
        )

        state = viewModel.state.first()

        for (record in listToAdd){
            assertTrue(
                actual = state.recordsList.contains(record),
                message = "${record.exerciseName} not found in exerciseList after adding"
            )
        }

    }

    @Test
    fun onEvent_RemoveFromListOfRecordsAtFront_recordsListUpdatedCorrectly() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(testRecord0,testRecord1,testRecord2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordsList.contains(testRecord0),
            message = "recordsList should contain testRecord0"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromListOfRecords(0))

        state = viewModel.state.first()

        assertFalse(
            actual = state.recordsList.contains(testRecord0),
            message = "recordsList should not contain testRecord0"
        )

    }

    @Test
    fun onEvent_RemoveFromListOfRecordsInMiddle_recordsListUpdatedCorrectly() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(testRecord0,testRecord1,testRecord2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordsList.contains(testRecord1),
            message = "recordsList should contain testRecord1"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromListOfRecords(1))

        state = viewModel.state.first()

        assertFalse(
            actual = state.recordsList.contains(testRecord1),
            message = "recordsList should not contain testRecord1"
        )
    }

    @Test
    fun onEvent_RemoveFromListOfRecordsAtEnd_recordsListUpdatedCorrectly() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(testRecord0,testRecord1,testRecord2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordsList.contains(testRecord2),
            message = "recordsList should contain testRecord2"
        )

        viewModel.onEvent(WorkoutEvent.RemoveFromListOfRecords(2))

        state = viewModel.state.first()

        assertFalse(
            actual = state.recordsList.contains(testRecord2),
            message = "recordsList should not contain testRecord2"
        )
    }

    @Test
    fun onEvent_clearFilterType_filterNullInState() = runTest {
        val testFilterType = ExerciseLibraryFilterType.Barbell()

        setupViewModel(
            WorkoutState(
                searchFilter = testFilterType
            )
        )
        var state = viewModel.state.first()
        var currentFilterType = state.searchFilter


        assertNotNull(currentFilterType, "Filter should be $testFilterType.")

        viewModel.onEvent(WorkoutEvent.ClearFilterType)

        state = viewModel.state.first()
        currentFilterType = state.searchFilter

        assertNull(currentFilterType, null)

    }

    @Test
    fun onEvent_setCurrentFilterType_typeReflectedInState() = runTest {
        var state = viewModel.state.first()
        var currentFilterType = state.searchFilter
        val testFilterType = ExerciseLibraryFilterType.Barbell()

        assertNull(currentFilterType, "Filter should be null initially.")

        viewModel.onEvent(WorkoutEvent.SetCurrentFilterType(testFilterType))

        state = viewModel.state.first()
        currentFilterType = state.searchFilter

        assertEquals(
            testFilterType,
            currentFilterType,
            "Filter type not updated properly."
        )

    }

    @Test
    fun onEvent_OnSearchStringChanged_stateProperlyUpdated() = runTest {
        viewModel.onEvent(WorkoutEvent.OnSearchStringChanged("Test"))

        val state = viewModel.state.first()

        assertEquals(
            state.searchString,
            "Test",
            "SearchString does not equal 'Test'"
        )

    }


    @Test
    fun onEvent__() = runTest {  }

}