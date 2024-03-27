package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState

@Composable
fun EmptyWorkoutContent(
    modifier: Modifier = Modifier,
    workoutState: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit = {}
){
    Text(
        text = "This workout is currently empty.",
        color = MaterialTheme.colorScheme.onBackground
    )

    // Buttons Column
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add Exercise Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 0.dp, max = 60.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onEvent(WorkoutEvent.OpenExerciseSelector)
                }
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ){

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                text = "Add an exercise.",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(Modifier.height(8.dp))

        // Select Routine Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 0.dp, max = 60.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {  }
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
        ){

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                text = "Select a routine.",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(Modifier.height(8.dp))
    }
}