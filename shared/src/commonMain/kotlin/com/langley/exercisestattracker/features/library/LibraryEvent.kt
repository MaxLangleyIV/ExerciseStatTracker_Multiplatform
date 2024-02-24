package com.langley.exercisestattracker.features.library

import com.langley.exercisestattracker.core.domain.ExerciseDefinition

sealed interface LibraryEvent {

    // Main Library View Events
    data class DefinitionSelected(val exerciseDefinition: ExerciseDefinition): LibraryEvent
    data class OnSearchStringChanged(val value: String) : LibraryEvent
    data class SetCurrentFilterType(val filterType: ExerciseLibraryFilterType): LibraryEvent
    data class UpdateSelectedDefinition(val definition: ExerciseDefinition): LibraryEvent
    data object ClearFilterType : LibraryEvent
    data object ToggleIsSearchDropdownOpen: LibraryEvent
    data object AddNewDefClicked : LibraryEvent
    data object CloseAddDefClicked : LibraryEvent
    data object ClearSelectedDef : LibraryEvent



    // Details View Events
    data class EditDefinition(val exerciseDefinition: ExerciseDefinition) : LibraryEvent
    data class ToggleIsFavorite(val selectedExerciseDefinition: ExerciseDefinition): LibraryEvent
    data object CloseDetailsView : LibraryEvent


    // For initializing dummy data.
    data class SaveDefinition(val exerciseDefinition: ExerciseDefinition): LibraryEvent



}