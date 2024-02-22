package com.langley.exercisestattracker.features.records

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord

sealed interface RecordsEvent {


    // Main Records View Events
    data class RecordSelected(val exerciseRecord: ExerciseRecord): RecordsEvent
    data class OnSearchStringChanged(val searchString: String) : RecordsEvent
    data class SetCurrentFilterType(val filterType: RecordsFilterType): RecordsEvent
    data object ToggleIsSearchDropdownOpen: RecordsEvent
    data object ClearFilterType : RecordsEvent


    // Details View Events
    data class EditRecord(val exerciseDefinition: ExerciseDefinition) : RecordsEvent
    data object CloseDetailsView : RecordsEvent


    // Edit View Events
    data class OnNameChanged(val value: String) : RecordsEvent
    data class OnBodyRegionChanged(val value: String) : RecordsEvent
    data class OnTargetMusclesChanged(val value: String) : RecordsEvent
    data class OnDescriptionChanged(val value: String) : RecordsEvent
    data object CloseEditRecordView : RecordsEvent
    data object SaveOrUpdateRecord : RecordsEvent
    data object DeleteRecord : RecordsEvent


    // For initializing dummy data.
    data class SaveRecord(val exerciseRecord: ExerciseRecord): RecordsEvent











}