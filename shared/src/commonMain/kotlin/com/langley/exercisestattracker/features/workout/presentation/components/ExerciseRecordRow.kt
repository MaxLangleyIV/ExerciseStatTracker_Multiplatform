package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.features.workout.WorkoutEvent

@Composable
fun ExerciseRecordRow(
    modifier: Modifier = Modifier,
    exercise: ExerciseDefinition = ExerciseDefinition(),
    set: ExerciseRecord = ExerciseRecord(),
    onEvent: (WorkoutEvent) -> Unit,
    recordIndex: Int
    ){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (exercise.hasReps){

            Column {
                Text(
                    text = "Reps:",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(4.dp))

            Column {
                Text(
                    text = set.repsCompleted.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(4.dp))

        }

        if (exercise.isWeighted){

            Column {
                Text(
                    text = "Weight:",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(4.dp))

            Column {
                Text(
                    text = set.weightUsed.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(4.dp))

        }

        Column {
            Checkbox(
                onCheckedChange = {isChecked ->
                    if (isChecked){
                        onEvent(
                            WorkoutEvent.MarkCompleted(recordIndex, set)
                        )
                    }
                    else {
                        onEvent(
                            WorkoutEvent.MarkIncomplete(recordIndex, set)
                        )
                    }
                },
                checked = set.completed
            )
        }
    }
}