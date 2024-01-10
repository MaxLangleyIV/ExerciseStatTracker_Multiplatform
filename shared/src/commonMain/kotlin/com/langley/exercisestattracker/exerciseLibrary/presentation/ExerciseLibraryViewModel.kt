package com.langley.exercisestattracker.exerciseLibrary.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionDataSource
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinitionValidator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ExerciseLibraryState())

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
                newExerciseDefinition = _state.value.selectedExerciseDefinition?.copy()
            }

            ExerciseLibraryEvent.CloseEditExerciseDefView -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isEditExerciseDefSheetOpen = false,
                        exerciseNameError = null,
                        exerciseBodyRegionError = null,
                        exerciseTargetMusclesError = null
                    ) }
                }
                newExerciseDefinition = null
            }

            is ExerciseLibraryEvent.OnBodyRegionChanged -> {
                newExerciseDefinition = newExerciseDefinition?.copy(
                    bodyRegion = event.value
                )
            }
            is ExerciseLibraryEvent.OnExerciseDescriptionChanged -> {
                newExerciseDefinition = newExerciseDefinition?.copy(
                    description = event.value
                )
            }
            is ExerciseLibraryEvent.OnExerciseNameChanged -> {
                newExerciseDefinition = newExerciseDefinition?.copy(
                    exerciseName = event.value
                )
            }
            is ExerciseLibraryEvent.OnTargetMusclesChanged -> {
                newExerciseDefinition = newExerciseDefinition?.copy(
                    targetMuscles = event.value
                )
            }
            is ExerciseLibraryEvent.SaveOrUpdateExerciseDef -> {
                newExerciseDefinition?.let {exerciseDefinition ->

                    val validationResult =
                        ExerciseDefinitionValidator.validateExerciseDefinition(exerciseDefinition)

                    val errorsList = listOfNotNull(
                        validationResult.nameErrorString,
                        validationResult.bodyRegionErrorString,
                        validationResult.targetMusclesErrorString
                    )

                    if (errorsList.isEmpty()){
                        _state.update {it.copy(
                            selectedExerciseDefinition = newExerciseDefinition,
                            isEditExerciseDefSheetOpen = false,
                            isAddExerciseDefSheetOpen = false
                        )
                        }

                        viewModelScope.launch {
                            exerciseLibraryDataSource.insertOrReplaceExerciseDefinition(exerciseDefinition)
                        }
                    }
                    else {
                        _state.update { it.copy(
                            exerciseNameError = validationResult.nameErrorString,
                            exerciseBodyRegionError = validationResult.bodyRegionErrorString,
                            exerciseTargetMusclesError = validationResult.targetMusclesErrorString
                        ) }
                    }
                }
            }

            ExerciseLibraryEvent.AddNewExerciseDefClicked -> {
                _state.update { it.copy(
                    isAddExerciseDefSheetOpen = true,
                ) }
                newExerciseDefinition = ExerciseDefinition(
                    exerciseDefinitionId = null,
                    exerciseName = "",
                    bodyRegion = "",
                    targetMuscles = "",
                    description = "",
                    isFavorite = 0,
                    dateCreated = null
                )
            }

            ExerciseLibraryEvent.CloseAddExerciseDefClicked -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isAddExerciseDefSheetOpen = false,
                        exerciseNameError = null,
                        exerciseBodyRegionError = null,
                        exerciseTargetMusclesError = null
                    ) }
                    delay(300L) //Animation delay for slide out.
                    newExerciseDefinition = null
                }
            }
        }
    }
}