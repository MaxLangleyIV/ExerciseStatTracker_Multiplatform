package com.langley.exercisestattracker.features.records.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.components.RoundedTextContainer
import com.langley.exercisestattracker.features.records.RecordsEvent

@Composable
fun RecordDetailsView(
    isVisible: Boolean,
    onEvent: (RecordsEvent) -> Unit,
    selectedRecord: ExerciseRecord = ExerciseRecord()
) {
    var showMuscles by remember { mutableStateOf(true) }

    val record by remember(selectedRecord) { mutableStateOf(selectedRecord) }


    BasicBottomSheet(
        visible = isVisible,
        modifier = Modifier.fillMaxSize()
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
                    onEvent(RecordsEvent.CloseDetailsView)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }

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
                    text = record.exerciseName,
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

            Spacer(Modifier.height(16.dp))

            // Tags Section
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (record.weightUsed > 0 ){
                    RoundedTextContainer(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Weight Training",
                        maxLines = 1
                    )
                    Spacer(Modifier.height(4.dp))
                }
                if (record.isCalisthenic){
                    RoundedTextContainer(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Calisthenics",
                        maxLines = 1
                    )
                    Spacer(Modifier.height(4.dp))
                }
                if (record.isCardio){
                    RoundedTextContainer(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Cardio",
                        maxLines = 1
                    )
                    Spacer(Modifier.height(4.dp))
                }
                if (record.duration > 0){
                    RoundedTextContainer(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Timed Exercise",
                        maxLines = 1
                    )
                    Spacer(Modifier.height(4.dp))
                }
                if (record.distance > 0){
                    RoundedTextContainer(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Distance Measured",
                        maxLines = 1
                    )
                    Spacer(Modifier.height(4.dp))
                }
            }

            Spacer(Modifier.height(16.dp))

            // Metrics
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){

                if (record.weightUsed > 0 ){
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier.weight(0.30f),
                            text = "Weight Used:",
                            textAlign = TextAlign.Center
                        )
                        RoundedTextContainer(
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f),
                            text =
                            record.weightUsed.toString()
                            + if (record.weightIsPounds){" lbs."} else { " kg."}
                            ,
                            maxLines = 1
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                }

                if (record.repsCompleted > 0){
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier.weight(0.30f),
                            text = "Reps:",
                            textAlign = TextAlign.Center
                        )
                        RoundedTextContainer(
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f),
                            text = record.repsCompleted.toString(),
                            maxLines = 1
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                }

                if (record.duration > 0){
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier.weight(0.30f),
                            text = "Duration:",
                            textAlign = TextAlign.Center
                        )
                        RoundedTextContainer(
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f),
                            text = record.duration.toString(),
                            maxLines = 1
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                }
                if (record.distance > 0){
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier.weight(0.30f),
                            text = "Distance:",
                            textAlign = TextAlign.Center
                        )
                        RoundedTextContainer(
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f),
                            text = record.distance.toString(),
                            maxLines = 1
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                }

            }

        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = record.notes,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(16.dp))

    }
}