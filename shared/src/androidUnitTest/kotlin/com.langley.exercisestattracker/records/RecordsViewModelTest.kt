package com.langley.exercisestattracker.records

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.getListOfDummyExerciseRecords
import com.langley.exercisestattracker.core.data.dummyData.toListOfExerciseDefinitionsWithIndex
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.library.LibraryState
import com.langley.exercisestattracker.library.MainDispatcherRule
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RecordsViewModelTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RecordsViewModel
    private lateinit var state: RecordsState

    private fun setupViewModel( initialState: RecordsState ){

        viewModel = viewModelFactory {

            RecordsViewModel(

                TestExerciseAppDataSource(
                    dummyRecords = ExerciseDefinitionDummyData().getListOfDummyExerciseRecords()
                ),
                initialState,

            )
        }.createViewModel()

    }

    @Before
    fun setup() = runTest{

        setupViewModel(RecordsState())
        state = viewModel.state.first()

    }

    // Main Records View Events
    @Test
    fun onEvent_recordSelected_stateUpdatedCorrectly() = runTest {

        val selectedRecord = state.exerciseRecords[0]

        println("Record to select: ${selectedRecord.exerciseName}")
        println("SelectedRecord before event: ${state.selectedRecord}")

        assertNotEquals(
            illegal = selectedRecord,
            actual = state.selectedRecord,
            message = "Selected record already in state before event execution."
        )

        assertFalse(
            actual = state.isRecordDetailsSheetOpen,
            message = "'DetailsSheetOpen is true before event."
        )

        viewModel.onEvent(RecordsEvent.RecordSelected(selectedRecord))
        state = viewModel.state.first()

        assertEquals(
            expected = selectedRecord,
            actual = state.selectedRecord,
            message = "Selected record not correctly reflected in state."
        )

        assertTrue(
            actual = state.isRecordDetailsSheetOpen,
            message = "'DetailsSheetOpen is false after event."
        )
    }

    @Test
    fun onEvent_searchStringChanged_reflectedInStateCorrectly() = runTest {
        val testString = "Test"

        assertNotEquals(
            illegal = testString,
            actual = state.searchString,
            message = "Test string and searchString equal before event."
        )

        viewModel.onEvent(RecordsEvent.OnSearchStringChanged(testString))
        state = viewModel.state.first()

        assertEquals(
            expected = testString,
            actual = state.searchString,
            message = "Search string doesn't match test string after event."
        )
    }

    @Test
    fun onEvent_SetCurrentFilterType_reflectedInStateCorrectly() = runTest {
        val testFilter = RecordsFilterType.Barbell()

        assertNotEquals(
            illegal = testFilter,
            actual = state.searchFilterType,
            message = "Test filter and searchFilter equal before event."
        )

        viewModel.onEvent(RecordsEvent.SetCurrentFilterType(testFilter))
        state = viewModel.state.first()

        assertEquals(
            expected = testFilter,
            actual = state.searchFilterType,
            message = "Search filter doesn't match test filter after event."
        )

    }

    @Test
    fun onEvent_ClearFilterType_filterNullInState() = runTest {
        val testFilter = RecordsFilterType.Barbell()

        viewModel.onEvent(RecordsEvent.SetCurrentFilterType(testFilter))
        state = viewModel.state.first()

        assertEquals(
            expected = testFilter,
            actual = state.searchFilterType,
            message = "Search filter doesn't match test filter after initial event."
        )

        viewModel.onEvent(RecordsEvent.ClearFilterType)
        state = viewModel.state.first()

        assertNull(
            actual = state.searchFilterType,
            message = "Filter type not null after event."
        )
    }

    // Details View Events
    @Test
    fun onEvent_CloseDetailsView_DetailsSheetOpenIsFalse() = runTest {
        val selectedRecord = state.exerciseRecords[0]

        viewModel.onEvent(RecordsEvent.RecordSelected(selectedRecord))
        state = viewModel.state.first()

        assertEquals(
            expected = selectedRecord,
            actual = state.selectedRecord,
            message = "Selected record not initialized correctly."
        )

        assertTrue(
            actual = state.isRecordDetailsSheetOpen,
            message = "'DetailsSheetOpen should be true initially."
        )

        viewModel.onEvent(RecordsEvent.CloseDetailsView)
        state = viewModel.state.first()

        assertNull(
            actual = state.selectedRecord,
            message = "Selected record should be null after closing event."
        )

        assertFalse(
            actual = state.isRecordDetailsSheetOpen,
            message = "DetailsSheetOpen still true after closing event."
        )
    }

    @Test
    fun onEvent_SaveRecord_recordSavedToDatasource() = runTest {
        val newRecord = state.exerciseRecords[0].copy(
            exerciseRecordId = null,
            dateCompleted = Clock.System.now().toEpochMilliseconds(),
            exerciseName = "Test"
        )

        viewModel.onEvent(RecordsEvent.SaveRecord(newRecord))

        state = viewModel.state.first()

        assertEquals(
            expected = newRecord.exerciseName,
            actual = state.exerciseRecords.last().exerciseName
        )
    }
}









