package com.langley.exercisestattracker.features.exerciseBuilder.utils

import com.langley.exercisestattracker.core.data.TargetMuscles

fun filterTargetMuscles(
    regions: List<String>,
    musclesMap: Map<String, List<String>> = TargetMuscles().musclesMap
): List<String>{
//    var regions = state.primaryTargetList
    var currentRegionsList = regions.toMutableList()
    val newCurrentList = mutableListOf<String>()


    if (
        regions.contains("Upper Body")
        && (regions.contains("Arms")
                || regions.contains("Back")
                || regions.contains("Chest")
                || regions.contains("Shoulders")
                )
    ) { currentRegionsList = regions.filter { it != "Upper Body" }.toMutableList()}

    for (region in currentRegionsList){

        try {
            newCurrentList.addAll(musclesMap.getValue(region.lowercase()))
        }
        catch (error: NoSuchElementException){
            continue
        }
    }

    return if (newCurrentList.isNotEmpty()) {
        newCurrentList.toSet().toList()
    }
    else regions
}