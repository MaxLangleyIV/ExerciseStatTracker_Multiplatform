package com.langley.exercisestattracker.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.core.presentation.composables.ErrorDisplayingTextField
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.library.LibraryEvent
import com.langley.exercisestattracker.library.LibraryState

@Composable
fun AddNewExerciseDefView(
    state: LibraryState,
    isVisible: Boolean,
    newExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource
){
    BasicBottomSheet(
        visible = isVisible,
        modifier = Modifier.fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() }
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
                    onEvent(LibraryEvent.CloseAddDefClicked)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
        }

        // Title Row
        Row(
            modifier = Modifier.fillMaxWidth(),
        ){
            Text(
                text = "New Exercise:",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Column (
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Spacer(Modifier.height(16.dp))

            // Name Input
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                ErrorDisplayingTextField(
                    value = "${newExerciseDefinition?.exerciseName}",
                    placeholder = "Exercise Name",
                    error = state.exerciseNameError,
                    onValueChanged = {
                        onEvent(LibraryEvent.OnNameChanged(it))
                    },
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

            Spacer(
                Modifier.height(16.dp)
            )

            // Body Region Row
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){

                Column()
                {
                    Text(
                        text = "Body Region:",
                        textAlign = TextAlign.Left,
                        modifier = Modifier,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Column()
                {
                    ErrorDisplayingTextField(
                        value = "${newExerciseDefinition?.bodyRegion}",
                        placeholder = "Body Region",
                        error = state.exerciseBodyRegionError,
                        onValueChanged = {
                            onEvent(LibraryEvent.OnBodyRegionChanged(it))
                        },
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Target Muscles Row
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column()
                {
                    Text(
                        text = "Target Muscles:",
                        textAlign = TextAlign.Left,
                        modifier = Modifier,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Column()
                {
                    ErrorDisplayingTextField(
                        value = newExerciseDefinition.targetMuscles,
                        placeholder = "Target Muscles",
                        error = state.exerciseTargetMusclesError,
                        onValueChanged = {
                            onEvent(LibraryEvent.OnTargetMusclesChanged(it))
                        },
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(8.dp)
            ){

                Text( text = "Exercise Type:" )
                Spacer(Modifier.height(8.dp))

                Row {

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .clickable { onEvent(LibraryEvent.ToggleIsWeighted) }
                            .border(
                                width = 2.dp,

                                color = if (newExerciseDefinition.isWeighted) {
                                    MaterialTheme.colorScheme.outline
                                } else Color.Transparent,

                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(4.dp)

                    ){
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            text = "Weight Training"
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = newExerciseDefinition.description,
                onValueChange = {
                    onEvent(LibraryEvent.OnDescriptionChanged(it))
                },
                placeholder = {
                    Text(text = "Exercise Description")
                },
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    onEvent(LibraryEvent.SaveOrUpdateDef)
                }
            ){
                Text(text = "Save")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    onEvent(LibraryEvent.CloseAddDefClicked)
                }
            ){
                Text(text = "Cancel")
            }

        }
    }
}
