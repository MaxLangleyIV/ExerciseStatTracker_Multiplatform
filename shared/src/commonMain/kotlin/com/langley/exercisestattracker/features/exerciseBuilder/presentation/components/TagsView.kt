package com.langley.exercisestattracker.features.exerciseBuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.presentation.composables.DropdownToggle
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState

@Composable
fun TagsView(
    state: ExerciseBuilderState,
//    newExerciseDefinition: ExerciseDefinition,
    onEvent: (ExerciseBuilderEvent) -> Unit,
){
    val tagsOpen = remember { mutableStateOf(false) }
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
            modifier = Modifier.fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = "Tags:"
            )

            DropdownToggle(
                modifier = Modifier.size(20.dp),
                toggled = tagsOpen
            )

        }
        Spacer(Modifier.height(8.dp))

        if (tagsOpen.value){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SelectableTextBoxWithEvent(
                    text = "Weight Training",
                    isClicked = state.newExerciseDefinition.isWeighted,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleIsWeighted,
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Body Weight",
                    isClicked = state.newExerciseDefinition.isCalisthenic,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleIsCalisthenics,
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Cardio",
                    isClicked = state.newExerciseDefinition.isCardio,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleIsCardio,
                )
            }
        }
    }
}