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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState

@Composable
fun TagsView(
    state: ExerciseBuilderState,
    newExerciseDefinition: ExerciseDefinition,
    onEvent: (ExerciseBuilderEvent) -> Unit,
){
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
                text = "Tags:"
            )

        }
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SelectableTextBoxWithEvent(
                text = "Weight Training",
                isClicked = newExerciseDefinition.isWeighted,
                onEvent = onEvent,
                event = ExerciseBuilderEvent.ToggleIsWeighted,
            )

            Spacer(Modifier.width(8.dp))

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