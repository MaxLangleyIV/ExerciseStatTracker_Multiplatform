package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.getListOfDummyExerciseRecords
import com.langley.exercisestattracker.features.library.MainDispatcherRule
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.junit.Rule

class WorkoutViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var state: WorkoutState

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

}