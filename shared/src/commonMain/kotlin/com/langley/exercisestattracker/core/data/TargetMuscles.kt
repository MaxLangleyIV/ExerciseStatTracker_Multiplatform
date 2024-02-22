package com.langley.exercisestattracker.core.data

data class TargetMuscles(
    val armMuscles:List<String> = listOf(
        "Biceps",
        "Triceps",
        "Forearms"
    ),
    val backMuscles:List<String> = listOf(
        "Latissimus dorsi",
        "Trapezius",
        "Rhomboids",
        "Erector spinae"
    ),
    val chestMuscles:List<String> = listOf(
        "Pectoralis Major / Minor",
        "Upper Pecs",
        "Lower Pecs"
    ),
    val coreMuscles:List<String> = listOf(
        "Rectus abdominis",
        "Transverse abdominis",
        "Internal obliques",
        "External obliques",
        "Erector spinae"
    ),
    val lowerMuscles:List<String> = listOf(
        "Quadriceps",
        "Hamstrings",
        "Gluteus maximus",
        "Gluteus medius",
        "Gluteus minimus",
        "Calves"
    ),
    val shoulderMuscles:List<String> = listOf(
        "Anterior Deltoid",
        "Lateral Deltoid",
        "Rear Deltoid",
        "Rotator Cuff"
    ),

    val musclesMap: Map<String,List<String>> = mapOf(
        "arms" to armMuscles,
        "back" to backMuscles,
        "chest" to chestMuscles,
        "core" to coreMuscles,
        "lower" to lowerMuscles,
        "shoulders" to shoulderMuscles
    )
)