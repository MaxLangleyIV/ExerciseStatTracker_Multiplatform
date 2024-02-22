package com.langley.exercisestattracker.features.library.features.exerciseBuilder.presentation.components

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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.langley.exercisestattracker.core.data.TargetMuscles
import com.langley.exercisestattracker.core.presentation.composables.DropdownToggle
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.library.features.exerciseBuilder.ExerciseBuilderState

@Composable
fun TargetMusclesView(
    state: ExerciseBuilderState,
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
        val listIsVisible = remember { mutableStateOf(false) }
        val musclesMap = remember { mutableStateOf(TargetMuscles().musclesMap) }

        val fullMusclesList = remember { mutableStateOf(
            TargetMuscles().coreMuscles
                    + TargetMuscles().lowerMuscles
                    + TargetMuscles().armMuscles
                    + TargetMuscles().backMuscles
                    + TargetMuscles().chestMuscles
                    + TargetMuscles().shoulderMuscles
        ) }
        val currentMusclesList = remember { mutableStateOf( listOf("") ) }

        currentMusclesList.value = fullMusclesList.value

//        when(state.bodyRegion){
//            BodyRegion.Core -> {
//                currentMusclesList.value = TargetMuscles().coreMuscles
//            }
//            BodyRegion.Lower -> {
//                currentMusclesList.value = TargetMuscles().lowerMuscles
//            }
//            BodyRegion.NotApplicable -> {
//                currentMusclesList.value = listOf("Not Applicable")
//            }
//            BodyRegion.Upper -> {
//                when (state.bodyRegionSubGroup){
//                    BodyRegionSubGroup.Arms -> {
//                        currentMusclesList.value = TargetMuscles().armMuscles
//                    }
//                    BodyRegionSubGroup.Back -> {
//                        currentMusclesList.value = TargetMuscles().backMuscles
//                    }
//                    BodyRegionSubGroup.Chest -> {
//                        currentMusclesList.value = TargetMuscles().chestMuscles
//                    }
//                    BodyRegionSubGroup.NotApplicable -> {
//                        currentMusclesList.value = listOf("Not Applicable")
//                    }
//                    BodyRegionSubGroup.Shoulders -> {
//                        currentMusclesList.value = TargetMuscles().shoulderMuscles
//                    }
//                    null -> {}
//                }
//            }
//
//            BodyRegion.Full -> {
//                currentMusclesList.value = fullMusclesList.value
//            }
//
//            null -> {
//                currentMusclesList.value = listOf("Not Applicable")
//            }
//
//        }

        // Section Title Row
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = "Target Muscles (Optional):",
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            DropdownToggle(
                modifier = Modifier.size(20.dp),
                toggled = listIsVisible
            )
        }

        Spacer(Modifier.height(8.dp))

        // Target Muscles List
        if (listIsVisible.value){
//            LazyRow {
//                items(currentMusclesList.value){
//                    val textSize = if (it.length >= 8){ 16.sp } else { 18.sp }
//
//                    SelectableTextBoxWithEvent(
//                        text = it,
//                        textSize = textSize,
//                        isClicked = state.musclesList?.contains(it) ?: false,
//                        onEvent = onEvent,
//                        event = ExerciseBuilderEvent.ToggleTargetMuscle(it),
//                    )
//
//                    Spacer(Modifier.width(8.dp))
//                }
//            }
            LazyColumn {
                items(currentMusclesList.value){
//                    val textSize = if (it.length >= 8){ 16.sp } else { 18.sp }

                    SelectableTextBoxWithEvent(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
//                        textSize = textSize,
                        isClicked = state.musclesList?.contains(it) ?: false,
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.ToggleTargetMuscle(it),
                    )

                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }
    Spacer(Modifier.height(8.dp))
}