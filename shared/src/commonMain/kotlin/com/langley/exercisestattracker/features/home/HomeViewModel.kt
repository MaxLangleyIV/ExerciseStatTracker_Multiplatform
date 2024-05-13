package com.langley.exercisestattracker.features.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val dataSource: ExerciseAppDataSource,
    private val dataStore: DataStore<Preferences>,
    initialState: HomeState = HomeState()
): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = initialState
    )



}