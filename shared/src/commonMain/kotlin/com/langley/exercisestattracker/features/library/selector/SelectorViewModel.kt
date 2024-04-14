package com.langley.exercisestattracker.features.library.selector

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.library.LibraryState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SelectorViewModel(
    private val dataSource: ExerciseAppDataSource,
    initialState: SelectorState = SelectorState()
): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = combine(
        _state,
        dataSource.getDefinitions(),
        dataSource.getRoutines(),
        dataSource.getSchedules()
    ){
            state, definitions, routines, schedules ->

        state.copy(
            exercises = definitions,
            routines = routines,
            schedules = schedules
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SelectorState())

}