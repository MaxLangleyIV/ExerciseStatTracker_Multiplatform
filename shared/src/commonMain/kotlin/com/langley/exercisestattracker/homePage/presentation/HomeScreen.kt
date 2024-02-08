package com.langley.exercisestattracker.homePage.presentation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import com.langley.exercisestattracker.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
//    onEvent: (ExerciseLibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController
){
    Box(
        modifier = modifier
    ){
        Button(
            modifier = modifier.align(Alignment.Center),
            onClick = { navController.navigateTo(Screen.Library) }
        ){
            Text( text = "Open Library" )
        }
    }


}