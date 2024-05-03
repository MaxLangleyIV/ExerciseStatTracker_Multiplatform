package com.langley.exercisestattracker.features.library

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.ExerciseRoutineDummyData
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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

        val dummyData = ExerciseDefinitionDummyData()
        val dummyRoutineData = ExerciseRoutineDummyData(dummyData.definitionList)

        viewModel = viewModelFactory {

            LibraryViewModel(

                TestExerciseAppDataSource(
                    dummyDefinitions = dummyData.definitionList,
                    dummyRoutines = dummyRoutineData.getRoutines(),
                    dummySchedules = dummyRoutineData.getSchedules(10)
                ),
                initialState,
                newExerciseDefinition,
                ExerciseRoutine()
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
            state.exercises.isNotEmpty(),
            "Definitions list is empty."
        )

        for (i in 0..<state.exercises.size) {

            assertEquals(exerciseDefinitionList[i].exerciseName,
                state.exercises[i].exerciseName,
                "${exerciseDefinitionList[i].exerciseName} not equal to " +
                        "definition in state: ${state.exercises[i].exerciseName}"
            )
        }
    }

    @Test
    fun onEvent_saveExerciseDefinition_savedExerciseFoundInState() = runTest {
        testExerciseDefinition = testExerciseDefinition.copy(
            exerciseName = "Testing SaveExercise event."
        )

        viewModel.onEvent(LibraryEvent.SaveExercise(testExerciseDefinition))

        val state = viewModel.state.first()
        var savedExerciseFoundInState = false

        for (def in state.exercises){

            if (def.exerciseName == testExerciseDefinition.exerciseName){
                savedExerciseFoundInState = true
            }
        }
        assertTrue(savedExerciseFoundInState,
            "Unable to find definition with matching name.")
    }

    @Test
    fun onEvent_saveExerciseRoutine_savedRoutineFoundInState() = runTest {
        val testRoutine = ExerciseRoutine(routineName = "Test")

        viewModel.onEvent(LibraryEvent.SaveRoutine(testRoutine))

        val state = viewModel.state.first()
        var savedRoutineFoundInState = false

        for (routine in state.routines){

            if (routine.routineName == testRoutine.routineName){
                savedRoutineFoundInState = true
            }
        }
        assertTrue(savedRoutineFoundInState,
            "Unable to find routine with matching name.")
    }

    @Test
    fun onEvent_saveExerciseSchedule_savedScheduleFoundInState() = runTest {
        val testSchedule = ExerciseSchedule( exerciseScheduleName = "Test" )

        viewModel.onEvent(LibraryEvent.SaveSchedule(testSchedule))

        val state = viewModel.state.first()
        var savedScheduleFoundInState = false

        for (schedule in state.schedules){

            if (schedule.exerciseScheduleName == testSchedule.exerciseScheduleName){
                savedScheduleFoundInState = true
            }
        }
        assertTrue(savedScheduleFoundInState,
            "Unable to find schedule with matching name.")
    }

    @Test
    fun onEvent_exerciseDefSelectedCalled_selectedDefReflectedInState() = runTest{
        var state = viewModel.state.first()

        val randomNum = Random.nextInt(0,(state.exercises.size - 1))
        val selectedDef = state.exercises[randomNum]

        viewModel.onEvent(LibraryEvent.ExerciseSelected(selectedDef))

        state = viewModel.state.first()

        assertEquals(selectedDef, state.selectedExercise,
            "selectedDef: $selectedDef does not equal def in state: " +
                    "${state.selectedExercise}" +
                    "Definition selected with index: $randomNum"
        )

        assertTrue(state.isExerciseDetailsSheetOpen,
            "isExerciseDetailsSheetOpen failed to be set true after selection.")
    }

    @Test
    fun onEvent_RoutineSelected_selectedRoutineInState() = runTest{

        val selectedRoutine = ExerciseRoutine(routineName = "Selected Test")

        viewModel.onEvent(LibraryEvent.RoutineSelected(selectedRoutine))

        val state = viewModel.state.first()


        assertEquals(
            expected = selectedRoutine,
            actual = state.selectedRoutine,
            message =
            "$selectedRoutine does not equal routine in state:${state.selectedRoutine}"
        )

        assertTrue(state.isRoutineDetailsSheetOpen,
            "isRoutineDetailsSheetOpen failed to be set true after selection.")
    }

    @Test
    fun onEvent_ScheduleSelected_selectedScheduleInState() = runTest{

        val exerciseSchedule = ExerciseSchedule(exerciseScheduleName = "Selected Test")

        viewModel.onEvent(LibraryEvent.ScheduleSelected(exerciseSchedule))

        val state = viewModel.state.first()


        assertEquals(
            expected = exerciseSchedule,
            actual = state.selectedSchedule,
            message =
            "$exerciseSchedule does not equal schedule in state:${state.selectedSchedule}"
        )

        assertTrue(state.isScheduleDetailsSheetOpen,
            "isScheduleDetailsSheetOpen failed to be set true after selection.")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onEvent_closeDetailsViewCalled_AllDetailsSheetOpenFlagsAreFalse_allSelectedValNull() = runTest {

        setupViewModel(
            LibraryState(
                isExerciseDetailsSheetOpen = true,
                isRoutineDetailsSheetOpen = true,
                isScheduleDetailsSheetOpen = true,
                selectedExercise = ExerciseDefinition(),
                selectedSchedule = ExerciseSchedule(),
                selectedRoutine = ExerciseRoutine()
            )
        )

        viewModel.onEvent(LibraryEvent.CloseDetailsView)

        delay(300L)

        val state = viewModel.state.first()

        assertFalse(
            actual = state.isExerciseDetailsSheetOpen,
            message = "isExerciseDetailsSheetOpen failed to be set false " +
                    "after CloseDetailsView."
        )
        assertFalse(
            state.isRoutineDetailsSheetOpen,
            "isRoutineDetailsSheetOpen failed to be set false " +
                    "after CloseDetailsView."
        )
        assertFalse(
            actual = state.isScheduleDetailsSheetOpen,
            message = "isScheduleDetailsSheetOpen failed to be set false " +
                    "after CloseDetailsView."
        )

        advanceUntilIdle()

        assertNull(
            actual = state.selectedExercise,
            message = "selectedDefinition is not null after event"
        )
        assertNull(
            actual = state.selectedRoutine,
            message = "selectedRoutine is not null after event"
        )
        assertNull(
            actual = state.selectedSchedule,
            message = "selectedSchedule is not null after event"
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onEvent_closeEditViewCalled_AllEditSheetOpenFlagsAreFalse_allSelectedValNull() = runTest {

        setupViewModel(
            LibraryState(
                isEditExerciseSheetOpen = true,
                isEditRoutineSheetOpen = true,
                isEditScheduleSheetOpen = true,
            )
        )

        viewModel.onEvent(LibraryEvent.CloseEditView)

        delay(300L)

        val state = viewModel.state.first()

        assertFalse(
            actual = state.isEditExerciseSheetOpen,
            message = "isEditExerciseDefSheetOpen failed to be set false "
        )
        assertFalse(
            state.isEditRoutineSheetOpen,
            "isEditRoutineSheetOpen failed to be set false "
        )
        assertFalse(
            actual = state.isEditScheduleSheetOpen,
            message = "isEditScheduleSheetOpen failed to be set false "
        )
    }

    @Test
    fun onEvent_EditExerciseDefinition_stateProperlyUpdated() = runTest {
        val selectedDef = viewModel.state.first().exercises[0]

        setupViewModel(
            LibraryState(
                isAddExerciseSheetOpen = true,
                selectedExercise = selectedDef
            )
        )

        viewModel.onEvent(LibraryEvent.EditExercise(selectedDef))

        val state = viewModel.state.first()

        assertTrue(state.isAddExerciseSheetOpen,
            "EditExerciseDefSheetOpen is false after EditExercise event."
        )

        assertEquals(state.selectedExercise, selectedDef,
            "Selected Def in state: ${state.selectedExercise} " +
                    "does not equal selected def: $selectedDef"
        )
    }

    @Test
    fun onEvent_EditRoutine_stateProperlyUpdated() = runTest {

        viewModel.onEvent(LibraryEvent.EditRoutine)

        val state = viewModel.state.first()

        assertTrue(state.isEditRoutineSheetOpen,
            "isEditRoutineSheetOpen is false after EditExercise event."
        )

    }



    @Test
    fun onEvent_AddNewExerciseDefClicked_stateProperlyUpdated() = runTest {
        viewModel.onEvent(LibraryEvent.AddNewExercise)

        val state = viewModel.state.first()

        assertTrue(
            state.isAddExerciseSheetOpen,
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
                isAddExerciseSheetOpen = true,
                exerciseNameError = "Test",
                exerciseBodyRegionError = "Test",
                exerciseTargetMusclesError = "Test"
            ),
            newExerciseDefinition = testExerciseDefinition
        )

        viewModel.onEvent(LibraryEvent.CloseAddDefClicked)

        val state = viewModel.state.first()

        assertFalse(
            state.isAddExerciseSheetOpen,
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
    fun onEvent_ToggleFavoriteDef_valueUpdatedInDb() = runTest {
        var state = viewModel.state.first()
        val defToFavorite = state.exercises[0]

        assertFalse(defToFavorite.isFavorite)

        viewModel.onEvent(LibraryEvent.ExerciseSelected(defToFavorite))
        state = viewModel.state.first()

        viewModel.onEvent(LibraryEvent.ToggleFavoriteExercise(state.selectedExercise!!))
        state = viewModel.state.first()

        assertTrue(state.exercises.last().isFavorite)
    }

    @Test
    fun onEvent_ToggleFavoriteRoutine_valueUpdatedInDb() = runTest {
        var state = viewModel.state.first()
        val routine = state.routines[0]

        assertFalse(routine.isFavorite)

        viewModel.onEvent(LibraryEvent.RoutineSelected(routine))
        state = viewModel.state.first()

        viewModel.onEvent(LibraryEvent.ToggleFavoriteRoutine(state.selectedRoutine!!))
        state = viewModel.state.first()

        assertTrue(state.routines.last().isFavorite)
    }

    @Test
    fun onEvent_ToggleFavoriteSchedule_valueUpdatedInDb() = runTest {
        var state = viewModel.state.first()
        val schedule = state.schedules[0]

        assertFalse(schedule.isFavorite)

        viewModel.onEvent(LibraryEvent.ScheduleSelected(schedule))
        state = viewModel.state.first()

        viewModel.onEvent(LibraryEvent.ToggleFavoriteSchedule(state.selectedSchedule!!))
        state = viewModel.state.first()

        assertTrue(state.schedules.last().isFavorite)
    }

    @Test
    fun onEvent_SelectDefinitionsTab_isShowingDefinitionsTrueAndOtherTabsFalse() = runTest {
        setupViewModel(
            LibraryState(
            isShowingExercises = false
        )
        )

        var state = viewModel.state.first()

        assertFalse(
            actual = state.isShowingExercises,
            message = "isShowingExercises should start false for this test"
        )

        viewModel.onEvent(LibraryEvent.SelectExercisesTab)

        state = viewModel.state.first()

        assertTrue(
            actual = state.isShowingExercises,
            message = "isShowingExercises should be true after event"
        )

        assertFalse(
            actual = state.isShowingRoutines,
            message = "isShowingRoutines should be false after event"
        )

        assertFalse(
            actual = state.isShowingSchedules,
            message = "isShowingSchedules should be false after event"
        )

    }

    @Test
    fun onEvent_SelectRoutinesTab_isShowingRoutinesTrueAndOtherTabsFalse() = runTest {

        viewModel.onEvent(LibraryEvent.SelectRoutinesTab)

        val state = viewModel.state.first()

        assertTrue(
            actual = state.isShowingRoutines,
            message = "isShowingRoutines should be true after event"
        )

        assertFalse(
            actual = state.isShowingExercises,
            message = "isShowingExercises should be false after event"
        )

        assertFalse(
            actual = state.isShowingSchedules,
            message = "isShowingSchedules should be false after event"
        )

    }

    @Test
    fun onEvent_SelectSchedulesTab_isShowingSchedulesTrueAndOtherTabsFalse() = runTest {

        viewModel.onEvent(LibraryEvent.SelectSchedulesTab)

        val state = viewModel.state.first()

        assertTrue(
            actual = state.isShowingSchedules,
            message = "isShowingExercises should be true after event"
        )

        assertFalse(
            actual = state.isShowingRoutines,
            message = "isShowingRoutines should be false after event"
        )

        assertFalse(
            actual = state.isShowingExercises,
            message = "isShowingExercises should be false after event"
        )

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

