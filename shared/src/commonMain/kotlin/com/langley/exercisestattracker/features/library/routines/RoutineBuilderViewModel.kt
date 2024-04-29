package com.langley.exercisestattracker.features.library.routines

import com.langley.exercisestattracker.core.data.getExercisesFromCSV
import com.langley.exercisestattracker.core.data.toBlankRecord
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutineBuilderViewModel(
    private val dataSource: ExerciseAppDataSource,
    initialState: RoutineBuilderState = RoutineBuilderState(),
): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = combine(
        _state,
        dataSource.getDefinitions()
    ){
            currentState, definitions ->
        currentState.copy(
            exerciseLibrary = definitions
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = initialState
    )

    fun onEvent(event: RoutineBuilderEvent){
        when(event){
            is RoutineBuilderEvent.AddToListOfExercises -> {
                val mutableList = _state.value.exerciseList.toMutableList()

                for (exercise in event.exercises){
                    mutableList.add(exercise)
                }

                _state.update { it.copy(
                    exerciseList = mutableList
                ) }

            }
            RoutineBuilderEvent.OpenSelector -> {
                _state.update { it.copy(
                    isSelectorOpen = true
                ) }
            }
            RoutineBuilderEvent.CloseSelector -> {
                _state.update { it.copy(
                    isSelectorOpen = false
                ) }
            }
            is RoutineBuilderEvent.OnSearchStringChanged -> {
                _state.update { it.copy(
                    searchString = event.value
                ) }
            }

            is RoutineBuilderEvent.RemoveExercise -> {
                val mutableList = _state.value.exerciseList.toMutableList()

                mutableList.removeAt(event.index)

                _state.update { it.copy(
                    exerciseList = mutableList
                ) }

            }
            is RoutineBuilderEvent.InsertOrReplaceRoutine -> {

                if (
                    event.routine.routineName.isNotBlank() &&
                    event.routine.repsCSV.isNotBlank() &&
                    event.routine.exerciseCSV.isNotBlank()
                ){
                    viewModelScope.launch {
                        dataSource.insertOrReplaceRoutine(event.routine)
                    }
                }

            }

            is RoutineBuilderEvent.DeleteRoutine -> {

                val routineId = event.routine.exerciseRoutineId

                if (routineId != null){
                    viewModelScope.launch { dataSource.deleteRoutine(routineId) }
                }

            }

            is RoutineBuilderEvent.AddRecords -> {

                val mutableList = _state.value.recordList.toMutableList()

                for (record in event.records){
                    mutableList.add(record)
                }

                _state.update { it.copy(
                    recordList = mutableList
                ) }

            }

            is RoutineBuilderEvent.UpdateRepsFromString -> {
                val reps = try {
                    event.string.toInt()
                } catch (formatException: NumberFormatException) { 0 }

                val mutableList = _state.value.recordList.toMutableList()

                mutableList[event.index] = mutableList[event.index].copy(
                    repsCompleted = reps
                )

                _state.update { it.copy(
                    recordList = mutableList
                ) }
            }

            is RoutineBuilderEvent.RemoveRecord -> {
                val mutableList = _state.value.recordList.toMutableList()

                mutableList.removeAt(event.index)

                _state.update { it.copy(
                    recordList = mutableList
                ) }
            }

            is RoutineBuilderEvent.UpdateName -> {
                _state.update { it.copy(
                    routine = _state.value.routine.copy(routineName = event.string)
                ) }
            }
            is RoutineBuilderEvent.UpdateDescription -> {
                _state.update { it.copy(
                    routine = _state.value.routine.copy(description = event.string)
                ) }
            }

            is RoutineBuilderEvent.UpdateSelectedRoutine -> {

                viewModelScope.launch {
                    println("STARTING UPDATE")
                    if (_state.value.exerciseLibrary.isEmpty()){
                        _state.update { it.copy(
                            exerciseLibrary = dataSource.getDefinitions().first()
                        ) }
                    }

                    val exercises = _state.value.exerciseList.toMutableList()
                    val records = _state.value.recordList.toMutableList()

                    println(_state.value.exerciseLibrary.size)

                    val newExercises = event.routine.getExercisesFromCSV(
                        _state.value.exerciseLibrary
                    )
                    println(newExercises)

                    val reps = event.routine.repsCSV.split(",")

                    for ((index, exercise) in newExercises.withIndex()){

                        if (!exercises.contains(exercise)){

                            exercises.add(exercise)
                        }

                        records.add(
                            exercise.toBlankRecord().copy(
                                repsCompleted = reps[index].toInt(),
                                completed = false
                            )
                        )

                    }

                    println("EXERCISES: $exercises")
                    println("RECORDS: $records")

                    _state.update { it.copy(
                        exerciseList = exercises,
                        recordList = records,
                        routine = event.routine
                    ) }
                }

            }
        }
    }


}