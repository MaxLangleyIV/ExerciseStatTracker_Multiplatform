package com.langley.exercisestattracker.features.library.presentation.components

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class DefDetailsViewModel(private val definition: ExerciseDefinition) : ViewModel(){

    private val _definitionFlow = MutableStateFlow(definition)
    val definitionFlow =
        _definitionFlow.asStateFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ExerciseBuilderState()
        )


}