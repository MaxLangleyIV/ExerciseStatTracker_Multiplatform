package com.langley.exercisestattracker.features.library

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule

sealed interface LibraryEvent {

    // Main Library View Events
    data class ExerciseSelected(val exercise: ExerciseDefinition): LibraryEvent
    data class RoutineSelected(val routine: ExerciseRoutine) : LibraryEvent
    data class ScheduleSelected(val schedule: ExerciseSchedule) : LibraryEvent
    data class OnSearchStringChanged(val value: String) : LibraryEvent
    data class SetCurrentFilterType(val filterType: ExerciseLibraryFilterType?): LibraryEvent
    data class UpdateSelectedExercise(val exercise: ExerciseDefinition): LibraryEvent
    data object ClearFilterType : LibraryEvent
    data object ToggleIsSearchDropdownOpen: LibraryEvent
    data object AddNewExercise : LibraryEvent
    data object AddNewRoutine : LibraryEvent
    data object CloseAddDefClicked : LibraryEvent
    data object ClearSelectedExercise : LibraryEvent
    data object ClearSelectedRoutine : LibraryEvent
    data object SelectExercisesTab : LibraryEvent
    data object SelectRoutinesTab : LibraryEvent
    data object SelectSchedulesTab : LibraryEvent




    // Details View Events
    data class EditExercise(val exerciseDefinition: ExerciseDefinition) : LibraryEvent
    data object EditRoutine : LibraryEvent
    data object EditSchedule : LibraryEvent
    data class ToggleFavoriteExercise(val exercise: ExerciseDefinition): LibraryEvent
    data class ToggleFavoriteRoutine(val routine: ExerciseRoutine): LibraryEvent
    data class ToggleFavoriteSchedule(val schedule: ExerciseSchedule): LibraryEvent
    data object CloseDetailsView : LibraryEvent

    // Edit View Events
    data object CloseEditView : LibraryEvent


    // For initializing dummy data.
    data class SaveExercise(val exercise: ExerciseDefinition): LibraryEvent
    data class SaveRoutine(val routine: ExerciseRoutine): LibraryEvent
    data class SaveSchedule(val schedule: ExerciseSchedule): LibraryEvent

}