package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.data.dummyData.ExerciseRoutineDummyData
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val dataSource: ExerciseAppDataSource,
    initialState: WorkoutState = WorkoutState()
): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = _state
        .asStateFlow()
        .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        WorkoutState()
        )

    // FOR DEVELOPMENT
    init {
        viewModelScope.launch {
            val definitions = dataSource.getDefinitions().first()
            val currentRoutine = ExerciseRoutineDummyData(definitions).getRoutines()[0]
            val exerciseIdList = currentRoutine.exerciseCSV.split(",")
            println(exerciseIdList)

            val currentExerciseQueue = mutableListOf<ExerciseDefinition>()

            for (idString in exerciseIdList){
                try {
                    currentExerciseQueue.add(definitions[idString.toInt()])
                }
                catch (error : IndexOutOfBoundsException){
                    println("Index passed: $idString")
                }
            }


            _state.update { it.copy(
                routine = currentRoutine,
                exerciseQueue = currentExerciseQueue
            ) }
        }

    }

}