package com.langley.exercisestattracker.features.library.schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseSchedule
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.core.presentation.composables.RoundedTextContainer
import com.langley.exercisestattracker.features.library.LibraryEvent

@Composable
fun ScheduleDetailsView(
    modifier: Modifier = Modifier,
    isVisible: Boolean = false,
    schedule: ExerciseSchedule = ExerciseSchedule(),
    libraryOnEvent: (LibraryEvent) -> Unit
){

    BasicBottomSheet(
        visible = isVisible,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    )
    {

        // Top Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            IconButton(
                onClick = {
                    libraryOnEvent(LibraryEvent.CloseDetailsView)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }

            IconButton(
                onClick = {
                    libraryOnEvent(LibraryEvent.ToggleFavoriteSchedule(schedule))
                }
            ) {
                Icon(

                    imageVector =
                    if (
                        schedule.isFavorite
                    ) {
                        Icons.Filled.Star
                    }
                    else { Icons.Filled.StarOutline },

                    contentDescription = "Favorite"
                )

            }

            Text(
                text = "Edit",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
                    .clickable {
//                        defBuilderOnEvent(
//                            ExerciseBuilderEvent.InitializeDefinition
//                        )
//                        libraryOnEvent(
//                            LibraryEvent.EditExercise(schedule)
//                        )
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        // Title Section
        Column (
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Spacer(Modifier.height(16.dp))


            Column()
            {

                Text(
                    text = schedule.exerciseScheduleName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    lineHeight = 40.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.tertiaryContainer)

                ){}
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = schedule.description,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = schedule.exerciseRoutineCSV,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        )

    }

}