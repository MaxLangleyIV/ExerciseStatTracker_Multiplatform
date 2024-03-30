package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.features.workout.WorkoutEvent

@Composable
fun WeightTrainingRecordRow(
    modifier: Modifier = Modifier,
    exercise: ExerciseDefinition = ExerciseDefinition(),
    set: ExerciseRecord = ExerciseRecord(),
    onEvent: (WorkoutEvent) -> Unit,
    recordIndex: Int
    ){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                width = 4.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Weight Used
        Column(
            modifier = Modifier
                .weight(0.3F)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.weight(0.3F),
                text = "Weight: ",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Start
            )

            Spacer( Modifier.height(4.dp) )

            Text(
                modifier = Modifier.weight(0.7F),
                text = set.weightUsed.toString(),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp),
            color = MaterialTheme.colorScheme.outline
        )

        // Reps Performed
        Column(
            modifier = Modifier
                .weight(0.3F)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {  }
            Row {  }

        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp),
            color = MaterialTheme.colorScheme.outline
        )

        // Completion Status
        Column(
            modifier = Modifier
                .weight(0.3F)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {  }
            Row {  }

        }

    }

//    Column(
//
//    ) {
//
//        // Headers Row
//        Row(
//            modifier = Modifier
//                .weight(0.2F)
//                .fillMaxWidth()
//                .padding(2.dp)
//                .background(MaterialTheme.colorScheme.secondaryContainer),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceEvenly
//
//        ) {
//            if (exercise.hasReps){
//
//                Text(
//                    text = "Reps: ",
//                    color = MaterialTheme.colorScheme.onSecondaryContainer
//                )
//
//                Divider(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .width(4.dp),
//                    color = MaterialTheme.colorScheme.outline
//                )
//
//            }
//
//            if (exercise.isWeighted){
//
//
//
//                Divider(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .width(4.dp),
//                    color = MaterialTheme.colorScheme.outline
//                )
//
//            }
//
//            Text(
//                text = "Completed: ",
//                color = MaterialTheme.colorScheme.onSecondaryContainer
//            )
//        }
//
//        // Values Row
//        Row(
//            modifier = Modifier
//                .weight(0.4F)
//                .fillMaxWidth()
//                .padding(2.dp)
//                .background(MaterialTheme.colorScheme.secondaryContainer),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//
//            if (exercise.hasReps){
//
//                Text(
//                    text = set.repsCompleted.toString(),
//                    color = MaterialTheme.colorScheme.onSecondaryContainer
//                )
//
//                Divider(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .width(4.dp),
//                    color = MaterialTheme.colorScheme.outline
//                )
//
//            }
//
//            if (exercise.isWeighted){
//
//
//
//                Divider(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .width(4.dp),
//                    color = MaterialTheme.colorScheme.outline
//                )
//
//            }
//
//            Checkbox(
//                onCheckedChange = {isChecked ->
//                    if (isChecked){
//                        onEvent(3
//                            WorkoutEvent.MarkCompleted(recordIndex, set)
//                        )
//                    }
//                    else {
//                        onEvent(
//                            WorkoutEvent.MarkIncomplete(recordIndex, set)
//                        )
//                    }
//                },
//                checked = set.completed
//            )
//
//        }
//
//    }
}