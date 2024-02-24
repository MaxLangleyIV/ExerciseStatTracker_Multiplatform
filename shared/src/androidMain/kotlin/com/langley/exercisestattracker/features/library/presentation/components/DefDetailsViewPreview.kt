package com.langley.exercisestattracker.features.library.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.langley.exercisestattracker.core.data.dummyData.ExerciseDefinitionDummyData

@Preview
@Composable
fun DefDetailsViewPreview(){
//    Text(text = "Test ")

    DefinitionDetailsView(
        isVisible = true,
        selectedDefinition = ExerciseDefinitionDummyData().definitionList[0],
        libraryOnEvent = {}
    )
}