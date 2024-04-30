package com.langley.exercisestattracker.features.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
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
    initialRoutine: ExerciseRoutine = ExerciseRoutine(),
    initialSchedule: ExerciseSchedule = ExerciseSchedule(),
): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = combine(
        _state,
        exerciseAppDataSource.getDefinitions(),
        exerciseAppDataSource.getRoutines(),
        exerciseAppDataSource.getSchedules()
    ){
        state, definitions, routines, schedules ->

        updateLibraryState(state, definitions, routines, schedules)

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LibraryState())

    var definitionForBuilder: ExerciseDefinition by mutableStateOf(initialExerciseDef)
        private set
    var routineForBuilder: ExerciseRoutine by mutableStateOf(initialRoutine)
        private set
    var scheduleForBuilder: ExerciseSchedule by mutableStateOf(initialSchedule)
        private set


    private fun updateLibraryState(
        currentState: LibraryState,
        definitionsList: List<ExerciseDefinition>,
        routinesList: List<ExerciseRoutine>,
        schedulesList: List<ExerciseSchedule>,
    ): LibraryState {

        return currentState.copy(

            exercises =
            filterDefinitionLibrary(
                definitionLibrary = definitionsList,
                filterType = currentState.searchFilterType,
                searchString = currentState.searchString
            ),
            routines = routinesList,
            schedules = schedulesList

        )

    }


    fun onEvent(event: LibraryEvent) {
        when (event) {

            is LibraryEvent.ExerciseSelected -> {
                _state.update { it.copy(
                    isSearchDropdownOpen = false,
                    selectedExercise = event.exercise,
                    isExerciseDetailsSheetOpen = true,
                    isRoutineDetailsSheetOpen = false,
                    isScheduleDetailsSheetOpen = false
                ) }
            }

            is LibraryEvent.RoutineSelected -> {
                _state.update { it.copy(
                    isSearchDropdownOpen = false,
                    selectedRoutine = event.routine,
                    isExerciseDetailsSheetOpen = false,
                    isRoutineDetailsSheetOpen = true,
                    isScheduleDetailsSheetOpen = false
                ) }
            }

            is LibraryEvent.ScheduleSelected -> {
                _state.update { it.copy(
                    isSearchDropdownOpen = false,
                    selectedSchedule = event.schedule,
                    isRoutineDetailsSheetOpen = false,
                    isExerciseDetailsSheetOpen = false,
                    isScheduleDetailsSheetOpen = true
                ) }
            }

            LibraryEvent.CloseDetailsView -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isExerciseDetailsSheetOpen = false,
                        isRoutineDetailsSheetOpen = false,
                        isScheduleDetailsSheetOpen = false
                    ) }
                    delay(300L) //BottomSheet animation delay
                    _state.update { it.copy(
                        selectedExercise = null,
                        selectedRoutine = null,
                        selectedSchedule = null
                    ) }
                }
            }

            LibraryEvent.CloseEditView -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isEditExerciseSheetOpen = false,
                        isEditRoutineSheetOpen = false,
                        isEditScheduleSheetOpen = false
                    ) }
                }
            }

            LibraryEvent.CloseAddView -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isAddExerciseSheetOpen = false,
                        isAddRoutineSheetOpen = false,
                        isAddScheduleSheetOpen = false,
                        exerciseNameError = null,
                        exerciseBodyRegionError = null,
                        exerciseTargetMusclesError = null
                    ) }
                    delay(300L) //Animation delay for slide out.
                    definitionForBuilder = ExerciseDefinition()
                }
            }

            is LibraryEvent.EditExercise -> {
                _state.update { it.copy(
                    isAddExerciseSheetOpen = true,
                ) }
                definitionForBuilder = _state.value.selectedExercise!!.copy()
            }

            LibraryEvent.EditRoutine -> {
                _state.update { it.copy(
                    isEditRoutineSheetOpen = true,
                ) }
            }

            LibraryEvent.EditSchedule -> TODO()

            LibraryEvent.AddNewExercise -> {
                _state.update { it.copy(
                    isAddExerciseSheetOpen = true,
                    isEditExerciseSheetOpen = false
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
                        isAddExerciseSheetOpen = false,
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

            is LibraryEvent.ToggleFavoriteExercise -> {
                definitionForBuilder = _state.value.selectedExercise!!.copy(
                    isFavorite = !_state.value.selectedExercise!!.isFavorite
                )
                _state.update { it.copy(
                    selectedExercise = definitionForBuilder
                ) }
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceDefinition(definitionForBuilder)
                }

            }

            is LibraryEvent.ToggleFavoriteRoutine -> {
                routineForBuilder = _state.value.selectedRoutine!!.copy(
                    isFavorite = !_state.value.selectedRoutine!!.isFavorite
                )
                _state.update { it.copy(
                    selectedRoutine = routineForBuilder
                ) }
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceRoutine(routineForBuilder)
                }
            }

            is LibraryEvent.ToggleFavoriteSchedule -> {
                scheduleForBuilder = _state.value.selectedSchedule!!.copy(
                    isFavorite = !_state.value.selectedSchedule!!.isFavorite
                )
                _state.update { it.copy(
                    selectedSchedule = scheduleForBuilder
                ) }
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceSchedule(scheduleForBuilder)
                }
            }

            LibraryEvent.ClearSelectedExercise -> {
                _state.update { it.copy(
                    selectedExercise = null
                ) }
            }

            is LibraryEvent.UpdateSelectedExercise -> {

                definitionForBuilder = event.exercise
                _state.update { it.copy(
                    selectedExercise = event.exercise,
                    isExerciseDetailsSheetOpen = true
                ) }
            }

            LibraryEvent.SelectExercisesTab -> {
                _state.update { it.copy(
                    isShowingExercises = true,
                    isShowingRoutines = false,
                    isShowingSchedules = false
                ) }
            }

            LibraryEvent.SelectRoutinesTab -> {
                _state.update { it.copy(
                    isShowingExercises = false,
                    isShowingRoutines = true,
                    isShowingSchedules = false
                ) }
            }

            LibraryEvent.SelectSchedulesTab -> {
                _state.update { it.copy(
                    isShowingExercises = false,
                    isShowingRoutines = false,
                    isShowingSchedules = true
                ) }
            }

            is LibraryEvent.SaveExercise -> {
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceDefinition(event.exercise)
                }
            }

            is LibraryEvent.SaveRoutine -> {
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceRoutine(event.routine)
                }
            }

            is LibraryEvent.SaveSchedule -> {
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceSchedule(event.schedule)
                }
            }

            LibraryEvent.AddNewRoutine -> {
                _state.update { it.copy(
                    isAddRoutineSheetOpen = true
                ) }
            }
            LibraryEvent.ClearSelectedRoutine -> {
                _state.update { it.copy(
                    selectedRoutine = null
                ) }
            }
        }
    }
}