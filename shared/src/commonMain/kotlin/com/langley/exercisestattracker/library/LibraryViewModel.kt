package com.langley.exercisestattracker.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinitionValidator
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
    newExerciseDef: ExerciseDefinition? = null,

    ): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = combine(
        _state,
        exerciseAppDataSource.getDefinitions(),
    ){
        state, exerciseDefinitions ->

        updateLibraryState(state, exerciseDefinitions)

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LibraryState())

    var newExerciseDefinition: ExerciseDefinition? by mutableStateOf(newExerciseDef)
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

    private fun filterDefinitionLibrary(

        definitionLibrary: List<ExerciseDefinition>,
        filterType: ExerciseLibraryFilterType?,
        searchString: String = ""

    ):List<ExerciseDefinition> {

        var filteredLibrary: List<ExerciseDefinition> =
            when (filterType) {

            is ExerciseLibraryFilterType.Barbell -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "barbell",
                        true
                    )
                }
            }
            is ExerciseLibraryFilterType.Calisthenic -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "body",
                        true)
                }
            }
            is ExerciseLibraryFilterType.Dumbbell -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "dumbbell",
                        true)
                }
            }
            is ExerciseLibraryFilterType.Favorite -> {
                definitionLibrary.filter {
                    it.isFavorite?.toInt() == 1
                }
            }
            is ExerciseLibraryFilterType.LowerBody -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "leg",
                        true)
                }
            }
            is ExerciseLibraryFilterType.UpperBody -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "barbell",
                        true)
                }
            }
            null -> {
                definitionLibrary
            }
        }

        if (searchString.isNotBlank()){
            filteredLibrary = filteredLibrary.filter {
                it.exerciseName.contains(searchString, true)
            }
        }

        return filteredLibrary
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
                    isEditExerciseDefSheetOpen = true,
                ) }
                newExerciseDefinition = _state.value.selectedExerciseDefinition?.copy()
            }

            LibraryEvent.CloseEditDefView -> {
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

            is LibraryEvent.OnBodyRegionChanged -> {
                newExerciseDefinition = newExerciseDefinition?.copy(
                    bodyRegion = event.value
                )
            }

            is LibraryEvent.OnDescriptionChanged -> {
                newExerciseDefinition = newExerciseDefinition?.copy(
                    description = event.value
                )
            }

            is LibraryEvent.OnNameChanged -> {
                newExerciseDefinition = newExerciseDefinition?.copy(
                    exerciseName = event.value
                )
            }

            is LibraryEvent.OnTargetMusclesChanged -> {
                newExerciseDefinition = newExerciseDefinition?.copy(
                    targetMuscles = event.value
                )
            }

            is LibraryEvent.SaveOrUpdateDef -> {
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
                newExerciseDefinition = ExerciseDefinition(
                    exerciseDefinitionId = null,
                    exerciseName = "",
                    bodyRegion = "",
                    targetMuscles = "",
                    description = "",
                    isCalisthenic = 0,
                    isTimed = 0,
                    duration = 0,
                    isFavorite = 0,
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
                    newExerciseDefinition = null
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
                            isEditExerciseDefSheetOpen = false,
                            isExerciseDetailsSheetOpen = false,
                        ) }

                        delay(300L) //Animation delay for slide out.

                        _state.update { it.copy(
                            selectedExerciseDefinition = null
                        ) }
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
        }
    }
}