package com.langley.exercisestattracker.features.library.features.exerciseBuilder

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.features.library.LibraryViewModel
import com.langley.exercisestattracker.features.library.MainDispatcherRule
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule

class ExerciseBuilderViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ExerciseBuilderViewModel
    private lateinit var testExerciseDefinition: ExerciseDefinition

    private fun setupViewModel(
        initialState: ExerciseBuilderState = ExerciseBuilderState(),
        newExerciseDefinition: ExerciseDefinition = ExerciseDefinition()
    ): ExerciseBuilderViewModel {

        viewModel = viewModelFactory {

            ExerciseBuilderViewModel(

                exerciseAppDataSource = TestExerciseAppDataSource(
                    ExerciseDefinitionDummyData().definitionList
                ),
                initialState = initialState,
                initialExerciseDef = newExerciseDefinition,
                libraryViewModel =
                viewModelFactory {
                    LibraryViewModel(
                        TestExerciseAppDataSource(ExerciseDefinitionDummyData().definitionList)
                    ) }.createViewModel(),
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
}