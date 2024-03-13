package com.langley.exercisestattracker.features.workout

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

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

    private val _definitions = MutableStateFlow(dataSource.getDefinitions())

    val definitions = combine(
        _definitions,
        dataSource.getDefinitions()
    ){
        currentDefinitions, newDefinitions ->

        newDefinitions
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = listOf<ExerciseDefinition>()
    )





    // FOR DEVELOPMENT
//    init {
//        viewModelScope.launch {
//            val definitions = dataSource.getDefinitions().first()
//            val currentRoutine = ExerciseRoutineDummyData(definitions).getRoutines()[0]
//
//            val exerciseIdList = currentRoutine.exerciseCSV.split(",")
//            val repsList = currentRoutine.repsCSV.split(",")
//
//            val currentExerciseQueue = mutableListOf<ExerciseRecord>()
//
//            val newMap = _state.value.exerciseMap.toMutableMap()
//
//            for ((index,idString) in exerciseIdList.withIndex()){
//                try {
//                    val def = definitions[idString.toInt()]
//                    val reps = repsList[index].toInt()
//
//                    if (newMap[def.exerciseName] == null){
//
//                        newMap[def.exerciseName] =
//                            listOf(
//                                ExerciseRecord(
//                                    exerciseName = def.exerciseName,
//                                    isCardio = def.isCardio,
//                                    isCalisthenic = def.isCalisthenic,
//                                    repsCompleted = reps
//                                )
//                            )
//
//                    }
//                    else {
//                        newMap[def.exerciseName] =
//                            newMap[def.exerciseName]!!
//                                .toMutableList() + ExerciseRecord(
//                                exerciseName = def.exerciseName,
//                                isCardio = def.isCardio,
//                                isCalisthenic = def.isCalisthenic,
//                                repsCompleted = reps
//                                )
//
//                    }
//
//                    _state.update { it.copy(
//                        exerciseMap = newMap
//                    ) }
//
//                }
//                catch (error : IndexOutOfBoundsException){
//                    println("Index passed: $idString")
//                }
//            }
//
//
//            _state.update { it.copy(
//                routine = currentRoutine,
//                exerciseQueue = currentExerciseQueue,
//            ) }
//        }
//    }

    fun addToMap(record: ExerciseRecord?){
        if (record == null) { return }

        val newMap = _state.value.exerciseMap.toMutableMap()

        if (newMap[record.exerciseName] == null){
            newMap[record.exerciseName] = listOf(record).toMutableList()
        }
        else {
            newMap[record.exerciseName] = newMap[record.exerciseName]!!.toMutableList() + record
        }

        _state.update { it.copy(
            exerciseMap = newMap
        ) }
    }

    fun onEvent(workoutEvent: WorkoutEvent){

        when (workoutEvent){

            is WorkoutEvent.AddRecord -> {
                val newRecord = workoutEvent.record
                val newMap = _state.value.exerciseMap.toMutableMap()

                if (newMap[newRecord.exerciseName] == null){

                    newMap[newRecord.exerciseName] = listOf(workoutEvent.record)

                }

                else {
                    val newList = newMap[workoutEvent.record.exerciseName]!!.toMutableList()

                    newList.add(workoutEvent.record)

                    newMap[workoutEvent.record.exerciseName] = newList

                }

                _state.update { it.copy(
                    exerciseMap = newMap
                ) }
            }

            is WorkoutEvent.RemoveRecord -> {}
            WorkoutEvent.CloseExerciseSelector -> {

                _state.update { it.copy(
                    exerciseSelectorVisible = false
                ) }

            }
            WorkoutEvent.OpenExerciseSelector -> {

                _state.update { it.copy(
                    exerciseSelectorVisible = true
                ) }

            }

        }

    }

}