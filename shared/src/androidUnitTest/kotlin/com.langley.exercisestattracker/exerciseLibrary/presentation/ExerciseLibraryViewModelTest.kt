package com.langley.exercisestattracker.exerciseLibrary.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import com.langley.exercisestattracker.exerciseLibrary.data.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.exerciseLibrary.data.TestExerciseDefDataSource
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.test.assertNotNull

class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
class ExerciseLibraryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ExerciseLibraryViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() = runTest{
        viewModel = viewModelFactory { ExerciseLibraryViewModel(TestExerciseDefDataSource()) }.createViewModel()
        advanceUntilIdle()

        val exerciseDummyData = ExerciseDefinitionDummyData()
        val exerciseDefinitionList = exerciseDummyData
            .convertDummyDataToExerciseDef(exerciseDummyData.dummyDefinitionData)

        for (exerciseDefinition in exerciseDefinitionList){
            viewModel.onEvent(ExerciseLibraryEvent.SaveExerciseDefinition(exerciseDefinition))
            advanceUntilIdle()
        }

    }

    @Test
    fun test() = runTest{

        assertNotNull(viewModel.state.first().exerciseDefinitions[0])
    }

    @Test
    fun viewModelOnEvent_exerciseDefClicked_correctDefSelectedInState() = runTest{
        var state = viewModel.state.first()
        val selectedDef = state.exerciseDefinitions[0]
        viewModel.onEvent(ExerciseLibraryEvent.ExerciseDefinitionSelected(selectedDef))
        state = viewModel.state.value

        assertEquals(selectedDef, state.selectedExerciseDefinition)

    }
}
