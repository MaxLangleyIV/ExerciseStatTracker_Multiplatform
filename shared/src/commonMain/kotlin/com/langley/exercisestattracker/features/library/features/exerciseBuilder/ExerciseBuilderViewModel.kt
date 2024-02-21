package com.langley.exercisestattracker.features.library.features.exerciseBuilder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    initialExerciseDef: ExerciseDefinition = ExerciseDefinition(),
    private val libraryViewModel: LibraryViewModel,
    private val libraryOnEvent: (LibraryEvent) -> Unit

    ): ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state = _state
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ExerciseBuilderState()
        )

    var newExerciseDef: ExerciseDefinition by mutableStateOf(initialExerciseDef)
        private set


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

        if (currentTargetMuscles != null){

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

            is ExerciseBuilderEvent.OnBodyRegionChanged -> {
                newExerciseDef = newExerciseDef.copy(
                    bodyRegion = event.value
                )
            }

            is ExerciseBuilderEvent.OnDescriptionChanged -> {
                newExerciseDef = newExerciseDef.copy(
                    description = event.value
                )
            }

            is ExerciseBuilderEvent.OnNameChanged -> {
                newExerciseDef = newExerciseDef.copy(
                    exerciseName = event.value
                )
            }

            is ExerciseBuilderEvent.OnTargetMusclesChanged -> {
                newExerciseDef = newExerciseDef.copy(
                    targetMuscles = event.value
                )
            }

            is ExerciseBuilderEvent.SaveOrUpdateDef -> {

                newExerciseDef = newExerciseDef.copy(
                    bodyRegion = generateBodyRegionString(
                        _state.value.bodyRegion,
                        _state.value.bodyRegionSubGroup
                    ),
                    targetMuscles = generateTargetMusclesString()
                )

                val validationResult =
                    ExerciseDefinitionValidator.validateExerciseDefinition(newExerciseDef)

                val errorsList = listOfNotNull(
                    validationResult.nameErrorString,
                    validationResult.bodyRegionErrorString,
                    validationResult.targetMusclesErrorString
                )

                if (errorsList.isEmpty()){

                    viewModelScope.launch {
                        exerciseAppDataSource.insertOrReplaceDefinition(newExerciseDef)
                    }
                    libraryOnEvent(LibraryEvent.CloseAddDefClicked)

                    viewModelScope.launch {
                        delay(300L)
                        _state.update {it.copy(
                            bodyRegion = null,
                            bodyRegionSubGroup = null,
                            targetMusclesList = null
                        )}
                        newExerciseDef = ExerciseDefinition()

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
                _state.update { it.copy(
                    exerciseNameError = null,
                    exerciseBodyRegionError = null,
                    exerciseTargetMusclesError = null,
                    bodyRegion = null,
                    bodyRegionSubGroup = null,
                    targetMusclesList = null
                ) }
                viewModelScope.launch {
                    delay(300L) //Animation delay for slide out.
                    newExerciseDef = ExerciseDefinition()
                }
            }

            ExerciseBuilderEvent.DeleteDefinition -> {
                val exerciseDefId = newExerciseDef.exerciseDefinitionId

                if (exerciseDefId != null){
                    viewModelScope.launch {

                        exerciseAppDataSource.deleteDefinition(exerciseDefId)

                        libraryOnEvent(LibraryEvent.CloseDetailsView)
                        libraryOnEvent(LibraryEvent.CloseAddDefClicked)

                        delay(350L) //Animation delay for slide out.

                        newExerciseDef = ExerciseDefinition()

                    }
                }
            }

            is ExerciseBuilderEvent.ToggleIsWeighted -> {
                newExerciseDef = newExerciseDef.copy(
                    isWeighted = !newExerciseDef.isWeighted
                )
            }

            ExerciseBuilderEvent.ToggleIsCalisthenics -> {
                newExerciseDef = newExerciseDef.copy(
                    isCalisthenic = !newExerciseDef.isCalisthenic
                )
            }

            ExerciseBuilderEvent.ToggleIsCardio -> {
                newExerciseDef = newExerciseDef.copy(
                    isCardio = !newExerciseDef.isCardio
                )
            }

            ExerciseBuilderEvent.ToggleHasDistance -> {
                newExerciseDef = newExerciseDef.copy(
                    hasDistance = !newExerciseDef.hasDistance
                )
            }

            ExerciseBuilderEvent.ToggleHasReps -> {
                newExerciseDef = newExerciseDef.copy(
                    hasReps = !newExerciseDef.hasReps
                )
            }

            ExerciseBuilderEvent.ToggleIsTimed -> {

                newExerciseDef = newExerciseDef.copy(
                    isTimed = !newExerciseDef.isTimed
                )

//                _state.update { it.copy(
//                    newExerciseDefinition = _state.value.newExerciseDefinition.copy(
//                        isTimed = !_state.value.newExerciseDefinition.isTimed
//                    )
//                ) }
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
                newExerciseDef = libraryViewModel.definitionForBuilder
                val bodyRegion = generateBodyRegionObjectsFromString(newExerciseDef.bodyRegion)
                _state.update { it.copy(
                    targetMusclesList = newExerciseDef.targetMuscles.split(", "),
                    bodyRegion = bodyRegion.first,
                    bodyRegionSubGroup = bodyRegion.second
                ) }
            }
        }
    }


}