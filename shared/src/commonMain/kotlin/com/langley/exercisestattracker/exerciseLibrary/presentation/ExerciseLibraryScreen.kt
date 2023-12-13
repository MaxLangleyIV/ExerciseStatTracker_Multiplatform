package com.langley.exercisestattracker.exerciseLibrary.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.presentation.components.ExerciseDefinitionListItem

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
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),

        ){
            item {
                Text(text = "Exercise Library",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                    )
            }

            items(state.exerciseDefinitions){ exerciseDefinition: ExerciseDefinition ->
                ExerciseDefinitionListItem(
                    exerciseDefinition,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(ExerciseLibraryEvent.ExerciseDefinitionSelected(exerciseDefinition))
                        }
                    )
            }
        }
    }
}