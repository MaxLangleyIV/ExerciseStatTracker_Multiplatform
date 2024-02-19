package com.langley.exercisestattracker.library.features.exerciseBuilder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseDefinitionValidator
import com.langley.exercisestattracker.library.LibraryState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseBuilderViewModel(

    private val exerciseAppDataSource: ExerciseAppDataSource,
    initialState: ExerciseBuilderState = ExerciseBuilderState(),
    newExerciseDef: ExerciseDefinition = ExerciseDefinition(),

    ): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = _state
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ExerciseBuilderState()
        )

    var newExerciseDef: ExerciseDefinition by mutableStateOf(newExerciseDef)
        private set


    fun onEvent(event: ExerciseBuilderEvent){
        when (event) {


            is ExerciseBuilderEvent.OnBodyRegionChanged -> {
                newExerciseDef = newExerciseDef.copy(
                    bodyRegion = event.value
                )
            }

            is ExerciseBuilderEvent.OnDescriptionChanged -> {
                newExerciseDef = newExerciseDef.copy(
                    description = event.value
                )
            }

            is ExerciseBuilderEvent.OnNameChanged -> {
                newExerciseDef = newExerciseDef.copy(
                    exerciseName = event.value
                )
            }

            is ExerciseBuilderEvent.OnTargetMusclesChanged -> {
                newExerciseDef = newExerciseDef.copy(
                    targetMuscles = event.value
                )
            }

            is ExerciseBuilderEvent.SaveOrUpdateDef -> {
                newExerciseDef.let {exerciseDefinition ->

                    val validationResult =
                        ExerciseDefinitionValidator.validateExerciseDefinition(exerciseDefinition)

                    val errorsList = listOfNotNull(
                        validationResult.nameErrorString,
                        validationResult.bodyRegionErrorString,
                        validationResult.targetMusclesErrorString
                    )

                    if (errorsList.isEmpty()){
                        _state.update {it.copy() }

                        viewModelScope.launch {
                            exerciseAppDataSource.insertOrReplaceDefinition(exerciseDefinition)
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


            ExerciseBuilderEvent.CloseAddDefClicked -> {
                viewModelScope.launch {

                    delay(300L) //Animation delay for slide out.
                    _state.update { it.copy(
                        exerciseNameError = null,
                        exerciseBodyRegionError = null,
                        exerciseTargetMusclesError = null
                    ) }
                    newExerciseDef = ExerciseDefinition()
                }
            }


            ExerciseBuilderEvent.DeleteDefinition -> {
                val exerciseDefId = newExerciseDef.exerciseDefinitionId

                if (exerciseDefId != null){
                    viewModelScope.launch {

                        exerciseAppDataSource.deleteDefinition(exerciseDefId)

                        _state.update { it.copy(
//                            isEditExerciseDefSheetOpen = false,
//                            isExerciseDetailsSheetOpen = false,
                        ) }

                        delay(350L) //Animation delay for slide out.

                        _state.update { it.copy(
//                            selectedExerciseDefinition = null
                        ) }
                    }
                }
            }


            is ExerciseBuilderEvent.ToggleIsWeighted -> {
                newExerciseDef = newExerciseDef.copy(
                    isWeighted = !newExerciseDef.isWeighted
                )
            }

            ExerciseBuilderEvent.ToggleIsCalisthenics -> {
                newExerciseDef = newExerciseDef.copy(
                    isCalisthenic = !newExerciseDef.isCalisthenic
                )
            }
            ExerciseBuilderEvent.ToggleIsCardio -> {
                newExerciseDef = newExerciseDef.copy(
                    isCardio = !newExerciseDef.isCardio
                )
            }
            ExerciseBuilderEvent.ToggleHasDistance -> {
                newExerciseDef = newExerciseDef.copy(
                    hasDistance = !newExerciseDef.hasDistance
                )
            }
            ExerciseBuilderEvent.ToggleHasReps -> {
                newExerciseDef = newExerciseDef.copy(
                    hasReps = !newExerciseDef.hasReps
                )
            }
            ExerciseBuilderEvent.ToggleIsTimed -> {
                newExerciseDef = newExerciseDef.copy(
                    isTimed = !newExerciseDef.isTimed
                )
            }

            is ExerciseBuilderEvent.ExerciseTypeSelected -> {}
            is ExerciseBuilderEvent.ToggleBodyRegion -> {
                when (event.bodyRegion){
                    BodyRegion.Core -> {
                        _state.update { it.copy(
                            coreSelected = !_state.value.coreSelected
                        ) }
                    }
                    BodyRegion.Lower -> {
                        _state.update { it.copy(
                            lowerBodySelected = !_state.value.lowerBodySelected
                        ) }
                    }
                    BodyRegion.Upper -> {
                        _state.update { it.copy(
                            upperBodySelected = !_state.value.upperBodySelected
                        ) }
                    }

                    BodyRegion.NotApplicable -> {
                        _state.update { it.copy(
                            notApplicableSelected = !_state.value.notApplicableSelected
                        ) }
                    }
                }
            }
            is ExerciseBuilderEvent.ToggleTargetMuscle -> {}
        }
    }


}