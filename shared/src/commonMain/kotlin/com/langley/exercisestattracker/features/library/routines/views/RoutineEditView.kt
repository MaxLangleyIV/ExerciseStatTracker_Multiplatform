package com.langley.exercisestattracker.features.library.routines.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.core.presentation.composables.ErrorDisplayingTextField
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.LibraryState
import com.langley.exercisestattracker.features.library.routines.RoutineBuilderState
import com.langley.exercisestattracker.features.library.routines.RoutineBuilderViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun RoutineEditView(
    dataSource: ExerciseAppDataSource,
    visible: Boolean,
    routine: ExerciseRoutine = ExerciseRoutine(),
    onEvent: (LibraryEvent) -> Unit,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource
) {
    BasicBottomSheet(
        visible = visible
    ){

        val routineBuilderViewModel = getViewModel(
            key = "routineBuilderViewModel",
            factory = viewModelFactory {
                RoutineBuilderViewModel(
                    dataSource = dataSource,
                    initialState = RoutineBuilderState(routine = routine),
                    libraryOnEvent = onEvent
                )
            }
        )

        println("ROUTINE BUILDER VIEW MODEL: $routineBuilderViewModel")
        println("ROUTINE: $routine")

        val state by routineBuilderViewModel.state.collectAsState(RoutineBuilderState())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { focusManager.clearFocus() },
            verticalArrangement = Arrangement.Top
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
                        onEvent(LibraryEvent.CloseEditView)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close"
                    )
                }

                Text(
                    text = "Delete",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                        .clickable {},
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )

            }

            // Body
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
                        value = routine.routineName,
                        placeholder = "Routine Name",
                        error = state.nameError,
                        onValueChanged = {},
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

                }

                Spacer(Modifier.height(16.dp))

                // Metrics Column


                Spacer(Modifier.height(16.dp))

                // Tags Column
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(8.dp)
                ){

                    Text( text = "Extra Tags:" )
                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
//

//                    SelectableTextBoxWithEvent(
//                        text = "Body Weight",
//                        isClicked = newExerciseDefinition.isCalisthenic,
//                        onEvent = onEvent,
//                        event = LibraryEvent.ToggleIsCalisthenics,
//                    )
//
//                    Spacer(Modifier.width(8.dp))
//
//                    SelectableTextBoxWithEvent(
//                        text = "Cardio",
//                        isClicked = newExerciseDefinition.isCardio,
//                        onEvent = onEvent,
//                        event = LibraryEvent.ToggleIsCardio,
//                    )
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = routine.description,
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Exercise Description")
                    },
                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {}
                ){
                    Text(text = "Update")
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {}
                ){
                    Text(text = "Cancel")
                }

            }
        }
    }
}

@Composable
fun SelectableTextBoxWithEvent(
    modifier: Modifier = Modifier,
    text: String,
    isClicked: Boolean,
    onEvent: (LibraryEvent) -> Unit,
    event: LibraryEvent,
){
    Box(
        modifier = modifier
//            .size(76.dp)
            .defaultMinSize(64.dp, 64.dp)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .clickable { onEvent(event) }
            .border(
                width = 2.dp,

                color = if (isClicked) {
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
            text = text,
            fontSize = 16.sp,
        )
    }
}