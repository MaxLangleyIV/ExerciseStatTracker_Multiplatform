package com.langley.exercisestattracker.features.exerciseBuilder.presentation.components

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
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderEvent

@Composable
fun MetricsView(
    state: com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState,
    newExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),
    onEvent: (ExerciseBuilderEvent) -> Unit,
){

    Column (
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

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
    }
}