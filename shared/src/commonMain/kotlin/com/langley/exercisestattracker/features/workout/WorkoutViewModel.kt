package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class WorkoutViewModel(
    private val dataSource: ExerciseAppDataSource,
    initialState: WorkoutState = WorkoutState()
): ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state
        .asStateFlow()
        .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        ExerciseBuilderState()
        )
}