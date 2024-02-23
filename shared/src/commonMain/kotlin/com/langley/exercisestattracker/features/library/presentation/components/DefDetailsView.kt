package com.langley.exercisestattracker.features.library.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.presentation.composables.BasicBottomSheet
import com.langley.exercisestattracker.core.presentation.composables.DropdownToggle
import com.langley.exercisestattracker.features.exerciseBuilder.presentation.components.RoundedTextContainer
import com.langley.exercisestattracker.features.library.LibraryEvent

@Composable
fun DefinitionDetailsView(
    isVisible: Boolean,
    definition: ExerciseDefinition,
    libraryOnEvent: (LibraryEvent) -> Unit,
)
{
    val showMuscles = remember { mutableStateOf(false) }
    val primaryTargetList = remember { mutableStateOf(listOf<String>()) }
    val musclesList = remember { mutableStateOf(listOf<String>()) }

    if (definition.exerciseName.isNotBlank()){
        primaryTargetList.value = definition.bodyRegion.split(", ")
        musclesList.value = definition.targetMuscles.split(", ")
    }
    else {
        primaryTargetList.value = listOf()
        musclesList.value = listOf()
    }

    BasicBottomSheet(
        visible = isVisible,
        modifier = Modifier.fillMaxSize()
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
                    libraryOnEvent(LibraryEvent.ToggleIsFavorite(definition))
                }
            ) {
                Icon(

                    imageVector =
                    if (
                        definition.isFavorite
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
//                        defBuilderOnEvent(
//                            ExerciseBuilderEvent.InitializeDefinition
//                        )
                        libraryOnEvent(
                            LibraryEvent.EditDefinition(definition)
                        ) },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
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

            // Title Section
            Column()
            {

                Text(
                    text = definition.exerciseName,
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

            Spacer(
                Modifier.height(16.dp)
            )

            // Primary Target Section
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    text = "Primary Target:",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left
                )
                LazyVerticalGrid(
                    modifier = Modifier.heightIn(36.dp, 200.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    columns =
                    if (primaryTargetList.value.isEmpty()) { GridCells.Fixed(1)}
                    else if (primaryTargetList.value.size >= 3) { GridCells.Fixed(3) }
                    else { GridCells.Fixed(primaryTargetList.value.size) }
                ){
                    items(primaryTargetList.value){
                        RoundedTextContainer(
                            text = it,
                            boxSize = 64.dp
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Target Muscles
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                // Section Title Row
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1F),
                        text = "Target Muscles:",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    DropdownToggle(
                        modifier = Modifier.size(20.dp),
                        toggled = showMuscles
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Target Muscles List
                if (showMuscles.value){

                    LazyVerticalGrid(
                        modifier = Modifier.heightIn(min = 0.dp, max = 300.dp),
                        columns =
                        if (musclesList.value.isEmpty()) { GridCells.Fixed(1)}
                        else if (musclesList.value.size >= 3) { GridCells.Fixed(3) }
                        else { GridCells.Fixed(musclesList.value.size) }
                    ) {
                        items(musclesList.value){
                            RoundedTextContainer(
                                modifier = Modifier.fillMaxWidth(),
                                text = it,
                                boxSize = 64.dp
                            )

                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Target Muscles Section
//        Row(
//            modifier = Modifier.fillMaxWidth()
//                .clip(
//                    RoundedCornerShape(16.dp)
//                )
//                .background(MaterialTheme.colorScheme.secondaryContainer)
//                .padding(4.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically
//        ){
//            Column()
//            {
//                Text(
//                    text = "Target Muscles:",
//                    textAlign = TextAlign.Left,
//                    modifier = Modifier,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 20.sp,
//                    color = MaterialTheme.colorScheme.onSecondaryContainer
//                )
//            }
//
//            Column()
//            {
//                Text(
//                    text = definition.targetMuscles,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 24.sp,
//                    color = MaterialTheme.colorScheme.onSecondaryContainer
//                )
//            }
//        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = definition.description,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(16.dp))

    }
}