package com.langley.exercisestattracker.features.exerciseBuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderEvent
import com.langley.exercisestattracker.features.exerciseBuilder.ExerciseBuilderState
import com.langley.exercisestattracker.features.exerciseBuilder.utils.filterTargetMuscles

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

        val fullMusclesList = remember { mutableStateOf(
            TargetMuscles().coreMuscles
                    + TargetMuscles().lowerMuscles
                    + TargetMuscles().armMuscles
                    + TargetMuscles().backMuscles
                    + TargetMuscles().chestMuscles
                    + TargetMuscles().shoulderMuscles
        ) }
        val currentMusclesList = remember { mutableStateOf( listOf("") ) }

        if (state.primaryTargetList.isNullOrEmpty()){
            currentMusclesList.value = fullMusclesList.value
        }
        else {
            currentMusclesList.value = filterTargetMuscles(state.primaryTargetList)
        }


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

            LazyColumn(
                modifier = Modifier.heightIn(min = 0.dp, max = 300.dp)
            ) {
                items(currentMusclesList.value){

                    SelectableTextBoxWithEvent(
                        modifier = Modifier.fillMaxWidth()
                            .height(44.dp),
                        text = it,
                        isClicked = state.musclesList?.contains(it) ?: false,
                        onEvent = onEvent,
                        event = ExerciseBuilderEvent.ToggleTargetMuscle(it),
                    )

                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
    Spacer(Modifier.height(8.dp))
}