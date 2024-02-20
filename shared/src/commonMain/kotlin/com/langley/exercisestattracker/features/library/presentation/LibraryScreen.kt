package com.langley.exercisestattracker.features.library.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.EditDefinitionDetailsView
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.ExerciseBuilderScreen
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.LibraryState
import com.langley.exercisestattracker.features.library.presentation.components.DefinitionDetailsView
import com.langley.exercisestattracker.features.library.presentation.components.ExerciseDefinitionListItem
import com.langley.exercisestattracker.features.library.presentation.components.ExerciseLibraryTopBar
import com.langley.exercisestattracker.navigation.ExerciseAppNavController

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    appModule: AppModule,
    state: LibraryState,
    newExerciseDefinition: ExerciseDefinition = ExerciseDefinition(),
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController
) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            if (
                !state.isEditExerciseDefSheetOpen
                and !state.isAddExerciseDefSheetOpen
                and !state.isExerciseDetailsSheetOpen
                ){

                FloatingActionButton(
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(LibraryEvent.AddNewDefClicked)
                    },
                    shape = RoundedCornerShape(20.dp)
                ){
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add exercise definition."
                    )
                }
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .focusable(true)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { focusManager.clearFocus() },
        ){
            ExerciseLibraryTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp,16.dp),
                state = state,
                onEvent = onEvent,
                focusManager = focusManager,
                navController = navController
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(vertical = 8.dp),

                content = {
                    items(state.exerciseDefinitions){ exerciseDefinition: ExerciseDefinition ->
                        ExerciseDefinitionListItem(
                            exerciseDefinition,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(8.dp)
                                .focusable(true)
                                .clickable {
                                    focusManager.clearFocus()
                                    onEvent(LibraryEvent.DefinitionSelected(exerciseDefinition))
                                },
                        )
                    }
                }
            )
        }

        DefinitionDetailsView(
            isVisible = state.isExerciseDetailsSheetOpen,
            onEvent = onEvent,
            selectedExerciseDefinition = state.selectedExerciseDefinition
        )

        EditDefinitionDetailsView(
            isVisible = state.isEditExerciseDefSheetOpen,
            state = state,
            onEvent = onEvent,
            newExerciseDefinition = newExerciseDefinition,
            focusManager = focusManager,
            interactionSource = interactionSource
        )

        ExerciseBuilderScreen(
            isVisible = state.isAddExerciseDefSheetOpen,
            appModule = appModule,
            libraryOnEvent = onEvent,
            newExerciseDefinition = newExerciseDefinition,
            focusManager = focusManager,
            interactionSource = interactionSource,
        )
    }
}