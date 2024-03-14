package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.getListOfDummyExerciseRecords
import com.langley.exercisestattracker.core.domain.ExerciseRecord
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
    fun onEvent_AddRecord_mapUpdatedCorrectly() = runTest {

        assertTrue(
            actual = state.exerciseMap.isEmpty(),
            message = "ExerciseMap should be empty initially."
        )

        viewModel.onEvent(WorkoutEvent.AddRecord(testRecord0))

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

        viewModel.onEvent(WorkoutEvent.AddRecord(testRecord0.copy(exerciseRecordId = 1)))

        state = viewModel.state.first()

        assertEquals(
            expected = testRecord0.copy(exerciseRecordId = 1),
            actual = state.exerciseMap[testRecord0.exerciseName]!![1],
            message = "Record with id 1 not equal to record at Map[testRecord.exerciseName][1]"
        )


    }

    @Test
    fun onEvent_RemoveRecord() = runTest {

        viewModel.onEvent(WorkoutEvent.AddRecord(testRecord0))

        viewModel.onEvent(WorkoutEvent.AddRecord(testRecord0.copy(exerciseRecordId = 1)))

        state = viewModel.state.first()

        assertTrue(
            actual = state.exerciseMap.isNotEmpty(),
            message = "ExerciseMap should not be empty initially."
        )

        viewModel.onEvent(WorkoutEvent.RemoveRecord(testRecord0.exerciseName,0))

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

        viewModel.onEvent(WorkoutEvent.RemoveFromCompleted(1))

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

        viewModel.onEvent(WorkoutEvent.RemoveFromCompleted(0))

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

        viewModel.onEvent(WorkoutEvent.RemoveFromCompleted(2))

        state = viewModel.state.first()

        assertFalse(
            actual = state.completedExercises.contains(testRecord2),
            message = "CompletedExercises should not contain testRecord2"
        )
    }

    @Test
    fun onEvent_saveWorkout() = runTest {






    }

    @Test
    fun onEvent__() = runTest {  }

}