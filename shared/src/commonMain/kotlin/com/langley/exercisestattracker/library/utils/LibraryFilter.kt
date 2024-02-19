package com.langley.exercisestattracker.library.utils

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.library.ExerciseLibraryFilterType

fun filterDefinitionLibrary(

    definitionLibrary: List<ExerciseDefinition>,
    filterType: ExerciseLibraryFilterType?,
    searchString: String = ""

):List<ExerciseDefinition> {

    var filteredLibrary: List<ExerciseDefinition> =
        when (filterType) {

            is ExerciseLibraryFilterType.Barbell -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "barbell",
                        true
                    )
                }
            }
            is ExerciseLibraryFilterType.Calisthenic -> {
                definitionLibrary.filter {
                    it.isCalisthenic
                }
            }
            is ExerciseLibraryFilterType.Dumbbell -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "dumbbell",
                        true)
                }
            }
            is ExerciseLibraryFilterType.Favorite -> {
                definitionLibrary.filter {
                    it.isFavorite
                }
            }
            is ExerciseLibraryFilterType.LowerBody -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "leg",
                        true)
                }
            }
            is ExerciseLibraryFilterType.UpperBody -> {
                definitionLibrary.filter {
                    it.exerciseName.contains(
                        "barbell",
                        true)
                }
            }

            is ExerciseLibraryFilterType.Cardio -> {
                definitionLibrary.filter {
                    it.isCardio
                }
            }

            null -> {
                definitionLibrary
            }


        }

    if (searchString.isNotBlank()){
        filteredLibrary = filteredLibrary.filter {
            it.exerciseName.contains(searchString, true)
        }
    }

    return filteredLibrary
}