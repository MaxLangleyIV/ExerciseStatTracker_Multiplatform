package com.langley.exercisestattracker.features.workout

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.getListOfDummyExerciseRecords
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType
import com.langley.exercisestattracker.features.library.MainDispatcherRule
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
        dummyDefinitions = ExerciseDefinitionDummyData().definitionList,
        dummyRecords = ExerciseDefinitionDummyData().getListOfDummyExerciseRecords()
    )

    private val dataPrefs = mockk<DataStore<Preferences>>(relaxed = true)
    private val prefs = mockk<Preferences>(relaxed = true)

    private fun setupViewModel( initialState: WorkoutState){

        viewModel = viewModelFactory {

            WorkoutViewModel(

                dataSource = testDataSource,
                prefDataStore = dataPrefs,
                initialState = initialState,

                )
        }.createViewModel()

    }

    @Before
    fun setup() = runTest{

        every { dataPrefs.data } returns flowOf(prefs)
        every { prefs[stringPreferencesKey("WORKOUT_STATE")] } returns ""

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

        setupViewModel(WorkoutState(exerciseLibrary = ExerciseDefinitionDummyData().definitionList))

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

//    @Test
//    fun onEvent_AddRecordToMap_stateUpdatedCorrectly() = runTest {
//
//        assertTrue(
//            actual = state.exerciseMap.isEmpty(),
//            message = "ExerciseMap should be empty initially."
//        )
//
//        viewModel.onEvent(WorkoutEvent.AddRecordToMap(testRecord0))
//
//        state = viewModel.state.first()
//
//        assertNotNull(
//            actual = state.exerciseMap[testRecord0.exerciseName],
//            message = "Record name not found as key in map."
//        )
//
//        assertEquals(
//            expected = testRecord0,
//            actual = state.exerciseMap[testRecord0.exerciseName]!![0],
//            message = "First record in Map[testRecord name] not equal to testRecord."
//        )
//
//        viewModel.onEvent(WorkoutEvent.AddRecordToMap(testRecord0.copy(exerciseRecordId = 1)))
//
//        state = viewModel.state.first()
//
//        assertEquals(
//            expected = testRecord0.copy(exerciseRecordId = 1),
//            actual = state.exerciseMap[testRecord0.exerciseName]!![1],
//            message = "Record with id 1 not equal to record at Map[testRecord.exerciseName][1]"
//        )
//
//
//
//
//    }
//
//    @Test
//    fun onEvent_RemoveRecordFromMap_stateUpdatedCorrectly() = runTest {
//
//        viewModel.onEvent(WorkoutEvent.AddRecordToMap(testRecord0))
//
//        viewModel.onEvent(WorkoutEvent.AddRecordToMap(testRecord0.copy(exerciseRecordId = 1)))
//
//        state = viewModel.state.first()
//
//        assertTrue(
//            actual = state.exerciseMap.isNotEmpty(),
//            message = "ExerciseMap should not be empty initially."
//        )
//
//        viewModel.onEvent(WorkoutEvent.RemoveRecordFromMap(testRecord0.exerciseName,0))
//
//        state = viewModel.state.first()
//
//        assertNotEquals(
//            illegal = testRecord0,
//            actual = state.exerciseMap[testRecord0.exerciseName]!![0],
//            message = "Map[testRecord.exerciseName][0] should no longer equal testRecord"
//        )
//    }

    @Test
    fun onEvent_MarkCompleted_completedExercisesListUpdatedCorrectly() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(testRecord0, testRecord1, testRecord2)
            )
        )

        state = viewModel.state.first()

        assertFalse(
            actual = state.recordsList[1].completed,
            message = "recordsList[1].completed should equal false"
        )

        viewModel.onEvent(WorkoutEvent.MarkCompleted(1, testRecord1))

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordsList[1].completed,
            message = "recordsList[1].completed should equal true"
        )

    }

    @Test
    fun onEvent_MarkIncomplete_completedExercisesListUpdatedCorrectly() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(testRecord0, testRecord1.copy(completed = true), testRecord2)
            )
        )

        state = viewModel.state.first()

        assertTrue(
            actual = state.recordsList[1].completed,
            message = "recordsList[1].completed should equal true"
        )

        viewModel.onEvent(WorkoutEvent.MarkIncomplete(1, testRecord1))

        state = viewModel.state.first()

        assertFalse(
            actual = state.recordsList[1].completed,
            message = "recordsList[1].completed should equal false"
        )

    }

    @Test
    fun onEvent_saveWorkout() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(
                    testRecord0.copy(completed = true),
                    testRecord1,
                    testRecord2
                )
            )
        )

        viewModel.onEvent(WorkoutEvent.SaveWorkout)

        state = viewModel.state.first()

        for (record in state.recordsList){

            if (record.completed){
                assertTrue(
                    actual = testDataSource.getRecords().first().contains(record),
                    message =
                    record.exerciseName + " ${record.exerciseRecordId} not found in data source."
                )
            }
            else {
                assertFalse(
                    actual = testDataSource.getRecords().first().contains(record),
                    message =
                    record.exerciseName +
                            " ${record.exerciseRecordId} should not be found in data source."
                )
            }

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

        viewModel.onEvent(WorkoutEvent.AddToExercisesWithDefaultSet(listOf(testDef0)))

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
            WorkoutEvent.AddToExercisesWithDefaultSet(listToAdd)
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
    fun onEvent_UpdateRecordInList_recordUpdatedProperly() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(testRecord0, testRecord1, testRecord2)
            )
        )

        val newRecord = testRecord1.copy( completed = true, repsCompleted = 5, weightUsed = 100F )

        viewModel.onEvent(WorkoutEvent.UpdateRecordInList(index = 1, newRecord))

        assertEquals(
            expected = newRecord,
            actual = viewModel.state.first().recordsList[1]
        )

    }

    @Test
    fun onEvent_SelectSet_stateUpdatedCorrectly() = runTest {

        assertNull(
            actual = state.selectedSet,
            message = "SelectedSet should be null at start"
            )

        viewModel.onEvent(WorkoutEvent.SelectSet(testRecord0))

        state = viewModel.state.first()

        assertEquals(
            expected = testRecord0,
            actual = state.selectedSet,
            message = "testRecord0 not found in state.selectedSet"
        )

    }

    @Test
    fun onEvent_ClearSelectedSet_selectedSetEqualsNull() = runTest {

        viewModel.onEvent(WorkoutEvent.SelectSet(testRecord0))

        viewModel.onEvent(WorkoutEvent.ClearSelectedSet)

        state = viewModel.state.first()

        assertNull(
            actual = state.selectedSet,
            message = "selectedSet should be null in state"
        )

    }

    @Test
    fun onEvent_UpdateRepsFromString_recordsListProperlyUpdated() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(testRecord0,testRecord1,testRecord2)
            )
        )

        state = viewModel.state.first()

        viewModel.onEvent(WorkoutEvent.UpdateRepsFromString(index = 0, value = "5"))

        state = viewModel.state.first()

        assertEquals(
            expected = 5,
            actual = state.recordsList[0].repsCompleted,
            message = "recordsList[0].repsCompleted should equal 5"
        )

        viewModel.onEvent(WorkoutEvent.UpdateRepsFromString(index = 0, value = ""))

        state = viewModel.state.first()

        assertEquals(
            expected = 0,
            actual = state.recordsList[0].repsCompleted,
            message = "recordsList[0].repsCompleted should equal 0 when input is an empty string"
        )


    }

    @Test
    fun onEvent_UpdateWeight_recordsListProperlyUpdated() = runTest {

        setupViewModel(
            WorkoutState(
                recordsList = listOf(testRecord0,testRecord1,testRecord2)
            )
        )

        state = viewModel.state.first()

        viewModel.onEvent(WorkoutEvent.UpdateWeightFromString(index = 0, value = "135"))

        state = viewModel.state.first()

        assertEquals(
            expected = 135F,
            actual = state.recordsList[0].weightUsed,
            message = "recordsList[0].weightUsed should equal 135"
        )

        viewModel.onEvent(WorkoutEvent.UpdateWeightFromString(index = 0, value = ""))

        state = viewModel.state.first()

        assertEquals(
            expected = 0F,
            actual = state.recordsList[0].weightUsed,
            message = "recordsList[0].weightUsed should equal 0 when input is an empty string"
        )

    }

    @Test
    fun onEvent_addRoutine_routineSet_exercisesAndRecordsUpdated() = runTest {

        val routine = ExerciseRoutine(
            exerciseRoutineId = 0,
            exerciseCSV = "0,0,0,1,1,1,2,2,2",
            repsCSV = "5,5,5,5,5,5,5,5,5"
            )

        viewModel.onEvent(WorkoutEvent.AddRoutine(routine))

        state = viewModel.state.first()

        assertEquals(
            expected = routine,
            actual = state.routine,
            message = "${state.routine} in state doesn't equal test routine"
        )

        println(state.exerciseList.size)

        var exercise0Count = 0
        var exercise1Count = 0
        var exercise2Count = 0

        for (record in state.recordsList){
            println("RECORD: ${record.exerciseName}")

            if (record.exerciseName == state.exerciseLibrary[0].exerciseName){
                exercise0Count++
            }
            if (record.exerciseName == state.exerciseLibrary[1].exerciseName){
                exercise1Count++
            }
            else if (record.exerciseName == state.exerciseLibrary[2].exerciseName){
                exercise2Count++
            }

        }

        assertEquals(
            expected = 3,
            actual = exercise0Count,
            message = "exercise0Count should equal 3"
        )
        assertEquals(
            expected = 3,
            actual = exercise1Count,
            message = "exercise1Count should equal 3"
        )
        assertEquals(
            expected = 3,
            actual = exercise2Count,
            message = "exercise2Count should equal 3"
        )


    }

    @Test
    fun onEvent__() = runTest {  }

}