package com.langley.exercisestattracker.library.features.exerciseBuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.presentation.composables.ErrorDisplayingTextField
import com.langley.exercisestattracker.library.LibraryEvent
import com.langley.exercisestattracker.library.LibraryState
import com.langley.exercisestattracker.library.presentation.components.SelectableTextBoxWithEvent

@Composable
fun CardioExerciseBuilderView(
    state: LibraryState,
    newExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),
    onEvent: (LibraryEvent) -> Unit,
    tagsSectionVisibleState: MutableState<Boolean>
){



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
                SelectableTextBoxWithEvent(
                    text = "Reps",
                    isClicked = newExerciseDefinition.hasReps,
                    onEvent = onEvent,
                    event = LibraryEvent.ToggleHasReps,
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Weight",
                    isClicked = newExerciseDefinition.isWeighted,
                    onEvent = onEvent,
                    event = LibraryEvent.ToggleIsWeighted,
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
                    text = "Time",
                    isClicked = newExerciseDefinition.isTimed,
                    onEvent = onEvent,
                    event = LibraryEvent.ToggleIsTimed,
                )

                Spacer(Modifier.width(8.dp))

                SelectableTextBoxWithEvent(
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
                        tagsSectionVisibleState.value = !tagsSectionVisibleState.value
                    }
                ) {
                    Icon(
                        imageVector =
                        if (tagsSectionVisibleState.value){ Icons.Outlined.ArrowDropUp }
                        else{ Icons.Outlined.ArrowDropDown },
                        contentDescription = "Expand Tags Section"
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            if (tagsSectionVisibleState.value){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    SelectableTextBoxWithEvent(
                        text = "Body Weight",
                        isClicked = newExerciseDefinition.isCalisthenic,
                        onEvent = onEvent,
                        event = LibraryEvent.ToggleIsCalisthenics,
                    )

                    Spacer(Modifier.width(8.dp))

                    SelectableTextBoxWithEvent(
                        text = "Cardio",
                        isClicked = newExerciseDefinition.isCardio,
                        onEvent = onEvent,
                        event = LibraryEvent.ToggleIsCardio,
                    )
                }
            }
        }
    }
}