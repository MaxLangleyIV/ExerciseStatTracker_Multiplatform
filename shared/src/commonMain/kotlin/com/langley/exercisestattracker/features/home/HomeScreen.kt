package com.langley.exercisestattracker.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.langley.exercisestattracker.core.domain.ExerciseAppDataSource
import com.langley.exercisestattracker.navigation.ExerciseAppNavController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState = HomeState(),
    dataSource: ExerciseAppDataSource,
    dataStore: DataStore<Preferences>,
//    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource,
    navController: ExerciseAppNavController,
    visible: Boolean = false,
){
    if (visible){

        Box(
            modifier = modifier
        ){
            // Main Screen
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                // Date and User Profile
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25F)
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ){
                    Text(text = "Date and User Details")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Weekly Stats
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25F)
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ){
                    Text(text = "Weekly Stats")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Start Workout
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25F)
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ){
                    Text(text = "Start Workout")
                }

                Spacer(modifier = Modifier.height(8.dp))


                

            }
        }

    }
}