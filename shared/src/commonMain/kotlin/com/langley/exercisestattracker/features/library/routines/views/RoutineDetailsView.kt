package com.langley.exercisestattracker.features.library.routines.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseRoutine
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.features.library.LibraryEvent

@Composable
fun RoutineDetailsView(
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    routine: ExerciseRoutine = ExerciseRoutine(),
    libraryOnEvent: (LibraryEvent) -> Unit
){
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 100),
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 100),
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Top Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                IconButton(
                    onClick = {
                        libraryOnEvent(LibraryEvent.CloseDetailsView)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close"
                    )
                }

                IconButton(
                    onClick = {
                        libraryOnEvent(LibraryEvent.ToggleFavoriteRoutine(routine))
                    }
                ) {
                    Icon(

                        imageVector =
                        if (
                            routine.isFavorite
                        ) {
                            Icons.Filled.Star
                        }
                        else { Icons.Filled.StarOutline },

                        contentDescription = "Favorite"
                    )

                }

                Text(
                    text = "Edit",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                        .clickable {
                            libraryOnEvent(LibraryEvent.EditRoutine)
                        },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
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
                        text = routine.routineName,
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
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = routine.description,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = routine.exerciseCSV,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = routine.repsCSV,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

        }
    }

}