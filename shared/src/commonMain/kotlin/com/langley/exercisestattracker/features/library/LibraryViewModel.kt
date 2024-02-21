package com.langley.exercisestattracker.features.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseDefinitionValidator
import com.langley.exercisestattracker.features.library.utils.filterDefinitionLibrary
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(

    private val exerciseAppDataSource: ExerciseAppDataSource,
    initialState: LibraryState = LibraryState(),
    initialExerciseDef: ExerciseDefinition = ExerciseDefinition(),

    ): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = combine(
        _state,
        exerciseAppDataSource.getDefinitions(),
    ){
        state, exerciseDefinitions ->

        updateLibraryState(state, exerciseDefinitions)

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LibraryState())

    var definitionForBuilder: ExerciseDefinition by mutableStateOf(initialExerciseDef)
        private set


    private fun updateLibraryState(
        currentState: LibraryState,
        definitionsList: List<ExerciseDefinition>
    ): LibraryState {

        return currentState.copy(
            exerciseDefinitions = filterDefinitionLibrary(
                definitionLibrary = definitionsList,
                filterType = currentState.searchFilterType,
                searchString = currentState.searchString
            )
        )

    }


    fun onEvent(event: LibraryEvent) {
        when (event) {

            is LibraryEvent.DefinitionSelected -> {
                _state.update { it.copy(
                    isSearchDropdownOpen = false,
                    selectedExerciseDefinition = event.exerciseDefinition,
                    isExerciseDetailsSheetOpen = true
                ) }
            }

            is LibraryEvent.SaveDefinition -> {
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceDefinition(event.exerciseDefinition)
                }
            }

            LibraryEvent.CloseDetailsView -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isExerciseDetailsSheetOpen = false
                    ) }
                    delay(300L) //BottomSheet animation delay
                    _state.update { it.copy(
                        selectedExerciseDefinition = null
                    ) }
                }
            }

            is LibraryEvent.EditDefinition -> {
                _state.update { it.copy(
                    isAddExerciseDefSheetOpen = true,
                ) }
                definitionForBuilder = _state.value.selectedExerciseDefinition!!.copy()
            }

            is LibraryEvent.SaveOrUpdateDef -> {
                definitionForBuilder.let { exerciseDefinition ->

                    val validationResult =
                        ExerciseDefinitionValidator.validateExerciseDefinition(exerciseDefinition)

                    val errorsList = listOfNotNull(
                        validationResult.nameErrorString,
                        validationResult.bodyRegionErrorString,
                        validationResult.targetMusclesErrorString
                    )

                    if (errorsList.isEmpty()){
                        _state.update {it.copy(
                            selectedExerciseDefinition = definitionForBuilder,
                            isEditExerciseDefSheetOpen = false,
                            isAddExerciseDefSheetOpen = false
                        )
                        }

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

            LibraryEvent.AddNewDefClicked -> {
                _state.update { it.copy(
                    isAddExerciseDefSheetOpen = true,
                    isEditExerciseDefSheetOpen = false
                ) }
                definitionForBuilder = ExerciseDefinition(
                    exerciseDefinitionId = null,
                    exerciseName = "",
                    bodyRegion = "",
                    targetMuscles = "",
                    description = "",
                    isWeighted = false,
                    hasReps = false,
                    isCardio = false,
                    isCalisthenic = false,
                    isTimed = false,
                    defaultDuration = 0,
                    hasDistance = false,
                    defaultDistance = 0,
                    isFavorite = false,
                    dateCreated = null
                )
            }

            LibraryEvent.CloseAddDefClicked -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isAddExerciseDefSheetOpen = false,
                        exerciseNameError = null,
                        exerciseBodyRegionError = null,
                        exerciseTargetMusclesError = null
                    ) }
                    delay(300L) //Animation delay for slide out.
                    definitionForBuilder = ExerciseDefinition()
                }
            }

            is LibraryEvent.OnSearchStringChanged -> {
                _state.update { it.copy(
                    searchString = event.value
                ) }
            }

            LibraryEvent.ToggleIsSearchDropdownOpen -> {
                _state.update { it.copy(
                    isSearchDropdownOpen = !_state.value.isSearchDropdownOpen
                ) }
            }

            LibraryEvent.DeleteDefinition -> {
                val exerciseDefId = _state.value.selectedExerciseDefinition?.exerciseDefinitionId

                if (exerciseDefId != null){
                    viewModelScope.launch {

                        exerciseAppDataSource.deleteDefinition(exerciseDefId)

                        _state.update { it.copy(
                            isAddExerciseDefSheetOpen = false,
                            isExerciseDetailsSheetOpen = false,
                        ) }

                        delay(350L) //Animation delay for slide out.

                        _state.update { it.copy(
                            selectedExerciseDefinition = null
                        ) }
                        definitionForBuilder = ExerciseDefinition()
                    }
                }
            }

            is LibraryEvent.SetCurrentFilterType -> {
                _state.update {
                    it.copy(
                        searchFilterType = event.filterType
                    )
                }
            }

            LibraryEvent.ClearFilterType -> {
                _state.update {
                    it.copy(
                        searchFilterType = null,
                    )
                }
            }

            is LibraryEvent.ToggleIsFavorite -> {
                definitionForBuilder = _state.value.selectedExerciseDefinition!!.copy(
                    isFavorite = !_state.value.selectedExerciseDefinition!!.isFavorite
                )
                _state.update { it.copy(
                    selectedExerciseDefinition = definitionForBuilder
                ) }
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceDefinition(definitionForBuilder)
                }

            }

            LibraryEvent.ClearSelectedDef -> {
                _state.update { it.copy(
                    selectedExerciseDefinition = null
                ) }
            }
        }
    }
}