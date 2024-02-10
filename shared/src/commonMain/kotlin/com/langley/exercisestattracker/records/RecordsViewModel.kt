package com.langley.exercisestattracker.records

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.library.ExerciseLibraryFilterType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

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

    private var selectedRecord: ExerciseRecord? = null

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
                        it.exerciseName.contains(
                            "body",
                            true)
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
                null -> {
                    records                }
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
                selectedRecord = event.exerciseRecord
            }
            is RecordsEvent.OnSearchStringChanged -> return
            is RecordsEvent.SetCurrentFilterType -> return
            RecordsEvent.ToggleIsSearchDropdownOpen -> return
            RecordsEvent.ClearFilterType -> return

            // Details View Events
            is RecordsEvent.EditRecord -> return
            RecordsEvent.CloseDetailsView -> return

            // Edit View Events


            is RecordsEvent.OnNameChanged -> TODO()
            is RecordsEvent.OnBodyRegionChanged -> TODO()
            is RecordsEvent.OnDescriptionChanged -> TODO()
            is RecordsEvent.OnTargetMusclesChanged -> TODO()
            RecordsEvent.SaveOrUpdateRecord -> TODO()
            RecordsEvent.DeleteRecord -> TODO()
            RecordsEvent.CloseEditRecordView -> TODO()

        }

    }

}