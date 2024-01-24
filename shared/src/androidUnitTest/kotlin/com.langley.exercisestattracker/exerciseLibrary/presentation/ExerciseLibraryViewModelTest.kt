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
import kotlinx.coroutines.test.advanceTimeBy
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
import kotlin.random.Random
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ExerciseLibraryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ExerciseLibraryViewModel
    private lateinit var testExerciseDefinition: ExerciseDefinition

    private fun setupViewModel(
        initialState: ExerciseLibraryState,
        newExerciseDefinition: ExerciseDefinition? = null
    ): ExerciseLibraryViewModel {

        viewModel = viewModelFactory {

            ExerciseLibraryViewModel(

                TestExerciseDefDataSource(
                    ExerciseDefinitionDummyData().toListOfExerciseDefinitionsWithIndex()
                ),
                initialState,
                newExerciseDefinition
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
            isFavorite = 0,
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
        setupViewModel(
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

        setupViewModel(
            ExerciseLibraryState(
                isExerciseDetailsSheetOpen = true,
                selectedExerciseDefinition = selectedDef
            )
        )

        viewModel.onEvent(ExerciseLibraryEvent.EditExerciseDefinition(selectedDef))

        val state = viewModel.state.first()

        assertTrue(state.isEditExerciseDefSheetOpen,
            "EditExerciseDefSheetOpen is false after EditExerciseDefinition event."
        )

        assertEquals(state.selectedExerciseDefinition, selectedDef,
            "Selected Def in state: ${state.selectedExerciseDefinition} " +
                    "does not equal selected def: $selectedDef"
        )
    }

    @Test
    fun onEvent_closeEditExerciseDefinition_stateProperlyUpdated() = runTest {
        val selectedDef = viewModel.state.first().exerciseDefinitions[0]

        setupViewModel(
            initialState = ExerciseLibraryState(
                isExerciseDetailsSheetOpen = true,
                isEditExerciseDefSheetOpen = true,
                selectedExerciseDefinition = selectedDef
            ),
            newExerciseDefinition = selectedDef
        )

        var state = viewModel.state.first()

        assertTrue(state.isEditExerciseDefSheetOpen,
            "isEditExerciseDefSheetOpen failed to be initialized as true."
        )

        assertEquals(viewModel.newExerciseDefinition, selectedDef,
            "newExerciseDef in viewModel: ${viewModel.newExerciseDefinition} " +
                    "does not equal $selectedDef"
        )

        viewModel.onEvent(ExerciseLibraryEvent.CloseEditExerciseDefView)

        state = viewModel.state.first()

        assertFalse(state.isEditExerciseDefSheetOpen,
            "isEditExerciseDefSheetOpen still true after CloseEdit' event"
        )

        assertNull(state.exerciseNameError, "name error not null.")
        assertNull(state.exerciseBodyRegionError, "body region error not null.")
        assertNull(state.exerciseTargetMusclesError, "target muscle error not null.")
        assertNull(viewModel.newExerciseDefinition, "newExerciseDef not null.")
    }

    @Test
    fun onEvent_OnBodyRegionChanged_newExerciseProperlyUpdated() = runTest {
        val testString = "Test Body Region."

        setupViewModel(
            ExerciseLibraryState(),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(ExerciseLibraryEvent.OnBodyRegionChanged(testString))
        val newExerciseDef = viewModel.newExerciseDefinition

        assertEquals(testString, newExerciseDef?.bodyRegion)

    }

    @Test
    fun onEvent_ExerciseDescriptionChanged_newExerciseProperlyUpdated() = runTest {
        val testString = "Test Exercise Description."

        setupViewModel(
            ExerciseLibraryState(),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(ExerciseLibraryEvent.OnExerciseDescriptionChanged(testString))

        val newExerciseDef = viewModel.newExerciseDefinition

        assertEquals(testString, newExerciseDef?.description)
    }

    @Test
    fun onEvent_OnExerciseNameChanged_newExerciseProperlyUpdated() = runTest {
        val testString = "Test Exercise Name."

        setupViewModel(
            ExerciseLibraryState(),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(ExerciseLibraryEvent.OnExerciseNameChanged(testString))

        val newExerciseDef = viewModel.newExerciseDefinition

        assertEquals(testString, newExerciseDef?.exerciseName)
    }

    @Test
    fun onEvent_OnTargetMusclesChanged_newExerciseProperlyUpdated() = runTest {
        val testString = "Test Exercise Target Muscles."

        setupViewModel(
            ExerciseLibraryState(),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(ExerciseLibraryEvent.OnTargetMusclesChanged(testString))

        val newExerciseDef = viewModel.newExerciseDefinition

        assertEquals(testString, newExerciseDef?.targetMuscles)
    }
    @Test
    fun onEvent_SaveOrUpdateExerciseDefWithCorrectInput_stateProperlyUpdated() = runTest {
        setupViewModel(
            ExerciseLibraryState(),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(ExerciseLibraryEvent.SaveOrUpdateExerciseDef)

        val state = viewModel.state.first()

        var savedExerciseDefFoundInState = false

        for (def in state.exerciseDefinitions){

            if (def.exerciseName == testExerciseDefinition.exerciseName){
                savedExerciseDefFoundInState = true
            }

        }

        assertTrue(savedExerciseDefFoundInState,
            "testExercise not found in state after SaveOrUpdate event."
        )
    }

    @Test
    fun onEvent_SaveOrUpdateExerciseDefWithIncorrectInput_stateProperlyUpdated() = runTest {
        testExerciseDefinition = testExerciseDefinition.copy(
            exerciseName = "",
            bodyRegion = "",
            targetMuscles = "",
        )

        setupViewModel(
            ExerciseLibraryState(),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(ExerciseLibraryEvent.SaveOrUpdateExerciseDef)

        val state = viewModel.state.first()

        assertNotNull(state.exerciseNameError,
            "Name error is null in state."
        )
        assertNotNull(state.exerciseBodyRegionError,
            "Body Region error is null in state."
        )
        assertNotNull(state.exerciseTargetMusclesError,
            "Target Muscles error is null in state."
        )
    }

    @Test
    fun onEvent_SaveOrUpdateExerciseDefWithDuplicateDef_stateProperlyUpdated() = runTest {


        testExerciseDefinition = testExerciseDefinition.copy(
            exerciseDefinitionId = 0
        )

        setupViewModel(
            ExerciseLibraryState(),
            newExerciseDefinition = testExerciseDefinition
        )

        var state = viewModel.state.first()
        val initialDefinitionListSize = state.exerciseDefinitions.size

        viewModel.onEvent(ExerciseLibraryEvent.SaveOrUpdateExerciseDef)

        state = viewModel.state.first()

        val finalDefinitionListSize = state.exerciseDefinitions.size
        val updatedDefinition = state.exerciseDefinitions[finalDefinitionListSize - 1]

        assertEquals(
            initialDefinitionListSize,
            finalDefinitionListSize,
            "List size changed after updating event."
            )

        assertEquals(
            testExerciseDefinition.exerciseName,
            updatedDefinition.exerciseName,
            "Last element in definitions list doesn't match updated definition."
        )

    }

    @Test
    fun onEvent_AddNewExerciseDefClicked_stateProperlyUpdated() = runTest {
        viewModel.onEvent(ExerciseLibraryEvent.AddNewExerciseDefClicked)

        val state = viewModel.state.first()

        assertTrue(
            state.isAddExerciseDefSheetOpen,
            "isAddExerciseDefSheetOpen is false, should be true."
        )

        assertNotNull(
            viewModel.newExerciseDefinition,
            "newExerciseDefinition is null, should be ExerciseDefinition."
            )
    }
    @Test
    fun onEvent_CloseAddExerciseDefClicked_stateProperlyUpdated() = runTest {

        setupViewModel(
            initialState = ExerciseLibraryState(
                isAddExerciseDefSheetOpen = true,
                exerciseNameError = "Test",
                exerciseBodyRegionError = "Test",
                exerciseTargetMusclesError = "Test"
            ),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(ExerciseLibraryEvent.CloseAddExerciseDefClicked)

        val state = viewModel.state.first()

        assertFalse(
            state.isAddExerciseDefSheetOpen,
            "isAddExerciseDefSheetOpen is true, should be false."
        )
        assertNull(
            state.exerciseNameError,
            "Name error is not null in state."
        )
        assertNull(
            state.exerciseBodyRegionError,
            "Body Region error is not null in state."
        )
        assertNull(
            state.exerciseTargetMusclesError,
            "Target Muscles error is not null in state."
        )

    }
    @Test
    fun onEvent_OnSearchStringChanged_stateProperlyUpdated() = runTest {

    }
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

