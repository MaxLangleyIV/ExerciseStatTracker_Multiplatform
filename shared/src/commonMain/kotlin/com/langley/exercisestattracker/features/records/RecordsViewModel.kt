package com.langley.exercisestattracker.features.records

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecordsViewModel(

    private val exerciseAppDataSource: ExerciseAppDataSource,
    initialState: RecordsState = RecordsState()

    ): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = combine(
        _state,
        exerciseAppDataSource.getRecords(),
    ){
            state, records ->

        updateRecordsState(state, records)

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecordsState())


    private fun updateRecordsState(
        state: RecordsState,
        records: List<ExerciseRecord>
    ):RecordsState {

        return state.copy(
            exerciseRecords = filterRecords(
                records = records,
                filterType = state.searchFilterType,
                searchString = state.searchString
            )
        )

    }

    private fun filterRecords(
        records: List<ExerciseRecord>,
        filterType: RecordsFilterType?,
        searchString: String = ""
    ): List<ExerciseRecord> {
        var filteredRecords: List<ExerciseRecord> =
            when (filterType) {

                is RecordsFilterType.Barbell -> {
                    records.filter {
                        it.exerciseName.contains(
                            "barbell",
                            true
                        )
                    }
                }
                is RecordsFilterType.Calisthenic -> {
                    records.filter {
                        it.isCalisthenic
                    }
                }
                is RecordsFilterType.Dumbbell -> {
                    records.filter {
                        it.exerciseName.contains(
                            "dumbbell",
                            true)
                    }
                }
                is RecordsFilterType.LowerBody -> {
                    records.filter {
                        it.exerciseName.contains(
                            "leg",
                            true)
                    }
                }
                is RecordsFilterType.UpperBody -> {
                    records.filter {
                        it.exerciseName.contains(
                            "barbell",
                            true)
                    }
                }

                is RecordsFilterType.Cardio -> {
                    records.filter {
                        it.isCardio
                    }
                }

                null -> { records }


            }

        if (searchString.isNotBlank()){
            filteredRecords = filteredRecords.filter {
                it.exerciseName.contains(searchString, true)
            }
        }

        return filteredRecords
    }

    fun onEvent(event: RecordsEvent){

        when (event){

            // Main Records View Events
            is RecordsEvent.RecordSelected -> {
                _state.update { it.copy(
                    selectedRecord = event.exerciseRecord,
                    isRecordDetailsSheetOpen = true
                ) }
            }

            is RecordsEvent.OnSearchStringChanged -> {
                _state.update { it.copy(
                    searchString = event.searchString
                ) }

            }

            is RecordsEvent.SetCurrentFilterType -> {
                _state.update { it.copy(
                    searchFilterType = event.filterType
                ) }
            }

            RecordsEvent.ClearFilterType -> {
                _state.update { it.copy(
                    searchFilterType = null
                ) }
            }

            RecordsEvent.ToggleIsSearchDropdownOpen -> {
                TODO()
            }


            // Details View Events
            is RecordsEvent.EditRecord -> {
                TODO() // Might choose to keep records immutable.
            }
            RecordsEvent.CloseDetailsView -> {
                _state.update { it.copy(
                    isRecordDetailsSheetOpen = false,
                    selectedRecord = null
                ) }
            }

            // Edit View Events
            is RecordsEvent.OnNameChanged -> {}
            is RecordsEvent.OnBodyRegionChanged -> {}
            is RecordsEvent.OnDescriptionChanged -> {}
            is RecordsEvent.OnTargetMusclesChanged -> {}
            RecordsEvent.SaveOrUpdateRecord -> {}
            RecordsEvent.DeleteRecord -> {}
            RecordsEvent.CloseEditRecordView -> {}

            //For initializing the database.
            is RecordsEvent.SaveRecord -> {
                viewModelScope.launch {
                    exerciseAppDataSource.insertOrReplaceRecord(event.exerciseRecord)
                }
            }
        }

    }

}