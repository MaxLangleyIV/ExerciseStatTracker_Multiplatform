package com.langley.exercisestattracker.features.library.selector

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.features.library.ExerciseLibraryFilterType
import com.langley.exercisestattracker.features.library.LibraryState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

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

    fun updateSearchString(string: String){
        _state.update { it.copy(
            searchString = string
        ) }
    }

    fun updateFilterType(filter: ExerciseLibraryFilterType?){
        _state.update { it.copy(
            filterType = filter
        ) }
    }

    fun updateDropdownState(value: Boolean){
        _state.update { it.copy(
            dropdownExpanded = value
        ) }
    }

    fun toggleSelectedDef(def: ExerciseDefinition, ){

        val mutableList = _state.value.selectedExercises.toMutableList()

        if (mutableList.contains(def)){
            mutableList.remove(def)
        }
        else { mutableList.add(def) }

        _state.update { it.copy(
            selectedExercises = mutableList
        ) }
    }

}