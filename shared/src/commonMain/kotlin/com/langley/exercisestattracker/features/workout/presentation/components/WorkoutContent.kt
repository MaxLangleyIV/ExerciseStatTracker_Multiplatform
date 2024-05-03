package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.data.toBlankRecord
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import kotlinx.datetime.Clock

@Composable
fun WorkoutContent(
    modifier: Modifier = Modifier,
    exercises: List<ExerciseDefinition> = emptyList(),
    records: List<ExerciseRecord> = emptyList(),
    openExerciseSelector: () -> Unit = {},
    workoutMode: Boolean = false,
    displayOnlyMode: Boolean = false,
    updateRepsFromString: (index: Int, string: String) -> Unit = { _, _ -> },
    updateWeightFromString: (index: Int, string: String) -> Unit = { _, _ -> },
    markSetComplete: (index: Int, set: ExerciseRecord) -> Unit = { _, _ -> },
    markSetIncomplete: (index: Int, set: ExerciseRecord) -> Unit = { _, _ -> },
    addToListOfRecords: (list: List<ExerciseRecord>) -> Unit = {},
    removeRecord: (index: Int) -> Unit = {},
    removeExercise: (index: Int) -> Unit = {},
){

    Box(
        modifier = modifier
    ) {
        // Main Exercises View
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for ((exerciseIndex, exercise) in exercises.withIndex()){

                var numberOfSets by remember { mutableStateOf( 0 ) }

                // Exercise Group
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(4.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = exercise.exerciseName,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Spacer( Modifier.height(4.dp).fillMaxWidth() )

                    // Exercise Sets
                    Column(
                        modifier = Modifier.padding(2.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var lastSetEntered: ExerciseRecord? = null

                        WeightTrainingLabelsRow(workoutMode = workoutMode)

                        Divider(
                            modifier = Modifier.fillMaxWidth()
                        )

                        for ((recordIndex, set) in records.withIndex()){

                            if (set.exerciseName == exercise.exerciseName){

                                numberOfSets += 1

                                WeightTrainingRecordRow(
                                    workoutMode = workoutMode,
                                    exercise = exercise,
                                    set = set,
                                    setNumber = numberOfSets,
                                    recordIndex = recordIndex,
                                    updateRepsFromString = {index, string ->
                                        updateRepsFromString(index, string)
//                                        onEvent(WorkoutEvent.UpdateRepsFromString(index, string))
                                    },
                                    updateWeightFromString = {index, string ->
                                        updateWeightFromString(index, string)
//                                        onEvent(WorkoutEvent.UpdateWeightFromString(index, string))
                                    },
                                    markComplete = {index, newSet ->
                                        markSetComplete(index, newSet)
//                                        onEvent(WorkoutEvent.MarkCompleted(index, newSet))
                                    },
                                    markIncomplete = {index, newSet ->
                                        markSetIncomplete(index, newSet)
                                    },
                                    removeRecord = {index -> removeRecord(index) },
                                    displayOnlyMode = displayOnlyMode
                                )

                                lastSetEntered = set
                            }

                        }

                        numberOfSets = 0

                        // Add New Set and Remove Exercise Buttons
                        if (!displayOnlyMode){

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        if (lastSetEntered != null){
                                            addToListOfRecords(
                                                listOf(
                                                    lastSetEntered.copy(
                                                        dateCompleted =
                                                        Clock.System.now().toEpochMilliseconds(),
                                                        completed = false
                                                    )
                                                )
                                            )
                                        }
                                        else {
                                            addToListOfRecords(
                                                listOf(exercise.toBlankRecord().copy(completed = false))
                                            )
                                        }
                                    }
                                ){
                                    Text( text = "Add Set" )
                                }

                                if (lastSetEntered == null){
                                    Button(
                                        onClick = {
                                            removeExercise(exerciseIndex)
                                        }
                                    ){
                                        Text( text = "Remove Exercise" )
                                    }
                                }
                            }

                        }

                    }
                }

                Spacer(Modifier.height(8.dp))
            }

            if (!displayOnlyMode){
                Button(
                    onClick = {
                        openExerciseSelector()
//                    onEvent(
//                        WorkoutEvent.OpenSelector
//                    )
                    }
                ){
                    Text( text = "Add New Exercise" )
                }
            }
        }

    }
}