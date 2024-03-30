package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    setNumber: Int = 0,
    onEvent: (WorkoutEvent) -> Unit,
    recordIndex: Int
    ){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn( min = 32.dp, max = 68.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 4.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Title
        Column(
            modifier = Modifier
                .weight(0.2F)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Set $setNumber"
            )


        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp),
            color = MaterialTheme.colorScheme.outline
        )

        // Weight Used
        Column(
            modifier = Modifier
                .weight(0.2F)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .weight(0.3F)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.3F),
                    text = "Weight: ",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .weight(0.4F)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = set.weightUsed.toString(),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }

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
                .weight(0.2F)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .weight(0.3F)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.3F),
                    text = "Reps: ",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .weight(0.4F)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = set.repsCompleted.toString(),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }

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
                .weight(0.2F)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .weight(0.3F)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.3F),
                    text = "Complete: ",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .weight(0.3F)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
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

//
//        }
//
//    }
}