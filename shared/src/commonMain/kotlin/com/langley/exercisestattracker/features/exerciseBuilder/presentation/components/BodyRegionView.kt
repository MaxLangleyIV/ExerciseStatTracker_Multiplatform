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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState


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
                text = "Primary Target:",
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
                    isClicked = state.primaryTargetList?.contains("Upper Body") ?: false,
                    onEvent = onEvent,
//                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Upper)
                    event = ExerciseBuilderEvent.ToggleBodyRegion("Upper Body")
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Lower",
                    isClicked = state.primaryTargetList?.contains("Lower Body") ?: false,
                    onEvent = onEvent,
//                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Lower)
                    event = ExerciseBuilderEvent.ToggleBodyRegion("Lower Body")
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Core",
                    isClicked = state.primaryTargetList?.contains("Core") ?: false,
                    onEvent = onEvent,
//                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Core)
                    event = ExerciseBuilderEvent.ToggleBodyRegion("Core")
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Full",
                    isClicked = state.primaryTargetList?.contains("Full Body") ?: false,
                    onEvent = onEvent,
//                    event = ExerciseBuilderEvent.ToggleBodyRegion(BodyRegion.Full)
                    event = ExerciseBuilderEvent.ToggleBodyRegion("Full Body")
                )
            }

            Spacer(Modifier.height(8.dp))

            // Sub Group Section
//            if (state.bodyRegion == BodyRegion.Upper){
            if (state.primaryTargetList?.contains("Upper Body") == true){
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
//                        isClicked = state.bodyRegionSubGroup == BodyRegionSubGroup.Arms,
                        isClicked = state.primaryTargetList.contains("Arms"),
                        onEvent = onEvent,
//                        event = ExerciseBuilderEvent
//                            .ToggleBodyRegionSubGroup(BodyRegionSubGroup.Arms)
                        event = ExerciseBuilderEvent.ToggleBodyRegion("Arms")
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Back",
//                        isClicked = state.bodyRegionSubGroup == BodyRegionSubGroup.Back,
                        isClicked = state.primaryTargetList.contains("Back"),
                        onEvent = onEvent,
//                        event = ExerciseBuilderEvent
//                            .ToggleBodyRegionSubGroup(BodyRegionSubGroup.Back)
                        event = ExerciseBuilderEvent.ToggleBodyRegion("Back")
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Chest",
//                        isClicked = state.bodyRegionSubGroup == BodyRegionSubGroup.Chest,
                        isClicked = state.primaryTargetList.contains("Chest"),
                        onEvent = onEvent,
//                        event = ExerciseBuilderEvent
//                            .ToggleBodyRegionSubGroup(BodyRegionSubGroup.Chest)
                        event = ExerciseBuilderEvent.ToggleBodyRegion("Chest")

                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Shoulders",
                        textSize = 16.sp,
//                        isClicked = state.bodyRegionSubGroup == BodyRegionSubGroup.Shoulders,
                        isClicked = state.primaryTargetList.contains("Shoulders"),
                        onEvent = onEvent,
//                        event = ExerciseBuilderEvent
//                            .ToggleBodyRegionSubGroup(BodyRegionSubGroup.Shoulders),
                        event = ExerciseBuilderEvent.ToggleBodyRegion("Shoulders")
                    )

                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}