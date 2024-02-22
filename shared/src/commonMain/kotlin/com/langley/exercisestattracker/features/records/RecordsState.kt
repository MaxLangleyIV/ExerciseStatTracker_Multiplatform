package com.langley.exercisestattracker.features.records

import com.langley.exercisestattracker.core.domain.ExerciseRecord

data class RecordsState(

    val exerciseRecords: List<ExerciseRecord> = emptyList(),
    val selectedRecord: ExerciseRecord? = null,

    //Search state
    val isSearchDropdownOpen: Boolean = false,
    val searchString: String = "",
    val previousSearches: List<String>? = null,
    val searchFilterType: RecordsFilterType? = null,
//
//    //UI visibility flags.
    val isRecordDetailsSheetOpen: Boolean = false,
    val isEditRecordDetailsSheetOpen: Boolean = false,

)

sealed interface RecordsFilterType {
    data class UpperBody(val name: String = "upperBody") : RecordsFilterType
    data class LowerBody(val name: String = "lowerBody") : RecordsFilterType
    data class Barbell(val name: String = "barbell"): RecordsFilterType
    data class Dumbbell(val name: String = "dumbbell") : RecordsFilterType
    data class Calisthenic(val name: String = "calisthenic") : RecordsFilterType

}