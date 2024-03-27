package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import kotlinx.datetime.Clock

@Composable
fun WorkoutContent(
    modifier: Modifier = Modifier,
    workoutState: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit = {}
){

    // Exercises
    for ((exerciseIndex, exercise) in workoutState.exerciseList.withIndex()){

        // Exercise Group
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = exercise.exerciseName,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Exercise Sets
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var lastSetEntered: ExerciseRecord? = null

                for ((recordIndex, set) in workoutState.recordsList.withIndex()){

                    if (set.exerciseName == exercise.exerciseName){
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

                        lastSetEntered = set
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            if (lastSetEntered != null){
                                onEvent(
                                    WorkoutEvent.AddToListOfRecords(
                                        listOf(
                                            lastSetEntered.copy(
                                                dateCompleted =
                                                Clock.System.now().toEpochMilliseconds(),
                                                completed = false
                                            )
                                        )
                                    )
                                )
                            }
                        }
                    ){
                        Text( text = "Add another set." )
                    }
                }

            }
        }
        Spacer(Modifier.height(8.dp))
    }
}