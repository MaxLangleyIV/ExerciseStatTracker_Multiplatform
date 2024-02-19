package com.langley.exercisestattracker.library.features.exerciseBuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.presentation.composables.ErrorDisplayingTextField
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderState

@Composable
fun StrengthExerciseBuilderView(
    state: ExerciseBuilderState,
    newExerciseDefinition: ExerciseDefinition,
    onEvent: (ExerciseBuilderEvent) -> Unit,
    upperBodySelectedState: MutableState<Boolean>,
    lowerBodySelectedState: MutableState<Boolean>,
    coreBodySelectedState: MutableState<Boolean>
){
    val tagsSectionIsVisible = remember { mutableStateOf(false) }



    Column (
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Spacer(Modifier.height(8.dp))

        // Name Input
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            ErrorDisplayingTextField(
                value = newExerciseDefinition.exerciseName,
                placeholder = "Exercise Name",
                error = state.exerciseNameError,
                onValueChanged = {
                    onEvent(ExerciseBuilderEvent.OnNameChanged(it))
                },
            )

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.tertiaryContainer)

            ){}
        }

        Spacer(
            Modifier.height(8.dp)
        )

        // Body Region Column
        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                text = "Body Region:",
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SelectableTextBoxWithToggle(
                    text = "Upper",
                    isClicked = upperBodySelectedState.value,
                    valueToToggle = upperBodySelectedState
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithToggle(
                    text = "Lower",
                    isClicked = lowerBodySelectedState.value,
                    valueToToggle = lowerBodySelectedState
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithToggle(
                    text = "Core",
                    isClicked = coreBodySelectedState.value,
                    valueToToggle = coreBodySelectedState
                )
            }

            Spacer(Modifier.height(8.dp))

            if (upperBodySelectedState.value){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    SelectableTextBoxWithEvent(
                        text = "Arms",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("arms"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnBodyRegionChanged("arms"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Back",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("back"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnBodyRegionChanged("back"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Chest",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("chest"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnBodyRegionChanged("chest"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Shoulders",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("legs"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnBodyRegionChanged("legs"),
                    )

                }
            }

            Spacer(Modifier.height(8.dp))

            if (lowerBodySelectedState.value){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    SelectableTextBoxWithEvent(
                        text = "Arms",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("arms"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnBodyRegionChanged("arms"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Back",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("back"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnBodyRegionChanged("back"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Chest",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("chest"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnBodyRegionChanged("chest"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Shoulders",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("legs"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnBodyRegionChanged("legs"),
                    )

                }
            }

        }

        Spacer(Modifier.height(8.dp))

        // Target Muscles Row
        if (newExerciseDefinition.bodyRegion.isNotBlank()){
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    text = "Target Muscles:",
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    SelectableTextBoxWithEvent(
                        text = "Biceps",
                        isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("biceps"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnTargetMusclesChanged("biceps"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Pectoralis",
                        isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("pectoralis"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnTargetMusclesChanged("pectoralis"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Deltoids",
                        isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("deltoid"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnTargetMusclesChanged("deltoids"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Quadriceps",
                        isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("quadriceps"),
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.OnTargetMusclesChanged("quadriceps"),
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Metrics Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(4.dp)
        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                text = "Metrics:"
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                SelectableTextBoxWithEvent(
                    text = "Reps",
                    isClicked = newExerciseDefinition.hasReps,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleHasReps,
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Weight",
                    isClicked = newExerciseDefinition.isWeighted,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleIsWeighted,
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Time",
                    isClicked = newExerciseDefinition.isTimed,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleIsTimed,
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Distance",
                    isClicked = newExerciseDefinition.hasDistance,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleHasDistance,
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Tags Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(4.dp)
        ){

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = "Extra Tags:"
                )

                IconButton(
                    onClick = {
                        tagsSectionIsVisible.value = !tagsSectionIsVisible.value
                    }
                ) {
                    Icon(
                        imageVector =
                        if (tagsSectionIsVisible.value){ Icons.Outlined.ArrowDropUp }
                        else{ Icons.Outlined.ArrowDropDown },
                        contentDescription = "Expand Tags Section"
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            if (tagsSectionIsVisible.value){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    SelectableTextBoxWithEvent(
                        text = "Body Weight",
                        isClicked = newExerciseDefinition.isCalisthenic,
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.ToggleIsCalisthenics,
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Cardio",
                        isClicked = newExerciseDefinition.isCardio,
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.ToggleIsCardio,
                    )
                }
            }
        }
    }
}