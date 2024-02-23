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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.di.AppModule
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.ExerciseBuilderScreen
import com.langley.exercisestattracker.features.library.LibraryEvent
import com.langley.exercisestattracker.features.library.LibraryState
import com.langley.exercisestattracker.features.library.LibraryViewModel
import com.langley.exercisestattracker.features.library.presentation.components.DefinitionDetailsView
import com.langley.exercisestattracker.features.library.presentation.components.ExerciseDefinitionListItem
import com.langley.exercisestattracker.features.library.presentation.components.ExerciseLibraryTopBar
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    appModule: AppModule,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController
) {
    val libraryViewModel = getViewModel(
        key = "libraryViewModel",
        factory = viewModelFactory {
            LibraryViewModel(appModule.exerciseAppDataSource)
        }
    )

    val libraryState by libraryViewModel.state.collectAsState(LibraryState())


    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            if (
                !libraryState.isEditExerciseDefSheetOpen
                and !libraryState.isAddExerciseDefSheetOpen
                and !libraryState.isExerciseDetailsSheetOpen
                ){

                FloatingActionButton(
                    onClick = {
                        focusManager.clearFocus()
                        (libraryViewModel::onEvent)(LibraryEvent.ClearSelectedDef)
                        (libraryViewModel::onEvent)(LibraryEvent.AddNewDefClicked)
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
                state = libraryState,
                onEvent = libraryViewModel::onEvent,
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
                    items(libraryState.exerciseDefinitions){ exerciseDefinition: ExerciseDefinition ->
                        ExerciseDefinitionListItem(
                            exerciseDefinition,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(8.dp)
                                .focusable(true)
                                .clickable {
                                    focusManager.clearFocus()
                                    (libraryViewModel::onEvent)(LibraryEvent.DefinitionSelected(exerciseDefinition))
                                },
                        )
                    }
                }
            )
        }

        DefinitionDetailsView(
            isVisible = libraryState.isExerciseDetailsSheetOpen,
            libraryOnEvent = libraryViewModel::onEvent,
            definition =
            libraryState.selectedExerciseDefinition?: ExerciseDefinition()
        )


        ExerciseBuilderScreen(
            appModule = appModule,
            isVisible = libraryState.isAddExerciseDefSheetOpen,
            selectedExercise = libraryState.selectedExerciseDefinition,
            libraryOnEvent = libraryViewModel::onEvent,
            focusManager = focusManager,
            interactionSource = interactionSource,
        )
    }
}