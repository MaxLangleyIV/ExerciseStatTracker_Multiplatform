package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.data.dummyData.ExerciseRoutineDummyData
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseRecord
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
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = WorkoutState()
        )


    // FOR DEVELOPMENT
    init {
        viewModelScope.launch {
            val definitions = dataSource.getDefinitions().first()
            val currentRoutine = ExerciseRoutineDummyData(definitions).getRoutines()[0]

            val exerciseIdList = currentRoutine.exerciseCSV.split(",")
            val repsList = currentRoutine.repsCSV.split(",")

            val currentExerciseQueue = mutableListOf<ExerciseRecord>()

            for ((index,idString) in exerciseIdList.withIndex()){
                try {
                    val def = definitions[idString.toInt()]
                    val reps = repsList[index].toInt()

                    if (_state.value.exerciseMap[def.exerciseName] == null){
                        _state.value.exerciseMap[def.exerciseName] =
                            mutableListOf(
                                ExerciseRecord(
                                    exerciseName = def.exerciseName,
                                    isCardio = def.isCardio,
                                    isCalisthenic = def.isCalisthenic,
                                    repsCompleted = reps
                                )
                            )

                    }
                    else {
                        _state.value.exerciseMap[def.exerciseName]!!.add(
                            ExerciseRecord(
                                exerciseName = def.exerciseName,
                                isCardio = def.isCardio,
                                isCalisthenic = def.isCalisthenic,
                                repsCompleted = reps
                            )
                        )
                    }

                }
                catch (error : IndexOutOfBoundsException){
                    println("Index passed: $idString")
                }
            }


            _state.update { it.copy(
                routine = currentRoutine,
                exerciseQueue = currentExerciseQueue,
            ) }
        }

    }

}