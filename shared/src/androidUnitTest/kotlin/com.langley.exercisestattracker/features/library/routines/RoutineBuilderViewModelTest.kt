package com.langley.exercisestattracker.features.library.routines

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.ExerciseRoutineDummyData
import com.langley.exercisestattracker.core.data.dummyData.getListOfDummyExerciseRecords
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import com.langley.exercisestattracker.features.workout.WorkoutViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RoutineBuilderViewModelTest {

    private lateinit var viewModel: RoutineBuilderViewModel
    private lateinit var state: RoutineBuilderState

    private val dummyExerciseData = ExerciseDefinitionDummyData()

    private val testDataSource = TestExerciseAppDataSource(
        dummyDefinitions = dummyExerciseData.definitionList,
        dummyRoutines = ExerciseRoutineDummyData(dummyExerciseData.definitionList).getRoutines(),
        dummyRecords = dummyExerciseData.getListOfDummyExerciseRecords()
    )


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
        setupViewModel(RoutineBuilderState())
        state = viewModel.state.first()
    }

    @Test
    fun onEvent_AddToListOfExercises_exercisesFoundInState() = runTest {  }

    @Test
    fun onEvent_RemoveExercise_exercisesInStateDoesNotContainRemovedItem() = runTest {  }

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
    fun onEvent_OnSearchStringChanged_searchStringUpdatedInState() = runTest {  }

    @Test
    fun onEvent_InsertOrReplaceRoutine_routineFoundInDB() = runTest {  }
}