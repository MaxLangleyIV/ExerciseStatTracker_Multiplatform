package com.langley.exercisestattracker.features.library

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.domain.ExerciseSchedule

sealed interface LibraryEvent {

    // Main Library View Events
    data class DefinitionSelected(val exerciseDefinition: ExerciseDefinition): LibraryEvent
    data class RoutineSelected(val routine: ExerciseRoutine) : LibraryEvent
    data class ScheduleSelected(val schedule: ExerciseSchedule) : LibraryEvent
    data class OnSearchStringChanged(val value: String) : LibraryEvent
    data class SetCurrentFilterType(val filterType: ExerciseLibraryFilterType): LibraryEvent
    data class UpdateSelectedDefinition(val definition: ExerciseDefinition): LibraryEvent
    data object ClearFilterType : LibraryEvent
    data object ToggleIsSearchDropdownOpen: LibraryEvent
    data object AddNewDefClicked : LibraryEvent
    data object CloseAddDefClicked : LibraryEvent
    data object ClearSelectedDef : LibraryEvent
    data object SelectDefinitionsTab : LibraryEvent
    data object SelectRoutinesTab : LibraryEvent
    data object SelectSchedulesTab : LibraryEvent



    // Details View Events
    data class EditDefinition(val exerciseDefinition: ExerciseDefinition) : LibraryEvent
    data class ToggleFavoriteDef(val definition: ExerciseDefinition): LibraryEvent
    data class ToggleFavoriteRoutine(val routine: ExerciseRoutine): LibraryEvent
    data class ToggleFavoriteSchedule(val schedule: ExerciseSchedule): LibraryEvent
    data object CloseDetailsView : LibraryEvent


    // For initializing dummy data.
    data class SaveDefinition(val definition: ExerciseDefinition): LibraryEvent
    data class SaveRoutine(val routine: ExerciseRoutine): LibraryEvent
    data class SaveSchedule(val schedule: ExerciseSchedule): LibraryEvent

}