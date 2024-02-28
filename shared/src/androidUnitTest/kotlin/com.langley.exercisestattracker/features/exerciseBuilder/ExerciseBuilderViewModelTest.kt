package com.langley.exercisestattracker.features.exerciseBuilder

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.features.library.MainDispatcherRule
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ExerciseBuilderViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var builderViewModel: ExerciseBuilderViewModel
    private lateinit var testDefinition: ExerciseDefinition
    private val testString = "Test"

    private fun setupViewModel(
        initialState: ExerciseBuilderState = ExerciseBuilderState(),
    ): ExerciseBuilderViewModel {

        builderViewModel = viewModelFactory {

            ExerciseBuilderViewModel(

                exerciseAppDataSource = TestExerciseAppDataSource(
                    ExerciseDefinitionDummyData().definitionList
                ),
                initialState = initialState,
                libraryOnEvent = {}
            )
        }.createViewModel()

        return builderViewModel
    }

    @Before
    fun setup() = runTest{

        setupViewModel(ExerciseBuilderState())

        testDefinition = ExerciseDefinition(
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
        var state = builderViewModel.state.first()

        assertFalse(
            state.primaryTargetList?.contains(testString)?: false,
            "Test string should not be found before toggling."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleBodyRegion(testString))

        state = builderViewModel.state.first()

        assertTrue(
            state.primaryTargetList?.contains(testString)?: false,
            "Test string not found after toggling on."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleBodyRegion(testString))

        state = builderViewModel.state.first()

        assertFalse(
            state.primaryTargetList?.contains(testString)?: false,
            "Test string should not be found after toggling off."
        )

    }

    @Test
    fun onEvent_ExerciseDescriptionChanged_newExerciseProperlyUpdated() = runTest {
        var state = builderViewModel.state.first()

        assertFalse(
            state.newExerciseDefinition.description.contains(testString),
            "Description must not contain test string before event."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.OnDescriptionChanged(testString))

        state = builderViewModel.state.first()

        assertTrue(
            state.newExerciseDefinition.description.contains(testString),
            "Test string not found in description."
        )
    }

    @Test
    fun onEvent_OnExerciseNameChanged_newExerciseProperlyUpdated() = runTest {
        var state = builderViewModel.state.first()

        assertNotEquals(
            testString,
            state.newExerciseDefinition.exerciseName,
            "Exercise name should not equal test string yet."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.OnNameChanged(testString))

        state = builderViewModel.state.first()

        assertEquals(
            expected =testString,
            actual = state.newExerciseDefinition.exerciseName,
            message ="Exercise name does not equal test string after event."
        )
    }

    @Test
    fun onEvent_OnToggleTargetMuscles_musclesListProperlyUpdated() = runTest {

        var state = builderViewModel.state.first()

        assertFalse(
            state.musclesList?.contains(testString) ?: false
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleTargetMuscle(testString))

        state = builderViewModel.state.first()


        assertTrue(
            state.musclesList?.contains(testString)?: false,
            "Test string not found after toggling on."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleTargetMuscle(testString))

        state = builderViewModel.state.first()

        assertFalse(
            state.musclesList?.contains(testString)?: false,
            "Test string should not be found after toggling off."
        )
    }

    @Test
    fun onEvent_toggleDefinitionFields_newExerciseDefUpdatedCorrectly()= runTest {

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleIsWeighted)
        var state: ExerciseBuilderState = builderViewModel.state.first()
        assertTrue(
            state.newExerciseDefinition.isWeighted,
            "isWeighted should be true."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleIsWeighted)
        state = builderViewModel.state.first()
        assertFalse(
            state.newExerciseDefinition.isWeighted,
            "isWeighted should be false."
        )


        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleHasReps)
        state = builderViewModel.state.first()
        assertTrue(
            state.newExerciseDefinition.hasReps,
            "hasReps should be true."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleHasReps)
        state = builderViewModel.state.first()
        assertFalse(
            state.newExerciseDefinition.hasReps,
            "hasReps should be false."
        )


        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleIsCardio)
        state = builderViewModel.state.first()
        assertTrue(
            state.newExerciseDefinition.isCardio,
            "isCardio should be true."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleIsCardio)
        state = builderViewModel.state.first()
        assertFalse(
            state.newExerciseDefinition.isCardio,
            "isCardio should be false."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleIsTimed)
        state = builderViewModel.state.first()
        assertTrue(
            state.newExerciseDefinition.isTimed,
            "isTimed should be true."
        )
        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleIsTimed)
        state = builderViewModel.state.first()
        assertFalse(
            state.newExerciseDefinition.isTimed,
            "isTimed should be false."
        )


        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleHasDistance)
        state = builderViewModel.state.first()
        assertTrue(
            state.newExerciseDefinition.hasDistance,
            "hasDistance should be true."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleHasDistance)
        state = builderViewModel.state.first()
        assertFalse(
            state.newExerciseDefinition.hasDistance,
            "hasDistance should be false."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleIsCalisthenics)
        state = builderViewModel.state.first()
        assertTrue(
            state.newExerciseDefinition.isCalisthenic,
            "isCalisthenic should be true."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.ToggleIsCalisthenics)
        state = builderViewModel.state.first()
        assertFalse(
            state.newExerciseDefinition.isCalisthenic,
            "isCalisthenic should be false."
        )



    }

    @Test
    fun onEvent_InitializeDefinition_newExerciseDefUpdated() = runTest {
        var state = builderViewModel.state.first()

        assertTrue(
            state.newExerciseDefinition.exerciseName.isBlank(),
            "NewExerciseDef name should be blank initially."
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.InitializeDefinition(testDefinition))

        state = builderViewModel.state.first()

        assertEquals(
            expected = testDefinition,
            actual = state.newExerciseDefinition,
            "NewExerciseDef should equal testDefinition after event."
        )

    }

    @Test
    fun onEvent_DeclareAsInitialized() = runTest {
        var state = builderViewModel.state.first()

        assertFalse(state.initialized, "Initialized should be set to false.")

        builderViewModel.onEvent(ExerciseBuilderEvent.DeclareAsInitialized)

        state = builderViewModel.state.first()

        assertTrue(state.initialized, "Initialized should be set to true.")

    }

    @Test
    fun onEvent_SaveOrUpdateExerciseDefWithCorrectInput_stateProperlyUpdated() = runTest {

        // Check that testDef isn't already in the data source.
        for (def in builderViewModel.definitions.first()){

            assertNotEquals(
                illegal = testDefinition.exerciseName,
                actual = def.exerciseName,
                message = "TestDef found in datasource before saving."
            )

        }

        builderViewModel.onEvent(ExerciseBuilderEvent.InitializeDefinition(testDefinition))

        builderViewModel.onEvent(ExerciseBuilderEvent.SaveOrUpdateDef)

        var savedExerciseDefFoundInState = false

        for (def in builderViewModel.definitions.first()){

            if (def.exerciseName == testDefinition.exerciseName){
                savedExerciseDefFoundInState = true
            }

        }


        assertTrue(savedExerciseDefFoundInState,
            "testExercise not found in state after SaveOrUpdate event."
        )
    }

    @Test
    fun onEvent_SaveOrUpdateExerciseDefWithIncorrectInput_stateProperlyUpdated() = runTest {
        testDefinition = testDefinition.copy(
            exerciseName = "",
            bodyRegion = "",
            targetMuscles = "",
        )


        builderViewModel.onEvent(ExerciseBuilderEvent.InitializeDefinition(testDefinition))

        builderViewModel.onEvent(ExerciseBuilderEvent.SaveOrUpdateDef)

        val state = builderViewModel.state.first()

        assertNotNull(
            actual = state.exerciseNameError,
            message = "Exercise name error should not be null."
        )
    }

    @Test
    fun onEvent_SaveOrUpdateExerciseDefWithDuplicateDef_stateProperlyUpdated() = runTest {


        testDefinition = testDefinition.copy(
            exerciseDefinitionId = 0
        )

        val initialDefinitionListSize = builderViewModel.definitions.first().size

        builderViewModel.onEvent(ExerciseBuilderEvent.InitializeDefinition(testDefinition))

        builderViewModel.onEvent(ExerciseBuilderEvent.SaveOrUpdateDef)



        val finalDefinitionListSize = builderViewModel.definitions.first().size
        val updatedDefinition = builderViewModel.definitions.first()[finalDefinitionListSize - 1]

        assertEquals(
            expected = initialDefinitionListSize,
            actual = finalDefinitionListSize,
            message = "List size changed after updating event."
            )

        assertEquals(
            expected = testDefinition.exerciseName,
            actual = updatedDefinition.exerciseName,
            message = "Last element in definitions list doesn't match updated definition."
        )

    }

    @Test
    fun onEvent_DeleteExerciseDefinition_stateProperlyUpdated() = runTest {

        val exerciseDefToDelete = builderViewModel.definitions.first()[0]

        builderViewModel.onEvent(
            ExerciseBuilderEvent.InitializeDefinition(exerciseDefToDelete)
        )

        builderViewModel.onEvent(ExerciseBuilderEvent.DeleteDefinition)


        assertFalse(
            builderViewModel.definitions.first().contains(exerciseDefToDelete),
            "${exerciseDefToDelete.exerciseName} " +
                    "found in definitions after deletion event."
        )

    }

    @Test
    fun onEvent_CloseAddDef_stateUpdatedProperly() = runTest {

        launch {
            builderViewModel.onEvent(ExerciseBuilderEvent.InitializeDefinition(testDefinition))
            builderViewModel.onEvent(ExerciseBuilderEvent.DeclareAsInitialized)

            var state = builderViewModel.state.first()

            assertTrue(state.initialized, "Initialized should be true.")

            builderViewModel.onEvent(ExerciseBuilderEvent.CloseAddDef)

            delay(300L)

            state = builderViewModel.state.first()

            assertFalse(state.initialized, "Initialized should be false.")
            assertNull(state.exerciseNameError, "exerciseNameError should be null.")
            assertNull(state.exerciseBodyRegionError, "Body regions should be null.")
            assertNull(state.exerciseTargetMusclesError, "Target muscles should be null.")
            assertNull(state.primaryTargetList, "Primary targets list should be null.")
            assertNull(state.musclesList, "Muscle list should be null.")
            assertFalse(state.initialized, "Initialized should be false.")
            assertEquals(
                expected = ExerciseDefinition(),
                actual = state.newExerciseDefinition,
                message = "NewExerciseDefinition should equal default definition."
            )
        }

    }

}