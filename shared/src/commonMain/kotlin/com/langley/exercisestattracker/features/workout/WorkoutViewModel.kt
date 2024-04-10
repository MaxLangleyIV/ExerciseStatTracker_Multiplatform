package com.langley.exercisestattracker.features.workout

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.langley.exercisestattracker.core.data.getWorkoutStateFromString
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.features.library.utils.filterDefinitionLibrary
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class WorkoutViewModel(
    private val dataSource: ExerciseAppDataSource,
    val prefDataStore: DataStore<Preferences>,
    initialState: WorkoutState = WorkoutState()

): ViewModel() {

    private var saveJob: Job? = null

    private val _state = MutableStateFlow(initialState)

    val state = _state
        .asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WorkoutState()
        )

    init {
        if (initialState == WorkoutState()){

            viewModelScope.launch {

                _state.update {
                    getWorkoutStateFromString(
                        getWorkoutStateFromPrefs()
                    )
                }

            }

        }
    }

    private suspend fun getWorkoutStateFromPrefs(): String {
        var workoutString = ""

        val workoutStringJob = viewModelScope.launch {
            workoutString = prefDataStore.data.map {
                it[stringPreferencesKey("WORKOUT_STATE")] ?: ""
            }.first()
        }

        workoutStringJob.join()

        return workoutString
    }

    private fun saveWorkoutState() {

        saveJob?.cancel()

        saveJob = viewModelScope.launch {
            delay(1000)

            val stateString = Json.encodeToString(_state.value)

            prefDataStore.edit {
                it[stringPreferencesKey("WORKOUT_STATE")] =
                    stateString
            }
        }

    }

    private fun clearWorkoutState(){

        saveJob?.cancel()

        viewModelScope.launch {
            prefDataStore.edit { it.remove(stringPreferencesKey("WORKOUT_STATE")) }
        }
        _state.update { WorkoutState() }

    }


    fun onEvent(workoutEvent: WorkoutEvent){

        when (workoutEvent){

            WorkoutEvent.OpenExerciseSelector -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        exerciseLibrary = dataSource.getDefinitions().first(),
                        exerciseSelectorVisible = true
                    ) }
                }

            }

            WorkoutEvent.CloseExerciseSelector -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        exerciseSelectorVisible = false,
                    ) }

                    delay(300L)

                    _state.update { it.copy(
                        exerciseLibrary = listOf(),
                        searchFilter = null,
                        searchString = "",
                        selectedExercises = listOf()
                    ) }

                }

            }

            is WorkoutEvent.MarkCompleted -> {

                val mutableList = _state.value.recordsList.toMutableList()

                mutableList[workoutEvent.index] =
                    workoutEvent.record.copy(
                        completed = true,
                        dateCompleted = Clock.System.now().toEpochMilliseconds()
                    )

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

                saveWorkoutState()

            }

            is WorkoutEvent.MarkIncomplete -> {

                val mutableList = _state.value.recordsList.toMutableList()

                mutableList[workoutEvent.index] =
                    workoutEvent.record.copy(
                        completed = false,
                    )

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

                saveWorkoutState()

            }


            WorkoutEvent.SaveWorkout -> {

                for (record in _state.value.recordsList){

                    if (record.completed){

                        viewModelScope.launch {

                            dataSource.insertOrReplaceRecord(record)

                        }

                    }

                }

                clearWorkoutState()

            }

            is WorkoutEvent.DefinitionSelected -> {

                val mutableList = _state.value.selectedExercises.toMutableList()

                if (mutableList.contains(workoutEvent.exerciseDefinition)){
                    mutableList.remove(workoutEvent.exerciseDefinition)
                }
                else {
                    mutableList.add(workoutEvent.exerciseDefinition)
                }

                _state.update { it.copy(
                    selectedExercises = mutableList
                ) }

            }

            is WorkoutEvent.AddToListOfExercises -> {

                val mutableList = _state.value.exerciseList.toMutableList()

                for (def in workoutEvent.exercises){

                    mutableList.add(def)

                }

                mutableList.toSet().toList()

                _state.update { it.copy(
                    exerciseList = mutableList
                ) }

                saveWorkoutState()

            }
            is WorkoutEvent.AddToListOfRecords -> {

                val mutableList = _state.value.recordsList.toMutableList()

                for (record in workoutEvent.records){

                    mutableList.add(record)

                }

                mutableList.toSet().toList()

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

                saveWorkoutState()

            }
            is WorkoutEvent.RemoveFromListOfExercises -> {

                val mutableList = _state.value.exerciseList.toMutableList()

                mutableList.remove(workoutEvent.exercise)

                _state.update { it.copy(
                    exerciseList = mutableList
                ) }

                saveWorkoutState()

            }
            is WorkoutEvent.RemoveFromListOfRecords -> {

                val mutableList = _state.value.recordsList.toMutableList()

                mutableList.removeAt(workoutEvent.index)

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

                saveWorkoutState()

            }

            is WorkoutEvent.SetCurrentFilterType -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        searchFilter = workoutEvent.filter,
                        exerciseLibrary = filterDefinitionLibrary(
                            definitionLibrary = dataSource.getDefinitions().first(),
                            filterType = workoutEvent.filter,
                            searchString = state.value.searchString
                        )
                    ) }
                }

            }

            WorkoutEvent.ClearFilterType -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        searchFilter = null,
                        exerciseLibrary = filterDefinitionLibrary(
                            definitionLibrary = dataSource.getDefinitions().first(),
                            filterType = null,
                            searchString = state.value.searchString
                        )
                    ) }
                }

            }
            is WorkoutEvent.OnSearchStringChanged -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        searchString = workoutEvent.string,
                        exerciseLibrary = filterDefinitionLibrary(
                            definitionLibrary = dataSource.getDefinitions().first(),
                            filterType = _state.value.searchFilter,
                            searchString = workoutEvent.string
                        )
                    ) }
                }

            }

            is WorkoutEvent.UpdateRecordInList -> {

                val mutableList = _state.value.recordsList.toMutableList()

                mutableList[workoutEvent.index] = workoutEvent.newRecord

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

                saveWorkoutState()

            }

            is WorkoutEvent.SelectSet -> {

                _state.update { it.copy(
                    selectedSet = workoutEvent.record
                ) }

            }

            WorkoutEvent.ClearSelectedSet -> {

                _state.update { it.copy(
                    selectedSet = null
                ) }

            }

            is WorkoutEvent.UpdateRepsFromString -> {

                val reps = try {
                    workoutEvent.value.toInt()
                } catch (formatException: NumberFormatException) { 0 }

                val mutableList = _state.value.recordsList.toMutableList()

                mutableList[workoutEvent.index] = mutableList[workoutEvent.index].copy(
                    repsCompleted = reps
                )

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

                saveWorkoutState()

            }

            is WorkoutEvent.UpdateWeightFromString -> {

                val weight = try {
                    workoutEvent.value.toFloat()
                } catch (formatException: NumberFormatException) { 0F }

                val mutableList = _state.value.recordsList.toMutableList()

                mutableList[workoutEvent.index] = mutableList[workoutEvent.index].copy(
                    weightUsed = weight
                )

                _state.update { it.copy(
                    recordsList = mutableList
                ) }

                saveWorkoutState()

            }

            WorkoutEvent.CancelWorkout -> {
                clearWorkoutState()
            }
        }

    }

}