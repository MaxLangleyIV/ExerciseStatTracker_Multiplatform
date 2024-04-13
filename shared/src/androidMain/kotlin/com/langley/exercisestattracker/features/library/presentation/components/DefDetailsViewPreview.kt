package com.langley.exercisestattracker.features.library.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData
import com.langley.exercisestattracker.features.library.presentation.components.exercises.DefinitionDetailsView

@Preview
@Composable
fun DefDetailsViewPreview(){
//    Text(text = "Test ")

    DefinitionDetailsView(
        isVisible = true,
        definition = ExerciseDefinitionDummyData().definitionList[0],
        libraryOnEvent = {}
    )
}