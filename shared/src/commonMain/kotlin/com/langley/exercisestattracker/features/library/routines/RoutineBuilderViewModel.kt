package com.langley.exercisestattracker.features.library.routines

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.features.library.LibraryEvent
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class RoutineBuilderViewModel(
    private val dataSource: ExerciseAppDataSource,
    initialState: RoutineBuilderState = RoutineBuilderState(),
    private val libraryOnEvent: (LibraryEvent) -> Unit
): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = _state
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            RoutineBuilderState()
        )

    val definitions = dataSource.getDefinitions()

    fun insertOrReplaceRoutine(newRoutine: ExerciseRoutine){

        TODO()

    }

}