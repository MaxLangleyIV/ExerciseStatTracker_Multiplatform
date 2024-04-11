package com.langley.exercisestattracker.features.library.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseSchedule

@Composable
fun ScheduleListItem(
    schedule: ExerciseSchedule,
    modifier: Modifier = Modifier,
    selectable: Boolean = false,
    isClicked: Boolean = false
){
    Box(
        modifier =
        if (selectable){
            modifier
                .size(128.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isClicked){
                        MaterialTheme.colorScheme.primary
                    }
                    else{
                        MaterialTheme.colorScheme.secondary
                    }
                )
                .border(
                    width = 4.dp,

                    color = if (isClicked) {
                        MaterialTheme.colorScheme.outline
                    } else Color.Transparent,

                    shape = RoundedCornerShape(8.dp)
                )
                .padding(4.dp)
        }
        else{
            modifier
                .size(128.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(4.dp)
        },

        contentAlignment = Alignment.Center

    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .size(128.dp,64.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier.weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    text = schedule.exerciseScheduleName,
                    color =
                    if (isClicked){
                        MaterialTheme.colorScheme.onPrimary
                    }
                    else{
                        MaterialTheme.colorScheme.onSecondary
                    },
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = schedule.exerciseRoutineCSV,
                    color =
                    if (isClicked){
                        MaterialTheme.colorScheme.onPrimary
                    }
                    else{
                        MaterialTheme.colorScheme.onSecondary
                    },
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}