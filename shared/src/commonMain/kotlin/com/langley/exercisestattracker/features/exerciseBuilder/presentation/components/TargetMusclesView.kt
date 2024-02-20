package com.langley.exercisestattracker.features.exerciseBuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.data.TargetMuscles
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.features.exerciseBuilder.BodyRegion
import com.langley.exercisestattracker.features.exerciseBuilder.BodyRegionSubGroup
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState

@Composable
fun TargetMusclesView(
    state: ExerciseBuilderState,
    newExerciseDefinition: ExerciseDefinition,
    onEvent: (ExerciseBuilderEvent) -> Unit,
) {
    // Target Muscles Row
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
        val currentMusclesList = remember { mutableStateOf(listOf("")) }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            text = "Target Muscles (Optional):",
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        Spacer(Modifier.height(8.dp))

        when(state.bodyRegion){
            BodyRegion.Core -> {
                currentMusclesList.value = TargetMuscles().coreMuscles
            }
            BodyRegion.Lower -> {
                currentMusclesList.value = TargetMuscles().lowerMuscles
            }
            BodyRegion.NotApplicable -> {
                currentMusclesList.value = listOf("Not Applicable")
            }
            BodyRegion.Upper -> {
                when (state.bodyRegionSubGroup){
                    BodyRegionSubGroup.Arms -> {
                        currentMusclesList.value = TargetMuscles().armMuscles
                    }
                    BodyRegionSubGroup.Back -> {
                        currentMusclesList.value = TargetMuscles().backMuscles
                    }
                    BodyRegionSubGroup.Chest -> {
                        currentMusclesList.value = TargetMuscles().chestMuscles
                    }
                    BodyRegionSubGroup.NotApplicable -> {
                        currentMusclesList.value = listOf("Not Applicable")
                    }
                    BodyRegionSubGroup.Shoulders -> {
                        currentMusclesList.value = TargetMuscles().shoulderMuscles
                    }
                    null -> {}
                }
            }

            BodyRegion.Full -> {
                currentMusclesList.value = listOf("Not Applicable")
            }

            null -> {
                currentMusclesList.value = listOf("Not Applicable")
            }

        }

        LazyRow {
            items(currentMusclesList.value){
                val textSize = if (it.length >= 8){ 16.sp } else { 18.sp }

                SelectableTextBoxWithEvent(
                    text = it,
                    textSize = textSize,
                    isClicked = newExerciseDefinition.targetMuscles.lowercase().contains(it),
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.OnTargetMusclesChanged(it),
                    )

                Spacer(Modifier.width(8.dp))
            }
        }

//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ){
//            SelectableTextBoxWithEvent(
//                text = "Biceps",
//                isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("biceps"),
//                onEvent = onEvent,
//                event = ExerciseBuilderEvent.OnTargetMusclesChanged("biceps"),
//            )
//
//            Spacer(Modifier.width(8.dp))
//
//            SelectableTextBoxWithEvent(
//                text = "Pectoralis",
//                isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("pectoralis"),
//                onEvent = onEvent,
//                event = ExerciseBuilderEvent.OnTargetMusclesChanged("pectoralis"),
//            )
//
//            Spacer(Modifier.width(8.dp))
//
//            SelectableTextBoxWithEvent(
//                text = "Deltoids",
//                isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("deltoid"),
//                onEvent = onEvent,
//                event = ExerciseBuilderEvent.OnTargetMusclesChanged("deltoids"),
//            )
//
//            Spacer(Modifier.width(8.dp))
//
//            SelectableTextBoxWithEvent(
//                text = "Quadriceps",
//                isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("quadriceps"),
//                onEvent = onEvent,
//                event = ExerciseBuilderEvent.OnTargetMusclesChanged("quadriceps"),
//            )
//
//        }
    }
    Spacer(Modifier.height(8.dp))
}