package com.langley.exercisestattracker.exerciseLibrary.data.dummyData

import com.langley.exercisestattracker.exerciseLibrary.domain.ExerciseRoutine

class ExerciseRoutineDummyData {

    val exerciseRoutines = listOf(
        mapOf(
            "name" to "Push Day",
            "exerciseCSV" to "Push-Ups, Barbell Bench Press, Dips, Barbell Overhead Press",
            "repsCSV" to "10, 10, 20, 10",
            "description" to "Chest and triceps focused push day."
        ),
        mapOf(
            "name" to "Pull Day",
            "exerciseCSV" to "Pull-Ups, Deadlifts, Bent Over Rows, Face Pulls",
            "repsCSV" to "10, 8, 10, 12",
            "description" to "Back and biceps focused pull day."
        ),
        mapOf(
            "name" to "Leg Day",
            "exerciseCSV" to "Squats, Lunges, Leg Press, Romanian Deadlifts",
            "repsCSV" to "8, 12, 10, 10",
            "description" to "Quads, hamstrings, and glutes focused leg day."
        ),
        mapOf(
            "name" to "Core Day",
            "exerciseCSV" to "Planks, Russian Twists, Hanging Leg Raises, Bicycle Crunches",
            "repsCSV" to "60s, 20, 12, 20",
            "description" to "Core strength and stability focused workout."
        ),
        mapOf(
            "name" to "Upper Body Strength",
            "exerciseCSV" to "Barbell Rows, Incline Bench Press, Pull-Ups, Seated Dumbbell Shoulder Press",
            "repsCSV" to "8, 10, 8, 10",
            "description" to "Focuses on building strength in the upper body muscles."
        ),
        mapOf(
            "name" to "Lower Body Strength",
            "exerciseCSV" to "Deadlifts, Front Squats, Leg Curls, Calf Raises",
            "repsCSV" to "5, 8, 10, 12",
            "description" to "Strength training for the lower body muscles."
        ),
        mapOf(
            "name" to "Full Body Circuit",
            "exerciseCSV" to "Burpees, Kettlebell Swings, Mountain Climbers, Jumping Lunges",
            "repsCSV" to "15, 12, 20, 10",
            "description" to "High-intensity circuit targeting multiple muscle groups."
        ),
        mapOf(
            "name" to "Core and Stability",
            "exerciseCSV" to "Planks, Stability Ball Rollouts, Side Planks, Superman",
            "repsCSV" to "60s, 10, 30s each side, 12",
            "description" to "Improves core strength and stability."
        ),
        mapOf(
            "name" to "Cardio Blast",
            "exerciseCSV" to "Running, Jump Rope, High Knees, Burpees",
            "repsCSV" to "20 mins, 3 sets, 1 min, 15 reps",
            "description" to "Intense cardio workout to boost endurance and burn calories."
        ),
        mapOf(
            "name" to "Yoga Flow",
            "exerciseCSV" to "Sun Salutations, Warrior Poses, Downward Dog, Tree Pose",
            "repsCSV" to "5 rounds, 5 breaths each, 5 breaths, 30s each side",
            "description" to "Gentle yoga sequence to improve flexibility, strength, and mindfulness."
        ),
        mapOf(
            "name" to "HIIT Express",
            "exerciseCSV" to "Sprint Intervals, Jumping Jacks, Burpees, Mountain Climbers",
            "repsCSV" to "30s sprint, 20 reps, 15 reps, 20 reps",
            "description" to "High-intensity interval training for maximum calorie burn and fitness gains."
        ),
        mapOf(
            "name" to "Stretch and Mobility",
            "exerciseCSV" to "Foam Rolling, Dynamic Stretches, Hip Openers, Shoulder Mobility",
            "repsCSV" to "10 mins, 10 reps each, 30s each side, 10 reps each",
            "description" to "Improves flexibility, reduces muscle tension, and enhances range of motion."
        ),
        mapOf(
            "name" to "Powerlifting Basics",
            "exerciseCSV" to "Squats, Bench Press, Deadlifts, Barbell Rows",
            "repsCSV" to "5, 5, 5, 5",
            "description" to "Classic powerlifting routine for overall strength development."
        ),
        mapOf(
            "name" to "Hypertrophy Circuit",
            "exerciseCSV" to "Dumbbell Bicep Curls, Tricep Dips, Leg Extensions, Lat Pulldowns",
            "repsCSV" to "12, 15, 12, 15",
            "description" to "Circuit training for muscle hypertrophy and definition."
        ),
        mapOf(
            "name" to "Upper/Lower Split",
            "exerciseCSV" to "Barbell Squats, Overhead Press, Bent Over Rows, Romanian Deadlifts",
            "repsCSV" to "6, 8, 8, 10",
            "description" to "Split routine targeting upper and lower body on different days for strength and size."
        ),
        mapOf(
            "name" to "Push-Pull Supersets",
            "exerciseCSV" to "Dumbbell Chest Press, Pull-Ups, Seated Shoulder Press, Cable Rows",
            "repsCSV" to "10, 12, 10, 12",
            "description" to "Supersetting push and pull exercises for efficient strength and muscle building."
        ),
        mapOf(
            "name" to "German Volume Training (GVT)",
            "exerciseCSV" to "Barbell Squats, Barbell Bench Press, Bent Over Rows, Romanian Deadlifts",
            "repsCSV" to "10, 10, 10, 10",
            "description" to "High-volume training method for muscle growth and strength gains."
        ),
        mapOf(
            "name" to "Arnold Schwarzenegger's Blueprint to Mass",
            "exerciseCSV" to "Dumbbell Shoulder Press, Barbell Curl, Dumbbell Flyes, Tricep Pushdown",
            "repsCSV" to "8, 10, 12, 12",
            "description" to "Classic bodybuilding routine focusing on muscle isolation and hypertrophy."
        ),
        mapOf(
            "name" to "5/3/1 Strength Program",
            "exerciseCSV" to "Deadlifts, Overhead Press, Bench Press, Squats",
            "repsCSV" to "5, 3, 1, 5",
            "description" to "Strength program emphasizing gradual progression and heavy lifting."
        ),
        mapOf(
            "name" to "Leg Day Mass Builder",
            "exerciseCSV" to "Barbell Squats, Leg Press, Walking Lunges, Leg Curls",
            "repsCSV" to "8, 10, 12, 12",
            "description" to "Intense leg workout to add mass and strength to lower body muscles."
        )
    )
}

fun ExerciseRoutineDummyData.toListOfExerciseRoutines(): List<ExerciseRoutine> {

    val convertedList = mutableListOf<ExerciseRoutine>()

    for ( (i, routine) in this.exerciseRoutines.withIndex() ){

        val exerciseRoutine = ExerciseRoutine(
            exerciseRoutineId = i.toLong(),
            routineName = routine["name"] as String,
            exerciseCSV = routine["exerciseCSV"] as String,
            repsCSV = routine["target_muscles"] as String,
            description = routine["description"] as String,
            isFavorite = 0,
            dateCreated = null,)

        convertedList.add(exerciseRoutine)
    }
    return convertedList
}