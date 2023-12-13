package com.langley.exercisestattracker.exerciseLibrary.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition

@Composable
fun ExerciseLibraryScreen(
    state: ExerciseLibraryState,
    newExerciseDefinition: ExerciseDefinition?,
    onEvent: (ExerciseLibraryEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(ExerciseLibraryEvent.DefaultEvent)
                },
                shape = RoundedCornerShape(20.dp)
            ){
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add exercise definition."
                )
            }
        }
    ){  }
}