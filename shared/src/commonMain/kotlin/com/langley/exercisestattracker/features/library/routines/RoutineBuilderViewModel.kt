package com.langley.exercisestattracker.features.library.routines

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
        }
    }


}