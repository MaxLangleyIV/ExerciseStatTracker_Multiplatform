package com.langley.exercisestattracker.features.home

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.langley.exercisestattracker.navigation.ExerciseAppNavController
import com.langley.exercisestattracker.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
//    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController
){
    Box(
        modifier = modifier
    ){
        Column(
            modifier = modifier.align(Alignment.Center)
        ) {
            Button(
                onClick = { navController.navigateTo(Screen.Library) }
            ){
                Text( text = "Open Library" )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = { navController.navigateTo(Screen.Records) }
            ){
                Text( text = "Open Records" )
            }
        }
    }


}