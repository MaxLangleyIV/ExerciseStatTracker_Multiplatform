package com.langley.exercisestattracker.features.library.routines

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.library.LibraryEvent
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RoutineBuilderViewModel(
    private val dataSource: ExerciseAppDataSource,
    initialState: RoutineBuilderState = RoutineBuilderState(),
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

    fun onEvent(event: RoutineBuilderEvent){
        when(event){
            is RoutineBuilderEvent.AddToListOfExercises -> {
                TODO()
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
                TODO()
            }

            is RoutineBuilderEvent.RemoveExercise -> {
                TODO()
            }
            is RoutineBuilderEvent.InsertOrReplaceRoutine -> {
                TODO()
            }
        }
    }


}