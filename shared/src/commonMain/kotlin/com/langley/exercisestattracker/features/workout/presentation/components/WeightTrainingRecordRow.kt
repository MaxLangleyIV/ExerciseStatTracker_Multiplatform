package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.features.workout.WorkoutEvent

@Composable
fun WeightTrainingLabelsRow(
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        //Title
        Column(
            modifier = Modifier
                .weight(0.2F)
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.weight(0.25F),
                text = "Sets",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
//            color = MaterialTheme.colorScheme.outline
        )

        // Weight Used
        Column(
            modifier = Modifier
                .weight(0.2F)
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.weight(0.25F),
                text = "Weight",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
//            color = MaterialTheme.colorScheme.outline
        )

        // Reps Performed
        Column(
            modifier = Modifier
                .weight(0.2F)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.weight(0.25F),
                text = "Reps",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

        }


        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
//            color = MaterialTheme.colorScheme.outline
        )

        // Completion Status
        Column(
            modifier = Modifier
                .weight(0.2F)
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.weight(0.25F),
                text = "Complete",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

        }

    }

}

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
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Title
        Column(
            modifier = Modifier
                .weight(0.2F)
                .clip(RoundedCornerShape(8.dp))
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = setNumber.toString()
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
        )

        // Weight Used
        var isWeightInputFocused by remember { mutableStateOf( false ) }

        Column(
            modifier = Modifier
                .weight(0.2F)
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(
                    width = 2.dp,
                    color =
                    if (isWeightInputFocused) {
                        MaterialTheme.colorScheme.outline
                    }
                    else {
                        Color.Transparent} ,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var textFieldValue by remember { mutableStateOf(set.weightUsed.toString()) }


            BasicTextField(
                modifier = Modifier
                    .onFocusChanged {
                        isWeightInputFocused = it.isFocused
                    }
                    .widthIn(min = 32.dp, max = 64.dp)
                    .heightIn(min = 24.dp, max = 64.dp)
                    .padding(4.dp),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    onEvent(
                        WorkoutEvent.UpdateWeightFromString(index = recordIndex, value = it)
                    )
                },
                singleLine = true,
                textStyle =
                TextStyle(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            )


        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
        )

        // Reps Performed
        var isRepsInputFocused by remember { mutableStateOf( false ) }

        Column(
            modifier = Modifier
                .weight(0.2F)
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(
                    width = 2.dp,
                    color =
                    if (isRepsInputFocused) {
                        MaterialTheme.colorScheme.outline
                    }
                    else {
                        Color.Transparent} ,
                    shape = RoundedCornerShape(8.dp)
                ),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var textFieldValue by remember { mutableStateOf(set.repsCompleted.toString()) }
            BasicTextField(
                modifier = Modifier
                    .onFocusChanged {
                        isRepsInputFocused = it.isFocused
                    }
                    .widthIn(min = 32.dp, max = 64.dp)
                    .heightIn(min = 24.dp, max = 64.dp)
                    .padding(4.dp),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    onEvent(
                        WorkoutEvent.UpdateRepsFromString(index = recordIndex, value = it)
                    )
                },
                singleLine = true,
                textStyle =
                TextStyle(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            )

        }


        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
//            color = MaterialTheme.colorScheme.outline
        )

        // Completion Status
        Column(
            modifier = Modifier
                .weight(0.2F)
//                .clip(RoundedCornerShape(8.dp))
////                .border(
////                    width = 4.dp,
////                    color = MaterialTheme.colorScheme.outline,
////                    shape = RoundedCornerShape(8.dp)
////                )
//                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
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