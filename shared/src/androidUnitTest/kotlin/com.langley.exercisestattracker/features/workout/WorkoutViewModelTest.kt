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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class WorkoutViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var state: WorkoutState
    private lateinit var testRecord: ExerciseRecord

    private fun setupViewModel( initialState: WorkoutState){

        viewModel = viewModelFactory {

            WorkoutViewModel(

                TestExerciseAppDataSource(
                    dummyRecords = ExerciseDefinitionDummyData().getListOfDummyExerciseRecords()
                ),
                initialState

                )
        }.createViewModel()

    }

    @Before
    fun setup() = runTest{

        setupViewModel(WorkoutState())
        state = viewModel.state.first()
        testRecord = ExerciseRecord(
            exerciseName = "Test"
        )

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
    fun onEvent_AddRecord() = runTest {

        assertTrue(
            actual = state.exerciseMap.isEmpty(),
            message = "ExerciseMap should be empty initially."
        )

        viewModel.onEvent(WorkoutEvent.AddRecord(testRecord))

        state = viewModel.state.first()

        assertNotNull(
            actual = state.exerciseMap[testRecord.exerciseName],
            message = "Record name not found as key in map."
        )

        assertEquals(
            expected = testRecord,
            actual = state.exerciseMap[testRecord.exerciseName]!![0],
            message = "First record in Map[testRecord name] not equal to testRecord."
        )


    }
    @Test
    fun onEvent_RemoveRecord() = runTest {



    }
    @Test
    fun onEvent_MarkCompleted() = runTest {  }
    @Test
    fun onEvent_4() = runTest {  }

}