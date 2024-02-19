package com.langley.exercisestattracker.features.exerciseBuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
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
    Spacer(Modifier.height(8.dp))
}