package com.langley.exercisestattracker.features.library.features.exerciseBuilder.presentation.components

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
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.BodyRegion
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.BodyRegionSubGroup
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.ExerciseBuilderState


@Composable
fun BodyRegionView(
    state: ExerciseBuilderState,
//    newExerciseDefinition: ExerciseDefinition,
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
                    isClicked = state.bodyRegion == BodyRegion.Upper,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Upper)
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Lower",
                    isClicked = state.bodyRegion == BodyRegion.Lower,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Lower)
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Core",
                    isClicked = state.bodyRegion == BodyRegion.Core,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Core)
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Full",
                    isClicked = state.bodyRegion == BodyRegion.Full,
                    onEvent = onEvent,
                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Full)
                )
            }

            Spacer(Modifier.height(8.dp))

            if (state.bodyRegion == BodyRegion.Upper){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    text = "Sub Group:",
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    SelectableTextBoxWithEvent(
                        text = "Arms",
                        isClicked = state.bodyRegionSubGroup == BodyRegionSubGroup.Arms,
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent
                            .ToggleBodyRegionSubGroup(BodyRegionSubGroup.Arms)
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Back",
                        isClicked = state.bodyRegionSubGroup == BodyRegionSubGroup.Back,
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent
                            .ToggleBodyRegionSubGroup(BodyRegionSubGroup.Back)
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Chest",
                        isClicked = state.bodyRegionSubGroup == BodyRegionSubGroup.Chest,
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent
                            .ToggleBodyRegionSubGroup(BodyRegionSubGroup.Chest)
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Shoulders",
                        textSize = 16.sp,
                        isClicked = state.bodyRegionSubGroup == BodyRegionSubGroup.Shoulders,
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent
                            .ToggleBodyRegionSubGroup(BodyRegionSubGroup.Shoulders),
                    )

                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}