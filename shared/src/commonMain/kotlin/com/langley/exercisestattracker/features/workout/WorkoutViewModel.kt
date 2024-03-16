package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

    private val _definitions = MutableStateFlow(dataSource.getDefinitions())

    val definitions = combine(
        _definitions,
        dataSource.getDefinitions()
    ){
        currentDefinitions, newDefinitions ->

        newDefinitions

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = listOf<ExerciseDefinition>()
    )


    fun onEvent(workoutEvent: WorkoutEvent){

        when (workoutEvent){

            is WorkoutEvent.AddRecord -> {
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

            is WorkoutEvent.RemoveRecord -> {

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

                _state.update { it.copy(
                    exerciseSelectorVisible = false
                ) }

            }
            WorkoutEvent.OpenExerciseSelector -> {

                _state.update { it.copy(
                    exerciseSelectorVisible = true
                ) }

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

                mutableList.removeAt(workoutEvent.index)

                _state.update { it.copy(
                    completedExercises = mutableList
                ) }
            }

            WorkoutEvent.SaveWorkout -> {

                viewModelScope.launch {

                    for (record in _state.value.completedExercises){

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
        }

    }

}