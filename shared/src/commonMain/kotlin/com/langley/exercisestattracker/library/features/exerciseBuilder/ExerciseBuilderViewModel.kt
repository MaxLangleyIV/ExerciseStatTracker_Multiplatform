package com.langley.exercisestattracker.library.features.exerciseBuilder

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.library.LibraryState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ExerciseBuilderViewModel(

    private val exerciseAppDataSource: ExerciseAppDataSource,
    initialState: ExerciseBuilderState = ExerciseBuilderState(),
    newExerciseDef: ExerciseDefinition = ExerciseDefinition(),

    ): ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = combine(
        _state,
        exerciseAppDataSource.getDefinitions(),
    ){
            state, exerciseDefinitions ->

        updateExerciseBuilderState(state, exerciseDefinitions)

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LibraryState())

    private fun updateExerciseBuilderState(
        state: ExerciseBuilderState,
        exerciseDefinitions: List<ExerciseDefinition>
    ) {
        TODO("Not yet implemented")
    }


}