package com.langley.exercisestattracker.features.exerciseBuilder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseDefinitionValidator
import com.langley.exercisestattracker.features.library.LibraryEvent
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseBuilderViewModel(

    private val exerciseAppDataSource: ExerciseAppDataSource,
    initialState: ExerciseBuilderState = ExerciseBuilderState(),
    newExerciseDef: ExerciseDefinition = ExerciseDefinition(),
    private val libraryOnEvent: (LibraryEvent) -> Unit

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

    private fun toggleBodyRegion(bodyRegion: BodyRegion): BodyRegion? {
        return if (_state.value.bodyRegion == bodyRegion){
            null
        } else {
            bodyRegion
        }
    }

    private fun toggleBodyRegionSubGroup(regionSubGroup: BodyRegionSubGroup): BodyRegionSubGroup? {
        return if (_state.value.bodyRegionSubGroup == regionSubGroup){
            null
        } else {
            regionSubGroup
        }
    }


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

                val validationResult =
                    ExerciseDefinitionValidator.validateExerciseDefinition(newExerciseDef)

                val errorsList = listOfNotNull(
                    validationResult.nameErrorString,
                    validationResult.bodyRegionErrorString,
                    validationResult.targetMusclesErrorString
                )


                if (errorsList.isEmpty()){
                    _state.update {it.copy() }

                    viewModelScope.launch {
                        exerciseAppDataSource.insertOrReplaceDefinition(newExerciseDef)
                    }
                    libraryOnEvent(LibraryEvent.CloseAddDefClicked)
                }
                else {
                    _state.update { it.copy(
                        exerciseNameError = validationResult.nameErrorString,
                        exerciseBodyRegionError = validationResult.bodyRegionErrorString,
                        exerciseTargetMusclesError = validationResult.targetMusclesErrorString
                    ) }
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

            is ExerciseBuilderEvent.ToggleBodyRegion -> {
                when (event.bodyRegion){
                    BodyRegion.Core -> {
                        _state.update { it.copy(
                            coreSelected = !_state.value.coreSelected,
                            bodyRegion = toggleBodyRegion(BodyRegion.Core),
                            bodyRegionSubGroup =
                            toggleBodyRegionSubGroup(BodyRegionSubGroup.NotApplicable)
                        ) }
                    }
                    BodyRegion.Lower -> {
                        _state.update { it.copy(
                            lowerBodySelected = !_state.value.lowerBodySelected,
                            bodyRegion = toggleBodyRegion(BodyRegion.Lower),
                            bodyRegionSubGroup =
                            toggleBodyRegionSubGroup(BodyRegionSubGroup.NotApplicable)
                        ) }
                    }
                    BodyRegion.Upper -> {
                        _state.update { it.copy(
                            upperBodySelected = !_state.value.upperBodySelected,
                            bodyRegion = toggleBodyRegion(BodyRegion.Upper)
                        ) }
                    }

                    BodyRegion.NotApplicable -> {
                        _state.update { it.copy(
                            bodyRegion = toggleBodyRegion(BodyRegion.NotApplicable),
                            bodyRegionSubGroup =
                            toggleBodyRegionSubGroup(BodyRegionSubGroup.NotApplicable)
                        ) }
                    }

                    BodyRegion.Full -> {
                        _state.update { it.copy(
                            fullBodySelected = !_state.value.fullBodySelected,
                            bodyRegion = toggleBodyRegion(BodyRegion.Full),
                            bodyRegionSubGroup = null
                        ) }
                    }
                }
            }
            is ExerciseBuilderEvent.ToggleBodyRegionSubGroup -> {
                _state.update { it.copy(
                    bodyRegionSubGroup = toggleBodyRegionSubGroup(event.subGroup)
                ) }
            }

            is ExerciseBuilderEvent.ToggleTargetMuscle -> {}

        }
    }


}