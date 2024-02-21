package com.langley.exercisestattracker.features.library.features.exerciseBuilder

import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseDefinitionValidator
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.LibraryViewModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseBuilderViewModel(

    private val exerciseAppDataSource: ExerciseAppDataSource,
    initialState: ExerciseBuilderState = ExerciseBuilderState(),
    private val libraryViewModel: LibraryViewModel,

    ): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = _state
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ExerciseBuilderState()
        )

    private fun updateCurrentDefinition(){
        val newExerciseDef = libraryViewModel.state.value.selectedExerciseDefinition

        if (newExerciseDef != null){
            println("SELECTED DEF: ${newExerciseDef.exerciseName}")
            val bodyRegion = generateBodyRegionObjectsFromString(newExerciseDef.bodyRegion)
            _state.update { it.copy(
                targetMusclesList = newExerciseDef.targetMuscles.split(", "),
                bodyRegion = bodyRegion.first,
                bodyRegionSubGroup = bodyRegion.second,
                newExerciseDefinition = newExerciseDef
            ) }
            println("NEW DEF: ${_state.value.newExerciseDefinition.exerciseName}")
        }
        else {
            _state.update { it.copy(
                newExerciseDefinition = ExerciseDefinition(),
                targetMusclesList = null,
                bodyRegion = null,
                bodyRegionSubGroup = null,
            ) }
        }
    }


    private fun toggleBodyRegion(bodyRegion: BodyRegion): BodyRegion? {
        _state.update { it.copy(
            bodyRegionSubGroup = null
        ) }
        return if (_state.value.bodyRegion == bodyRegion){
            null
        } else {
            bodyRegion
        }
    }

    private fun toggleBodyRegionSubGroup(regionSubGroup: BodyRegionSubGroup): BodyRegionSubGroup? {
        return if (_state.value.bodyRegionSubGroup == regionSubGroup){
            null
        } else {
            regionSubGroup
        }
    }

    private fun generateBodyRegionString(
        bodyRegion: BodyRegion?,
        subGroup: BodyRegionSubGroup?
    ): String {
        when(bodyRegion){
            BodyRegion.Core -> return "Core"

            BodyRegion.Full -> return "Full"

            BodyRegion.Lower -> return "Lower"

            BodyRegion.NotApplicable -> return "Not Applicable"

            BodyRegion.Upper -> {
                return when (subGroup){
                    BodyRegionSubGroup.Arms -> "Arms"
                    BodyRegionSubGroup.Back -> "Back"
                    BodyRegionSubGroup.Chest -> "Chest"
                    BodyRegionSubGroup.NotApplicable -> "Not Applicable"
                    BodyRegionSubGroup.Shoulders -> "Shoulders"
                    null -> "Upper"
                }
            }

            null -> return  "Not Applicable"
        }
    }

    private fun generateBodyRegionObjectsFromString(string: String):
            Pair<BodyRegion, BodyRegionSubGroup?>{

        return when (string) {
            "Core" -> Pair(BodyRegion.Core, BodyRegionSubGroup.NotApplicable)
            "Full" -> Pair(BodyRegion.Full, BodyRegionSubGroup.NotApplicable)
            "Lower" -> Pair(BodyRegion.Lower, BodyRegionSubGroup.NotApplicable)
            "Arms" -> Pair(BodyRegion.Upper, BodyRegionSubGroup.Arms)
            "Back" -> Pair(BodyRegion.Upper, BodyRegionSubGroup.Back)
            "Chest" -> Pair(BodyRegion.Upper, BodyRegionSubGroup.Chest)
            "Shoulders" -> Pair(BodyRegion.Upper, BodyRegionSubGroup.Shoulders)
            else -> Pair(BodyRegion.NotApplicable, null)
        }

    }

    private fun toggleTargetMuscleInList(muscleString: String){

        var currentTargetMuscles = _state.value.targetMusclesList

        if (currentTargetMuscles != null){

            currentTargetMuscles = currentTargetMuscles.toMutableList()
            println(currentTargetMuscles)
            val musclesToRemove = mutableListOf<String>()

            for (muscle in currentTargetMuscles){

                if (muscle.lowercase() == muscleString.lowercase()){
                    musclesToRemove.add(muscle)
                }

            }
            if (musclesToRemove.isEmpty()){
                currentTargetMuscles.add(muscleString)
                currentTargetMuscles = currentTargetMuscles.filter { it != "Not Specified" }
                _state.update { it.copy(
                    targetMusclesList = currentTargetMuscles
                ) }
            }
            else {
                currentTargetMuscles = currentTargetMuscles.filter { !musclesToRemove.contains(it) }

                _state.update { it.copy(
                    targetMusclesList = currentTargetMuscles
                ) }
            }
        }
        else {
            _state.update { it.copy(
                targetMusclesList = listOf(muscleString)
            ) }
        }
    }

    private fun generateTargetMusclesString(): String{

        val currentTargetMuscles = _state.value.targetMusclesList

        var returnString = ""

        if (!currentTargetMuscles.isNullOrEmpty()){

            for ((index, muscle) in currentTargetMuscles.withIndex()){

                returnString += if (index == currentTargetMuscles.lastIndex){
                    muscle
                } else {
                    "$muscle, "
                }

            }

            return returnString

        }

        return "Not Specified"
    }


    fun onEvent(event: ExerciseBuilderEvent){
        when (event) {

            is ExerciseBuilderEvent.OnDescriptionChanged -> {
                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        description = event.value
                    )
                ) }

            }

            is ExerciseBuilderEvent.OnNameChanged -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        exerciseName = event.value
                    )
                ) }

            }

            is ExerciseBuilderEvent.SaveOrUpdateDef -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        bodyRegion = generateBodyRegionString(
                            _state.value.bodyRegion,
                            _state.value.bodyRegionSubGroup
                        ),
                        targetMuscles = generateTargetMusclesString()
                    )
                ) }


                val validationResult =
                    ExerciseDefinitionValidator
                        .validateExerciseDefinition(_state.value.newExerciseDefinition)

                val errorsList = listOfNotNull(
                    validationResult.nameErrorString,
                    validationResult.bodyRegionErrorString,
                    validationResult.targetMusclesErrorString
                )
                println(errorsList)

                if (errorsList.isEmpty()){

                    viewModelScope.launch {
                        exerciseAppDataSource
                            .insertOrReplaceDefinition(_state.value.newExerciseDefinition)
                    }
                    (libraryViewModel::onEvent)(LibraryEvent.CloseAddDefClicked)

                    viewModelScope.launch {
                        delay(300L)
                        _state.update {it.copy(
                            bodyRegion = null,
                            bodyRegionSubGroup = null,
                            targetMusclesList = null
                        )}
                        _state.update { it.copy(
                            newExerciseDefinition = ExerciseDefinition()
                        ) }

                    }
                }
                else {
                    _state.update { it.copy(
                        exerciseNameError = validationResult.nameErrorString,
                        exerciseBodyRegionError = validationResult.bodyRegionErrorString,
                        exerciseTargetMusclesError = validationResult.targetMusclesErrorString
                    ) }
                }
            }

            ExerciseBuilderEvent.CloseAddDef -> {

                viewModelScope.launch {
                    (libraryViewModel::onEvent)(LibraryEvent.CloseAddDefClicked)
                    delay(300L) //Animation delay for slide out.
                    _state.update { it.copy(
                        exerciseNameError = null,
                        exerciseBodyRegionError = null,
                        exerciseTargetMusclesError = null,
                        bodyRegion = null,
                        bodyRegionSubGroup = null,
                        targetMusclesList = null,
                        newExerciseDefinition = ExerciseDefinition()
                    ) }
                }
            }

            ExerciseBuilderEvent.DeleteDefinition -> {

                val exerciseDefId = _state.value.newExerciseDefinition.exerciseDefinitionId

                if (exerciseDefId != null){
                    viewModelScope.launch {

                        exerciseAppDataSource.deleteDefinition(exerciseDefId)

                        (libraryViewModel::onEvent)(LibraryEvent.CloseDetailsView)
                        (libraryViewModel::onEvent)(LibraryEvent.CloseAddDefClicked)

                        delay(350L) //Animation delay for slide out.

                        _state.update { it.copy(
                            newExerciseDefinition = ExerciseDefinition()
                        ) }

                    }
                }
            }

            is ExerciseBuilderEvent.ToggleIsWeighted -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        isWeighted = !_state.value.newExerciseDefinition.isWeighted
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleIsCalisthenics -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        isCalisthenic = !_state.value.newExerciseDefinition.isCalisthenic
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleIsCardio -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        isCardio = !_state.value.newExerciseDefinition.isCardio
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleHasDistance -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        hasDistance = !_state.value.newExerciseDefinition.hasDistance
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleHasReps -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        hasReps = !_state.value.newExerciseDefinition.hasReps
                    )
                ) }
            }

            ExerciseBuilderEvent.ToggleIsTimed -> {

                _state.update { it.copy(
                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
                        isTimed = !_state.value.newExerciseDefinition.isTimed
                    )
                ) }

            }

            is ExerciseBuilderEvent.ToggleBodyRegion -> {
                when (event.bodyRegion){
                    BodyRegion.Core -> {
                        _state.update { it.copy(
                            bodyRegion = toggleBodyRegion(BodyRegion.Core),
                            bodyRegionSubGroup =
                            toggleBodyRegionSubGroup(BodyRegionSubGroup.NotApplicable)
                        ) }
                    }
                    BodyRegion.Lower -> {
                        _state.update { it.copy(
                            bodyRegion = toggleBodyRegion(BodyRegion.Lower),
                            bodyRegionSubGroup =
                            toggleBodyRegionSubGroup(BodyRegionSubGroup.NotApplicable)
                        ) }
                    }
                    BodyRegion.Upper -> {
                        _state.update { it.copy(
                            bodyRegion = toggleBodyRegion(BodyRegion.Upper)
                        ) }
                    }

                    BodyRegion.NotApplicable -> {
                        _state.update { it.copy(
                            bodyRegion = toggleBodyRegion(BodyRegion.NotApplicable),
                            bodyRegionSubGroup =
                            toggleBodyRegionSubGroup(BodyRegionSubGroup.NotApplicable)
                        ) }
                    }

                    BodyRegion.Full -> {
                        _state.update { it.copy(
                            bodyRegion = toggleBodyRegion(BodyRegion.Full),
                            bodyRegionSubGroup = toggleBodyRegionSubGroup(BodyRegionSubGroup.NotApplicable)
                        ) }
                    }
                }
            }

            is ExerciseBuilderEvent.ToggleBodyRegionSubGroup -> {
                _state.update { it.copy(
                    bodyRegionSubGroup = toggleBodyRegionSubGroup(event.subGroup)
                ) }
            }

            is ExerciseBuilderEvent.ToggleTargetMuscle -> {
                toggleTargetMuscleInList(event.value)
            }

            is ExerciseBuilderEvent.InitializeDefinition -> {
                updateCurrentDefinition()
            }
        }
    }


}