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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
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
import androidx.compose.ui.text.toLowerCase
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
    val tagsSectionIsVisible = remember { mutableStateOf(false) }

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
                fontSize = 26.sp,
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
            Spacer(Modifier.height(8.dp))

            // Name Input
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                ErrorDisplayingTextField(
                    value = newExerciseDefinition.exerciseName,
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
                Modifier.height(8.dp)
            )

            // Body Region Column
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    text = "Body Region:",
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    SelectableTextBox(
                        text = "Arms",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("arms"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnBodyRegionChanged("arms"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Back",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("back"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnBodyRegionChanged("back"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Chest",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("chest"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnBodyRegionChanged("chest"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Legs",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("legs"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnBodyRegionChanged("legs"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Core",
                        isClicked = newExerciseDefinition.bodyRegion.lowercase().contains("core"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnBodyRegionChanged("core"),
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Target Muscles Row
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    text = "Target Muscles:",
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    SelectableTextBox(
                        text = "Biceps",
                        isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("biceps"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnTargetMusclesChanged("biceps"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Pectoralis",
                        isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("pectoralis"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnTargetMusclesChanged("pectoralis"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Deltoids",
                        isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("deltoid"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnTargetMusclesChanged("deltoids"),
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Quadriceps",
                        isClicked = newExerciseDefinition.targetMuscles.lowercase().contains("quadriceps"),
                        onEvent = onEvent,
                        event = LibraryEvent.OnTargetMusclesChanged("quadriceps"),
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Metrics Column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp)
            ){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    text = "Metrics:"
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    SelectableTextBox(
                        text = "Reps",
                        isClicked = newExerciseDefinition.hasReps,
                        onEvent = onEvent,
                        event = LibraryEvent.ToggleHasReps,
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Weight",
                        isClicked = newExerciseDefinition.isWeighted,
                        onEvent = onEvent,
                        event = LibraryEvent.ToggleIsWeighted,
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Time",
                        isClicked = newExerciseDefinition.isTimed,
                        onEvent = onEvent,
                        event = LibraryEvent.ToggleIsTimed,
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBox(
                        text = "Distance",
                        isClicked = newExerciseDefinition.hasDistance,
                        onEvent = onEvent,
                        event = LibraryEvent.ToggleHasDistance,
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Tags Column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp)
            ){

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = "Extra Tags:"
                    )

                    IconButton(
                        onClick = {
                            tagsSectionIsVisible.value = !tagsSectionIsVisible.value
                        }
                    ) {
                        Icon(
                            imageVector =
                            if (tagsSectionIsVisible.value){ Icons.Outlined.ArrowDropUp }
                            else{ Icons.Outlined.ArrowDropDown },
                            contentDescription = "Expand Tags Section"
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))

                if (tagsSectionIsVisible.value){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        SelectableTextBox(
                            text = "Body Weight",
                            isClicked = newExerciseDefinition.isCalisthenic,
                            onEvent = onEvent,
                            event = LibraryEvent.ToggleIsCalisthenics,
                        )

                        Spacer(Modifier.width(8.dp))

                        SelectableTextBox(
                            text = "Cardio",
                            isClicked = newExerciseDefinition.isCardio,
                            onEvent = onEvent,
                            event = LibraryEvent.ToggleIsCardio,
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

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

            Spacer(Modifier.height(4.dp))

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

