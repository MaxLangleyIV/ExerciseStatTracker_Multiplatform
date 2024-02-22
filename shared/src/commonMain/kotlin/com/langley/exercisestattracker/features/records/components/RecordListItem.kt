package com.langley.exercisestattracker.features.records.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import kotlinx.datetime.Instant
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

@Composable
fun RecordListItem(exerciseRecord: ExerciseRecord, modifier: Modifier = Modifier) {

    val dateCompleted = Instant.fromEpochMilliseconds(
        exerciseRecord.dateCompleted
    ).toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())

    Column(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(top = 8.dp, start = 12.dp)
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondary,
                text = "${dateCompleted.month.number}/${dateCompleted.dayOfMonth}/${dateCompleted.year}"
            )
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Column(
                modifier = Modifier
            ){

                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    text = exerciseRecord.exerciseName
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Row {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    text = "Reps:"
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    text = exerciseRecord.repsCompleted.toString()
                )
            }



            Row(
                modifier = Modifier
            ) {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    text = "Weight:"
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    text = exerciseRecord.weightUsed.toString()
                )
            }
        }

    }


}