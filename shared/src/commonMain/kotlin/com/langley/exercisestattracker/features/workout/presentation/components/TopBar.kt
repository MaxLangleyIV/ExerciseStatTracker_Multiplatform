package com.langley.exercisestattracker.features.workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.features.workout.WorkoutEvent
import com.langley.exercisestattracker.features.workout.WorkoutState
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navController: ExerciseAppNavController,
    workoutState: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit = {}

){

    Column(
        modifier = modifier
    ) {

//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Start
//        ){
//            IconButton(
//                onClick = {
//                    navController.navigateBack()
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBackIos,
//                    contentDescription = "Close"
//                )
//            }
//        }
        // Routine Name
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(4.dp),

            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text = workoutState.routine?.routineName
                    ?: ("New Workout - " + Clock.System.todayIn(TimeZone.currentSystemDefault()))
            )

        }
    }

}