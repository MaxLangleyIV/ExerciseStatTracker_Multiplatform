package com.langley.exercisestattracker.features.library

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LibraryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LibraryViewModel
    private lateinit var testExerciseDefinition: ExerciseDefinition

    private fun setupViewModel(
        initialState: LibraryState,
        newExerciseDefinition: ExerciseDefinition = ExerciseDefinition()
    ): LibraryViewModel {

        viewModel = viewModelFactory {

            LibraryViewModel(

                TestExerciseAppDataSource(
                    ExerciseDefinitionDummyData().definitionList
                ),
                initialState,
                newExerciseDefinition
            )
        }.createViewModel()

        return viewModel
    }

    @BeforeTest
    fun setup() = runTest{

        setupViewModel(LibraryState())

        testExerciseDefinition = ExerciseDefinition(
            exerciseDefinitionId = null,
            exerciseName = "Test",
            bodyRegion = "test",
            targetMuscles = "test",
            description = "Test",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        )
    }

    @Test
    fun exerciseLibraryViewModel_retrieveDefinitionsList_exercisesFoundInState() = runTest {
        val exerciseDefinitionList =
            ExerciseDefinitionDummyData().definitionList

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
            exerciseName = "Testing SaveDefinition event."
        )

        viewModel.onEvent(LibraryEvent.SaveDefinition(testExerciseDefinition))

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

        viewModel.onEvent(LibraryEvent.DefinitionSelected(selectedDef))

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
            LibraryState(
                isExerciseDetailsSheetOpen = true
            )
        )

        var state = viewModel.state.first()

        assertTrue(state.isExerciseDetailsSheetOpen,
            "ViewModel failed to initialize correctly, " +
                    "isExerciseDetailsSheetOpen should be true but was false."
        )

        viewModel.onEvent(LibraryEvent.CloseDetailsView)

        state = viewModel.state.first()

        assertFalse(state.isExerciseDetailsSheetOpen,
            "isExerciseDetailsSheetOpen failed to be set false " +
                    "after CloseDetailsView."
        )
    }

    @Test
    fun onEvent_EditExerciseDefinition_stateProperlyUpdated() = runTest {
        val selectedDef = viewModel.state.first().exerciseDefinitions[0]

        setupViewModel(
            LibraryState(
                isAddExerciseDefSheetOpen = true,
                selectedExerciseDefinition = selectedDef
            )
        )

        viewModel.onEvent(LibraryEvent.EditDefinition(selectedDef))

        val state = viewModel.state.first()

        assertTrue(state.isAddExerciseDefSheetOpen,
            "EditExerciseDefSheetOpen is false after EditDefinition event."
        )

        assertEquals(state.selectedExerciseDefinition, selectedDef,
            "Selected Def in state: ${state.selectedExerciseDefinition} " +
                    "does not equal selected def: $selectedDef"
        )
    }



    @Test
    fun onEvent_AddNewExerciseDefClicked_stateProperlyUpdated() = runTest {
        viewModel.onEvent(LibraryEvent.AddNewDefClicked)

        val state = viewModel.state.first()

        assertTrue(
            state.isAddExerciseDefSheetOpen,
            "isAddExerciseDefSheetOpen is false, should be true."
        )

        assertNotNull(
            viewModel.definitionForBuilder,
            "newExerciseDefinition is null, should be ExerciseDefinition."
            )
    }
    @Test
    fun onEvent_CloseAddExerciseDefClicked_stateProperlyUpdated() = runTest {

        setupViewModel(
            initialState = LibraryState(
                isAddExerciseDefSheetOpen = true,
                exerciseNameError = "Test",
                exerciseBodyRegionError = "Test",
                exerciseTargetMusclesError = "Test"
            ),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(LibraryEvent.CloseAddDefClicked)

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
        viewModel.onEvent(LibraryEvent.OnSearchStringChanged("Test"))

        val state = viewModel.state.first()

        assertEquals(
            state.searchString,
            "Test",
            "SearchString does not equal 'Test'"
        )

    }
    @Test
    fun onEvent_ToggleIsDropdownOpen_stateProperlyUpdated() = runTest {
        viewModel.onEvent(LibraryEvent.ToggleIsSearchDropdownOpen)
        val state = viewModel.state.first()

        assertTrue(
            state.isSearchDropdownOpen,
            "isSearchDropdownOpen should be true after first toggle."
        )

    }


    @Test
    fun onEvent_setCurrentFilterType_typeReflectedInState() = runTest {
        var state = viewModel.state.first()
        var currentFilterType = state.searchFilterType
        val testFilterType = ExerciseLibraryFilterType.Barbell()

        assertNull(currentFilterType, "Filter should be null initially.")

        viewModel.onEvent(LibraryEvent.SetCurrentFilterType(testFilterType))

        state = viewModel.state.first()
        currentFilterType = state.searchFilterType

        assertEquals(
            testFilterType,
            currentFilterType,
            "Filter type not updated properly."
        )

    }

    @Test
    fun onEvent_clearFilterType_filterNullInState() = runTest {
        val testFilterType = ExerciseLibraryFilterType.Barbell()

        setupViewModel(
            LibraryState(
                searchFilterType = testFilterType
            )
        )
        var state = viewModel.state.first()
        var currentFilterType = state.searchFilterType


        assertNotNull(currentFilterType, "Filter should be $testFilterType.")

        viewModel.onEvent(LibraryEvent.ClearFilterType)

        state = viewModel.state.first()
        currentFilterType = state.searchFilterType

        assertNull(currentFilterType, null)

    }

    @Test
    fun onEvent_ToggleIsFavorite_valueUpdatedInDb() = runTest {
        var state = viewModel.state.first()
        val defToFavorite = state.exerciseDefinitions[0]

        assertFalse(defToFavorite.isFavorite)

        viewModel.onEvent(LibraryEvent.DefinitionSelected(defToFavorite))
        state = viewModel.state.first()

        viewModel.onEvent(LibraryEvent.ToggleIsFavorite(state.selectedExerciseDefinition!!))
        state = viewModel.state.first()

        assertTrue(state.exerciseDefinitions.last().isFavorite)
    }

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

