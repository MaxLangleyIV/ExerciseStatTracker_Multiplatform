package com.langley.exercisestattracker.exerciseLibrary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSource
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseLibraryViewModel(
    private val exerciseLibraryDataSource: ExerciseDefinitionDataSource
): ViewModel() {
    private val _state = MutableStateFlow(ExerciseLibraryState())
    val state = combine(
        _state,
        exerciseLibraryDataSource.getDefinitions()
    ){
        state, exerciseDefinitions ->
        state.copy(
            exerciseDefinitions = exerciseDefinitions
        )
    }

    var newExerciseDefinition: ExerciseDefinition? by mutableStateOf(null)
        private set

    fun onEvent(event: ExerciseLibraryEvent) {
        when (event) {
            ExerciseLibraryEvent.DefaultEvent -> return

            is ExerciseLibraryEvent.ExerciseDefinitionSelected -> {
                _state.update { it.copy(
                    selectedExerciseDefinition = event.exerciseDefinition,
                    isSelectedExerciseDefSheetOpen = true
                ) }
            }

            is ExerciseLibraryEvent.SaveExerciseDefinition -> {
                viewModelScope.launch {
                    exerciseLibraryDataSource.insertOrReplaceExerciseDefinition(event.exerciseDefinition)
                }
            }

            ExerciseLibraryEvent.CloseExerciseDetailsView -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isSelectedExerciseDefSheetOpen = false
                    ) }
                    delay(300L) //BottomSheet animation delay
                    _state.update { it.copy(
                        selectedExerciseDefinition = null
                    ) }
                }
            }

            is ExerciseLibraryEvent.EditExerciseDefinition -> {
                _state.update { it.copy(
                    isEditExerciseDefSheetOpen = true,
                ) }
            }

            ExerciseLibraryEvent.CloseEditExerciseDefView -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isEditExerciseDefSheetOpen = false
                    ) }
                }
            }

            is ExerciseLibraryEvent.OnBodyRegionChanged -> TODO()
            is ExerciseLibraryEvent.OnExerciseDescriptionChanged -> TODO()
            is ExerciseLibraryEvent.OnExerciseNameChanged -> TODO()
            is ExerciseLibraryEvent.OnTargetMusclesChanged -> TODO()
        }
    }
}