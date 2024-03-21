package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.library.utils.filterDefinitionLibrary
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val dataSource: ExerciseAppDataSource,
    initialState: WorkoutState = WorkoutState()
): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = _state
        .asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WorkoutState()
        )


    fun onEvent(workoutEvent: WorkoutEvent){

        when (workoutEvent){

            is WorkoutEvent.AddRecordToMap -> {
                val newRecord = workoutEvent.record
                val newMap = _state.value.exerciseMap.toMutableMap()

                if (newMap[newRecord.exerciseName] == null){

                    newMap[newRecord.exerciseName] = listOf(workoutEvent.record)

                }

                else {
                    val newList = newMap[workoutEvent.record.exerciseName]!!.toMutableList()

                    newList.add(workoutEvent.record)

                    newMap[workoutEvent.record.exerciseName] = newList

                }

                _state.update { it.copy(
                    exerciseMap = newMap
                ) }
            }

            is WorkoutEvent.RemoveRecordFromMap -> {

                if (_state.value.exerciseMap[workoutEvent.recordName] != null){

                    val newMap = _state.value.exerciseMap.toMutableMap()
                    val newList = newMap[workoutEvent.recordName]!!.toMutableList()

                    newList.removeAt(workoutEvent.index)

                    if (newList.isEmpty()){

                        newMap.remove(workoutEvent.recordName)

                    }
                    else {
                        newMap[workoutEvent.recordName] = newList
                    }

                    _state.update { it.copy(
                        exerciseMap = newMap
                    ) }
                }
            }

            WorkoutEvent.CloseExerciseSelector -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        exerciseSelectorVisible = false,
                    ) }

                    delay(300L)

                    _state.update { it.copy(
                        exerciseLibrary = listOf(),
                        searchFilter = null,
                        searchString = "",
                        selectedExercises = listOf()
                    ) }

                }

            }
            WorkoutEvent.OpenExerciseSelector -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        exerciseLibrary = dataSource.getDefinitions().first(),
                        exerciseSelectorVisible = true
                    ) }
                }

            }

            is WorkoutEvent.MarkCompleted -> {

                val mutableList = _state.value.completedExercises.toMutableList()

                mutableList.add(workoutEvent.record)

                _state.update { it.copy(
                    completedExercises = mutableList
                ) }

            }

            is WorkoutEvent.RemoveFromCompleted -> {

                val mutableList = _state.value.completedExercises.toMutableList()

                mutableList.remove(workoutEvent.record)

                _state.update { it.copy(
                    completedExercises = mutableList
                ) }
            }

            WorkoutEvent.SaveWorkout -> {

                for (record in _state.value.completedExercises){

                    viewModelScope.launch {

                        dataSource.insertOrReplaceRecord(record)

                    }

                }

            }

            is WorkoutEvent.DefinitionSelected -> {

                val mutableList = _state.value.selectedExercises.toMutableList()

                if (mutableList.contains(workoutEvent.exerciseDefinition)){
                    mutableList.remove(workoutEvent.exerciseDefinition)
                }
                else {
                    mutableList.add(workoutEvent.exerciseDefinition)
                }

                _state.update { it.copy(
                    selectedExercises = mutableList
                ) }

            }

            is WorkoutEvent.AddToListOfExercises -> {

                val mutableList = _state.value.exerciseList.toMutableList()

                for (def in workoutEvent.exercises){

                    mutableList.add(def)

                }

                mutableList.toSet().toList()

                _state.update { it.copy(
                    exerciseList = mutableList
                ) }

            }
            is WorkoutEvent.AddToListOfRecords -> {

                val mutableList = _state.value.recordsList.toMutableList()

                for (record in workoutEvent.records){

                    mutableList.add(record)

                }

                mutableList.toSet().toList()

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

            }
            is WorkoutEvent.RemoveFromListOfExercises -> {

                val mutableList = _state.value.exerciseList.toMutableList()

                mutableList.remove(workoutEvent.exercise)

                _state.update { it.copy(
                    exerciseList = mutableList
                ) }

            }
            is WorkoutEvent.RemoveFromListOfRecords -> {

                val mutableList = _state.value.recordsList.toMutableList()

                mutableList.removeAt(workoutEvent.index)

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

            }

            is WorkoutEvent.SetCurrentFilterType -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        searchFilter = workoutEvent.filter,
                        exerciseLibrary = filterDefinitionLibrary(
                            definitionLibrary = dataSource.getDefinitions().first(),
                            filterType = workoutEvent.filter,
                            searchString = state.value.searchString
                        )
                    ) }
                }

            }

            WorkoutEvent.ClearFilterType -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        searchFilter = null,
                        exerciseLibrary = filterDefinitionLibrary(
                            definitionLibrary = dataSource.getDefinitions().first(),
                            filterType = null,
                            searchString = state.value.searchString
                        )
                    ) }
                }

            }
            is WorkoutEvent.OnSearchStringChanged -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        searchString = workoutEvent.string,
                        exerciseLibrary = filterDefinitionLibrary(
                            definitionLibrary = dataSource.getDefinitions().first(),
                            filterType = _state.value.searchFilter,
                            searchString = workoutEvent.string
                        )
                    ) }
                }

            }

            is WorkoutEvent.ToggleCompleted -> {



            }
        }

    }

}