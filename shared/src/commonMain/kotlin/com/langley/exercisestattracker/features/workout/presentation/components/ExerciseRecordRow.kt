package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        modifier = Modifier
            .fillMaxWidth(),
//            .border(
//                width = 4.dp,
//                color = MaterialTheme.colorScheme.outline,
//                shape = RoundedCornerShape(16.dp)
//            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (exercise.hasReps){

            Column {
                Text(
                    text = "Reps: ",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

//            DividerColumn()


            Column {
                Text(
                    text = set.repsCompleted.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

//            Divider(
//                modifier = Modifier
//                    .height(32.dp)
//                    .width(32.dp),
//                color = Color.Red
//            )

//            DividerColumn()

        }

        if (exercise.isWeighted){

            Column {
                Text(
                    text = "Weight: ",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

//            DividerColumn()

            Column(
                modifier = Modifier.clickable {

                }
            ) {
                Text(
                    text = set.weightUsed.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

//            DividerColumn()

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