package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
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

        var numberOfSets by remember { mutableStateOf( 0 ) }

        // Exercise Group
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = exercise.exerciseName,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )

            Spacer( Modifier.height(4.dp).fillMaxWidth() )

            // Exercise Sets
            Column(
                modifier = Modifier.padding(2.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var lastSetEntered: ExerciseRecord? = null

                for ((recordIndex, set) in workoutState.recordsList.withIndex()){

                    if (set.exerciseName == exercise.exerciseName){

                        numberOfSets += 1

                        WeightTrainingRecordRow(
                            exercise = exercise,
                            set = set,
                            setNumber = numberOfSets,
                            recordIndex = recordIndex,
                            onEvent = onEvent
                        )

                        Spacer(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(4.dp),
                        )

                        lastSetEntered = set
                    }


                }

                numberOfSets = 0

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