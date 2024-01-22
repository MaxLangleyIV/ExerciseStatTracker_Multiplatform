package com.langley.exercisestattracker.exerciseLibrary.presentation

import com.langley.exercisestattracker.exerciseLibrary.data.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.exerciseLibrary.data.TestExerciseDefDataSource
import com.langley.exercisestattracker.exerciseLibrary.data.toListOfExerciseDefinitionsWithIndex
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
import kotlin.random.Random
import kotlin.test.assertFalse

class ExerciseLibraryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ExerciseLibraryViewModel
    private lateinit var testExerciseDefinition: ExerciseDefinition

    private fun setupViewModel(initialState: ExerciseLibraryState): ExerciseLibraryViewModel {
        viewModel = viewModelFactory {

            ExerciseLibraryViewModel(

                TestExerciseDefDataSource(
                    ExerciseDefinitionDummyData().toListOfExerciseDefinitionsWithIndex()
                ),
                initialState
            )
        }.createViewModel()

        return viewModel
    }

    @BeforeTest
    fun setup() = runTest{

        setupViewModel(ExerciseLibraryState())

        testExerciseDefinition = ExerciseDefinition(
            exerciseDefinitionId = null,
            exerciseName = "Test",
            bodyRegion = "test",
            targetMuscles = "test",
            description = "Test",
            isFavorite = null,
            dateCreated = null
        )
    }

    @Test
    fun exerciseLibraryViewModel_retrieveDefinitionsList_exercisesFoundInState() = runTest {
        val exerciseDefinitionList =
            ExerciseDefinitionDummyData().toListOfExerciseDefinitionsWithIndex()

        val state = viewModel.state.first()

        assertTrue(
            state.exerciseDefinitions.isNotEmpty(),
            "Definitions list is empty."
        )

        for (i in 0..<state.exerciseDefinitions.size) {

            assertEquals(exerciseDefinitionList[i].exerciseName,
                state.exerciseDefinitions[i].exerciseName,
                "${exerciseDefinitionList[i].exerciseName} not equal to " +
                        "definition in state: ${state.exerciseDefinitions[i].exerciseName}"
            )
        }
    }

    @Test
    fun onEvent_saveExerciseDefinition_savedExerciseFoundInState() = runTest {
        testExerciseDefinition = testExerciseDefinition.copy(
            exerciseName = "Testing SaveExerciseDefinition event."
        )

        viewModel.onEvent(ExerciseLibraryEvent.SaveExerciseDefinition(testExerciseDefinition))

        val state = viewModel.state.first()
        var savedExerciseFoundInState = false

        for (def in state.exerciseDefinitions){

            if (def.exerciseName == testExerciseDefinition.exerciseName){
                savedExerciseFoundInState = true
            }
        }
        assertTrue(savedExerciseFoundInState,
            "Unable to find definition with matching name.")
    }

    @Test
    fun onEvent_exerciseDefSelectedCalled_selectedDefReflectedInState() = runTest{
        var state = viewModel.state.first()

        val randomNum = Random.nextInt(0,(state.exerciseDefinitions.size - 1))
        val selectedDef = state.exerciseDefinitions[randomNum]

        viewModel.onEvent(ExerciseLibraryEvent.ExerciseDefinitionSelected(selectedDef))

        state = viewModel.state.first()

        assertFalse(state.isSearchDropdownOpen,
            "Search dropdown failed to be set false after selection.")

        assertEquals(selectedDef, state.selectedExerciseDefinition,
            "selectedDef: $selectedDef does not equal def in state: " +
                    "${state.selectedExerciseDefinition}" +
                    "Definition selected with index: $randomNum"
        )

        assertTrue(state.isExerciseDetailsSheetOpen,
            "isExerciseDetailsSheetOpen failed to be set true after selection.")
    }

    @Test
    fun onEvent_closeExerciseDetailsViewCalled_ExerciseDetailsSheetOpenIsFalse() = runTest {
        //Select a definition to open details view.
        viewModel = setupViewModel(
            ExerciseLibraryState(
                isExerciseDetailsSheetOpen = true
            )
        )

        var state = viewModel.state.first()

        assertTrue(state.isExerciseDetailsSheetOpen,
            "ViewModel failed to initialize correctly, " +
                    "isExerciseDetailsSheetOpen should be true but was false."
        )

        viewModel.onEvent(ExerciseLibraryEvent.CloseExerciseDetailsView)

        state = viewModel.state.first()

        assertFalse(state.isExerciseDetailsSheetOpen,
            "isExerciseDetailsSheetOpen failed to be set false " +
                    "after CloseExerciseDetailsView."
        )
    }

    @Test
    fun onEvent_EditExerciseDefinition_stateProperlyUpdated() = runTest {
        val selectedDef = viewModel.state.first().exerciseDefinitions[0]

        viewModel = setupViewModel(
            ExerciseLibraryState(
                isExerciseDetailsSheetOpen = true,
                selectedExerciseDefinition = selectedDef
            )
        )

        viewModel.onEvent(ExerciseLibraryEvent.EditExerciseDefinition(selectedDef))

        val state = viewModel.state.first()

        assertTrue(state.isEditExerciseDefSheetOpen,
            "EditExerciseDefSheetOpen is false after EditExerciseDefinition event.")

        assertEquals(state.selectedExerciseDefinition, selectedDef,
            "Selected Def in state: ${state.selectedExerciseDefinition} " +
                    "does not equal selected def: $selectedDef")
    }

    @Test
    fun onEvent_closeEditExerciseDefinition_stateProperlyUpdated() = runTest {

    }

    @Test
    fun onEvent_OnBodyRegionChanged_newExerciseProperlyUpdated() = runTest {

    }

    @Test
    fun onEvent_ExerciseDescriptionChanged_newExerciseProperlyUpdated() = runTest {

    }

    @Test
    fun onEvent_OnExerciseNameChanged_newExerciseProperlyUpdated() = runTest {}
    @Test
    fun onEvent_OnTargetMusclesChanged_newExerciseProperlyUpdated() = runTest {}
    @Test
    fun onEvent_SaveOrUpdateExerciseDef_stateProperlyUpdated() = runTest {}
    @Test
    fun onEvent_AddNewExerciseDefClicked_stateProperlyUpdated() = runTest {}
    @Test
    fun onEvent_CloseAddExerciseDefClicked_stateProperlyUpdated() = runTest {}
    @Test
    fun onEvent_OnSearchStringChanged_stateProperlyUpdated() = runTest {}
    @Test
    fun onEvent_ToggleIsDropdownOpen_stateProperlyUpdated() = runTest {}
    @Test
    fun onEvent_DeleteExerciseDefinition_stateProperlyUpdated() = runTest {}

}

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

