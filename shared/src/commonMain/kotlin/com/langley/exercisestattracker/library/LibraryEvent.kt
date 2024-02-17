package com.langley.exercisestattracker.library

import com.langley.exercisestattracker.core.domain.ExerciseDefinition

sealed interface LibraryEvent {

    // Main Library View Events
    data class DefinitionSelected(val exerciseDefinition: ExerciseDefinition): LibraryEvent
    data class OnSearchStringChanged(val value: String) : LibraryEvent
    data class SetCurrentFilterType(val filterType: ExerciseLibraryFilterType): LibraryEvent
    data object ClearFilterType : LibraryEvent
    data object ToggleIsSearchDropdownOpen: LibraryEvent


    // Details View Events
    data class EditDefinition(val exerciseDefinition: ExerciseDefinition) : LibraryEvent
    data object CloseDetailsView : LibraryEvent


    // Edit / Add View Events
    data class ToggleIsFavorite(val selectedExerciseDefinition: ExerciseDefinition): LibraryEvent
    data class OnNameChanged(val value: String) : LibraryEvent
    data class OnBodyRegionChanged(val value: String) : LibraryEvent
    data class OnTargetMusclesChanged(val value: String) : LibraryEvent
    data class OnDescriptionChanged(val value: String) : LibraryEvent
    data object SaveOrUpdateDef : LibraryEvent
    data object CloseEditDefView : LibraryEvent
    data object AddNewDefClicked : LibraryEvent
    data object CloseAddDefClicked : LibraryEvent
    data object DeleteDefinition : LibraryEvent


    // For initializing dummy data.
    data class SaveDefinition(val exerciseDefinition: ExerciseDefinition): LibraryEvent


}