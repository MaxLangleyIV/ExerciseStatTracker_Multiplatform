package com.langley.exercisestattracker.records

import com.langley.exercisestattracker.core.TestExerciseAppDataSource
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.core.data.dummyData.toListOfExerciseDefinitionsWithIndex
import com.langley.exercisestattracker.library.MainDispatcherRule
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.junit.Rule

class RecordsViewModelTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RecordsViewModel

    private fun setupViewModel( initialState: RecordsState ): RecordsViewModel {

        viewModel = viewModelFactory {

            RecordsViewModel(

                TestExerciseAppDataSource(
                    ExerciseDefinitionDummyData().toListOfExerciseDefinitionsWithIndex()
                ),
                initialState,

            )
        }.createViewModel()

        return viewModel
    }

}