package com.langley.exercisestattracker.exerciseLibrary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSourceModel
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseLibraryViewModel(
    private val exerciseLibraryDataSourceModel: ExerciseDefinitionDataSourceModel
): ViewModel() {
    private val _state = MutableStateFlow(ExerciseLibraryState())
    val state = _state.asStateFlow()

    var newExerciseDefinition: ExerciseDefinitionModel? by mutableStateOf(null)
        private set

    fun onEvent(event: ExerciseLibraryEvent){
        TODO()
    }
}