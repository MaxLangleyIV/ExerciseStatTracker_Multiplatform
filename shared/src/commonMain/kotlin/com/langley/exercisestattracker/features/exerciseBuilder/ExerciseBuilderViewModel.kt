package com.langley.exercisestattracker.features.exerciseBuilder

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseDefinitionValidator
import com.langley.exercisestattracker.core.utils.toggleStringInList
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
    private val libraryOnEvent: (LibraryEvent) -> Unit,


    ): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = _state
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ExerciseBuilderState()
        )

    private fun initialize(definition: ExerciseDefinition){
        println("SELECTED DEF: ${definition.exerciseName}")

        _state.update { it.copy(
            musclesList = definition.targetMuscles.split(", "),
            primaryTargetList = definition.bodyRegion.split(", "),
            newExerciseDefinition = definition
        ) }

        println("NEW DEF: ${_state.value.newExerciseDefinition.exerciseName}")

    }


    fun onEvent(event: ExerciseBuilderEvent){
        when (event) {

            is ExerciseBuilderEvent.OnDescriptionChanged -> {
                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        description = event.value
                    )
                ) }

            }

            is ExerciseBuilderEvent.OnNameChanged -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        exerciseName = event.value
                    )
                ) }

            }

            is ExerciseBuilderEvent.SaveOrUpdateDef -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(

                        bodyRegion =
                        _state.value
                            .primaryTargetList?.joinToString(", ")?: "Not Specified",
                        targetMuscles =
                        _state.value
                            .musclesList?.joinToString(", ")?: "Not Specified"
                    )
                ) }


                val validationResult =
                    ExerciseDefinitionValidator
                        .validateExerciseDefinition(_state.value.newExerciseDefinition)

                val errorsList = listOfNotNull(
                    validationResult.nameErrorString,
                    validationResult.bodyRegionErrorString,
                    validationResult.targetMusclesErrorString
                )
                println(errorsList)

                if (errorsList.isEmpty()){

                    viewModelScope.launch {
                        exerciseAppDataSource
                            .insertOrReplaceDefinition(_state.value.newExerciseDefinition)
                        if (_state.value.initialized){
                            libraryOnEvent(
                                LibraryEvent.UpdateSelectedDefinition(
                                    _state.value.newExerciseDefinition
                                )
                            )
                        }
                        else {
                            libraryOnEvent(LibraryEvent.CloseDetailsView)
                        }
                        libraryOnEvent(LibraryEvent.CloseAddDefClicked)
                        delay(300L)

                        _state.update {it.copy(
                            initialized = false,
                            primaryTargetList = null,
                            musclesList = null,
                            newExerciseDefinition = ExerciseDefinition(),
                            exerciseNameError = null,
                            exerciseBodyRegionError = null,
                            exerciseTargetMusclesError = null
                        )}
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

            ExerciseBuilderEvent.CloseAddDef -> {

                viewModelScope.launch {
                    libraryOnEvent(LibraryEvent.CloseAddDefClicked)
                    delay(300L) //Animation delay for slide out.
                    _state.update { it.copy(
                        initialized = false,
                        exerciseNameError = null,
                        exerciseBodyRegionError = null,
                        exerciseTargetMusclesError = null,
                        primaryTargetList = null,
                        musclesList = null,
                        newExerciseDefinition = ExerciseDefinition()
                    ) }
                }
            }

            ExerciseBuilderEvent.DeleteDefinition -> {

                val exerciseDefId = _state.value.newExerciseDefinition.exerciseDefinitionId

                if (exerciseDefId != null){
                    libraryOnEvent(LibraryEvent.CloseDetailsView)
                    libraryOnEvent(LibraryEvent.CloseAddDefClicked)

                    viewModelScope.launch {
                        exerciseAppDataSource.deleteDefinition(exerciseDefId)
                        delay(350L) //Animation delay for slide out.
                        _state.update { it.copy(
                            initialized = false,
                            newExerciseDefinition = ExerciseDefinition()
                        ) }

                    }
                }
            }

            is ExerciseBuilderEvent.ToggleIsWeighted -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        isWeighted = !_state.value.newExerciseDefinition.isWeighted
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleIsCalisthenics -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        isCalisthenic = !_state.value.newExerciseDefinition.isCalisthenic
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleIsCardio -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        isCardio = !_state.value.newExerciseDefinition.isCardio
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleHasDistance -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        hasDistance = !_state.value.newExerciseDefinition.hasDistance
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleHasReps -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        hasReps = !_state.value.newExerciseDefinition.hasReps
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleIsTimed -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        isTimed = !_state.value.newExerciseDefinition.isTimed
                    )
                ) }

            }


            is ExerciseBuilderEvent.ToggleBodyRegion -> {

                _state.update { it.copy(
                    primaryTargetList =
                    toggleStringInList(event.bodyRegion,_state.value.primaryTargetList)
                ) }
            }


            is ExerciseBuilderEvent.ToggleTargetMuscle -> {

                _state.update { it.copy(
                    musclesList =
                    toggleStringInList(event.value,_state.value.musclesList)
                ) }
            }

            is ExerciseBuilderEvent.InitializeDefinition -> {
                initialize(event.value)
            }

            ExerciseBuilderEvent.DeclareAsInitialized -> {
                _state.update { it.copy(
                    initialized = true
                ) }
            }
        }
    }
}