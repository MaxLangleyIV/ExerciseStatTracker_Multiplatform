package com.langley.exercisestattracker.exerciseLibrary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.presentation.BasicBottomSheet
import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseDefinition
import com.langley.exercisestattracker.exerciseLibrary.presentation.ExerciseLibraryEvent

@Composable
fun ExerciseDefinitionDetailsView(
    isVisible: Boolean,
    selectedExerciseDefinition: ExerciseDefinition?,
    onEvent: (ExerciseLibraryEvent) -> Unit
)
{
    BasicBottomSheet(
        visible = isVisible,
        modifier = Modifier.fillMaxSize()
    )
    {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            IconButton(
                onClick = {
                    onEvent(ExerciseLibraryEvent.CloseExerciseDetailsView)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }

            Text(
                text = "Edit",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
                    .clickable {
                        onEvent(
                            ExerciseLibraryEvent.EditExerciseDefinition(selectedExerciseDefinition!!)
                        ) },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Spacer(Modifier.height(16.dp))

            Column()
            {
                Text(
                    text = "${selectedExerciseDefinition?.exerciseName}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.tertiary)

                ){}
            }



            Spacer(
                Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "${selectedExerciseDefinition?.bodyRegion}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )

                Text(
                    text = "${selectedExerciseDefinition?.targetMuscles}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "${selectedExerciseDefinition?.description}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
    }
}