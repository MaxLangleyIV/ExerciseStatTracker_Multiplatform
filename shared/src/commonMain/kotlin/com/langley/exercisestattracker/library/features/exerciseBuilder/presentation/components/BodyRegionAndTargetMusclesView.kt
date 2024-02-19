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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.library.features.exerciseBuilder.BodyRegion
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.library.features.exerciseBuilder.ExerciseBuilderState

@Composable
fun BodyRegionAndTargetMusclesView(
    state: ExerciseBuilderState,
    newExerciseDefinition: ExerciseDefinition,
    onEvent: (ExerciseBuilderEvent) -> Unit,
){
    Column (
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

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
                SelectableTextBoxWithEvent(
                    text = "Upper",
                    isClicked = newExerciseDefinition
                        .bodyRegion
                        .lowercase()
                        .contains("upper"),
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Upper)
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Lower",
                    isClicked = newExerciseDefinition
                        .bodyRegion
                        .lowercase()
                        .contains("lower"),
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Lower)
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Core",
                    isClicked = newExerciseDefinition
                        .bodyRegion
                        .lowercase()
                        .contains("core"),
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Core)
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Not Applicable",
                    textSize = 14.sp,
                    isClicked = newExerciseDefinition
                        .bodyRegion
                        .lowercase()
                        .contains("not applicable"),
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.NotApplicable)
                )
            }

            Spacer(Modifier.height(8.dp))

            if (state.upperBodySelected){
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
    }
}