package com.langley.exercisestattracker.features.exerciseBuilder

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
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
import kotlin.test.assertTrue

class ExerciseBuilderViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ExerciseBuilderViewModel
    private lateinit var testExerciseDefinition: ExerciseDefinition
    private val testString = "Test"

    private fun setupViewModel(
        initialState: ExerciseBuilderState = ExerciseBuilderState(),
    ): ExerciseBuilderViewModel {

        viewModel = viewModelFactory {

            ExerciseBuilderViewModel(

                exerciseAppDataSource = TestExerciseAppDataSource(
                    ExerciseDefinitionDummyData().definitionList
                ),
                initialState = initialState,
                libraryOnEvent = {}
            )
        }.createViewModel()

        return viewModel
    }

    @Before
    fun setup() = runTest{

        setupViewModel(ExerciseBuilderState())

        testExerciseDefinition = ExerciseDefinition(
            exerciseDefinitionId = null,
            exerciseName = "Test",
            bodyRegion = "Test",
            targetMuscles = "Test",
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
    fun onEvent_OnToggleBodyRegion_primaryTargetListUpdated() = runTest {
        var state = viewModel.state.first()

        assertFalse(
            state.primaryTargetList?.contains(testString)?: false,
            "Test string should not be found before toggling."
        )

        viewModel.onEvent(ExerciseBuilderEvent.ToggleBodyRegion(testString))

        state = viewModel.state.first()

        assertTrue(
            state.primaryTargetList?.contains(testString)?: false,
            "Test string not found after toggling on."
        )

        viewModel.onEvent(ExerciseBuilderEvent.ToggleBodyRegion(testString))

        state = viewModel.state.first()

        assertFalse(
            state.primaryTargetList?.contains(testString)?: false,
            "Test string should not be found after toggling off."
        )

    }

    @Test
    fun onEvent_ExerciseDescriptionChanged_newExerciseProperlyUpdated() = runTest {
        var state = viewModel.state.first()

        assertFalse(
            state.newExerciseDefinition.description.contains(testString),
            "Description must not contain test string before event."
        )

        viewModel.onEvent(ExerciseBuilderEvent.OnDescriptionChanged(testString))

        state = viewModel.state.first()

        assertTrue(
            state.newExerciseDefinition.description.contains(testString),
            "Test string not found in description."
        )
    }

    @Test
    fun onEvent_OnExerciseNameChanged_newExerciseProperlyUpdated() = runTest {
        var state = viewModel.state.first()

        assertNotEquals(
            testString,
            state.newExerciseDefinition.exerciseName,
            "Exercise name should not equal test string yet."
        )

        viewModel.onEvent(ExerciseBuilderEvent.OnNameChanged(testString))

        state = viewModel.state.first()

        assertEquals(
            expected =testString,
            actual = state.newExerciseDefinition.exerciseName,
            message ="Exercise name does not equal test string after event."
        )
    }

    @Test
    fun onEvent_OnToggleTargetMuscles_musclesListProperlyUpdated() = runTest {

        var state = viewModel.state.first()

        assertFalse(
            state.musclesList?.contains(testString) ?: false
        )

        viewModel.onEvent(ExerciseBuilderEvent.ToggleTargetMuscle(testString))

        state = viewModel.state.first()


        assertTrue(
            state.musclesList?.contains(testString)?: false,
            "Test string not found after toggling on."
        )

        viewModel.onEvent(ExerciseBuilderEvent.ToggleTargetMuscle(testString))

        state = viewModel.state.first()

        assertFalse(
            state.musclesList?.contains(testString)?: false,
            "Test string should not be found after toggling off."
        )
    }

    @Test
    fun onEvent_SaveOrUpdateExerciseDefWithCorrectInput_stateProperlyUpdated() = runTest {

        viewModel.onEvent(ExerciseBuilderEvent.SaveOrUpdateDef)

        val state = viewModel.state.first()

        var savedExerciseDefFoundInState = false

//        for (def in state.exerciseDefinitions){
//
//            if (def.exerciseName == testExerciseDefinition.exerciseName){
//                savedExerciseDefFoundInState = true
//            }
//
//        }

//        assertTrue(savedExerciseDefFoundInState,
//            "testExercise not found in state after SaveOrUpdate event."
//        )
    }
//
//    @Test
//    fun onEvent_SaveOrUpdateExerciseDefWithIncorrectInput_stateProperlyUpdated() = runTest {
//        testExerciseDefinition = testExerciseDefinition.copy(
//            exerciseName = "",
//            bodyRegion = "",
//            targetMuscles = "",
//        )
//
//        setupViewModel(
//            LibraryState(),
//            newExerciseDefinition = testExerciseDefinition
//        )
//
//        viewModel.onEvent(LibraryEvent.SaveOrUpdateDef)
//
//        val state = viewModel.state.first()
//
//        assertNotNull(state.exerciseNameError,
//            "Name error is null in state."
//        )
//        assertNotNull(state.exerciseBodyRegionError,
//            "Body Region error is null in state."
//        )
//        assertNotNull(state.exerciseTargetMusclesError,
//            "Target Muscles error is null in state."
//        )
//    }
//
//    @Test
//    fun onEvent_SaveOrUpdateExerciseDefWithDuplicateDef_stateProperlyUpdated() = runTest {
//
//
//        testExerciseDefinition = testExerciseDefinition.copy(
//            exerciseDefinitionId = 0
//        )
//
//        setupViewModel(
//            LibraryState(),
//            newExerciseDefinition = testExerciseDefinition
//        )
//
//        var state = viewModel.state.first()
//        val initialDefinitionListSize = state.exerciseDefinitions.size
//
//        viewModel.onEvent(LibraryEvent.SaveOrUpdateDef)
//
//        state = viewModel.state.first()
//
//        val finalDefinitionListSize = state.exerciseDefinitions.size
//        val updatedDefinition = state.exerciseDefinitions[finalDefinitionListSize - 1]
//
//        assertEquals(
//            initialDefinitionListSize,
//            finalDefinitionListSize,
//            "List size changed after updating event."
//            )
//
//        assertEquals(
//            testExerciseDefinition.exerciseName,
//            updatedDefinition.exerciseName,
//            "Last element in definitions list doesn't match updated definition."
//        )
//
//    }

}