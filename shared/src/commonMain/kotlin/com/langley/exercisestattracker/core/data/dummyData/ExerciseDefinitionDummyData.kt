package com.langley.exercisestattracker.core.data.dummyData

import com.langley.exercisestattracker.core.domain.ExerciseDefinition
import com.langley.exercisestattracker.core.domain.ExerciseRecord
import kotlinx.datetime.Clock
import kotlin.random.Random

class ExerciseDefinitionDummyData {

    val definitionList = listOf(
        ExerciseDefinition(
            exerciseDefinitionId = 0,
            exerciseName = "Barbell Squat",
            bodyRegion = "Lower Body",
            targetMuscles = "Quadriceps, Hamstrings, Glutes, Calves, Lower Back, Core",
            description = "The barbell squat is a compound exercise that primarily targets the muscles of the lower body. It is often considered the king of all leg exercises.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 1,
            exerciseName = "Barbell Deadlift",
            bodyRegion = "Lower Body, Upper Body, Back",
            targetMuscles = "Lower Back, Glutes, Hamstrings, Forearms, Grip Strength",
            description = "The barbell deadlift is a compound exercise that involves lifting a loaded barbell from the ground to a standing position. It targets multiple muscle groups and is an excellent strength-building exercise.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 2,
            exerciseName = "Barbell Bench Press",
            bodyRegion = "Chest",
            targetMuscles = "Pectoralis Major, Anterior Deltoids, Triceps",
            description = "The barbell bench press is a classic chest exercise that targets the muscles of the chest, shoulders, and arms. It is a fundamental movement in strength training and bodybuilding.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 3,
            exerciseName = "Barbell Bent Over Row",
            bodyRegion = "Back",
            targetMuscles = "Latissimus Dorsi, Rhomboids, Trapezius, Biceps",
            description = "The barbell bent over row is an effective back exercise that targets the muscles of the upper and middle back, as well as the biceps. It helps improve back strength and posture.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 4,
            exerciseName = "Barbell Shoulder Press",
            bodyRegion = "Shoulders",
            targetMuscles = "Deltoids, Triceps, Trapezius",
            description = "The barbell shoulder press, also known as the overhead press, targets the muscles of the shoulders, triceps, and upper back. It is an effective exercise for building upper body strength and muscle mass.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 5,
            exerciseName = "Barbell Romanian Deadlift",
            bodyRegion = "Back, Hamstrings",
            targetMuscles = "Hamstrings, Glutes, Lower Back",
            description = "The barbell Romanian deadlift is a variation of the conventional deadlift that primarily targets the muscles of the posterior chain, including the hamstrings, glutes, and lower back. It is an excellent exercise for developing strength and muscle mass in the hamstrings and hips.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 6,
            exerciseName = "Pull-Up",
            bodyRegion = "Back, Arms",
            targetMuscles = "Latissimus Dorsi, Biceps, Forearms, Upper Back",
            description = "The pull-up is a classic calisthenics exercise that targets the muscles of the back, arms, and core. It involves lifting your bodyweight from a hanging position until your chin clears the bar.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = true,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 7,
            exerciseName = "Push-Up",
            bodyRegion = "Chest, Shoulders, Arms",
            targetMuscles = "Pectoralis Major, Deltoids, Triceps",
            description = "The push-up is a foundational calisthenics exercise that targets the muscles of the chest, shoulders, and arms. It is performed by lowering your body to the ground and then pushing back up to the starting position.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = true,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 8,
            exerciseName = "Bodyweight Squat",
            bodyRegion = "Legs",
            targetMuscles = "Quadriceps, Hamstrings, Glutes, Calves",
            description = "The bodyweight squat is a fundamental calisthenics exercise that targets the muscles of the lower body. It is performed by squatting down until your thighs are parallel to the ground and then standing back up.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = true,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 9,
            exerciseName = "Dips",
            bodyRegion = "Chest, Triceps, Shoulders",
            targetMuscles = "Pectoralis Major, Triceps, Deltoids",
            description = "Dips are a compound exercise that targets the muscles of the chest, triceps, and shoulders. It is performed by lowering your body between parallel bars and then pushing yourself back up to the starting position.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = true,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 10,
            exerciseName = "Lunges",
            bodyRegion = "Legs",
            targetMuscles = "Quadriceps, Hamstrings, Glutes, Calves",
            description = "Lunges are a unilateral lower body exercise that targets the quadriceps, hamstrings, glutes, and calves. They can be performed with bodyweight or with added resistance.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = true,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 11,
            exerciseName = "Dumbbell Shoulder Press",
            bodyRegion = "Shoulders",
            targetMuscles = "Deltoids, Triceps, Trapezius",
            description = "The dumbbell shoulder press targets the muscles of the shoulders, triceps, and upper back. It is performed by pressing dumbbells overhead from shoulder height to a fully extended position.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 12,
            exerciseName = "Dumbbell Bicep Curl",
            bodyRegion = "Arms",
            targetMuscles = "Biceps, Forearms",
            description = "The dumbbell bicep curl is an isolation exercise that targets the muscles of the biceps and forearms. It is performed by curling dumbbells from a fully extended position to shoulder height.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 13,
            exerciseName = "Dumbbell Bent Over Row",
            bodyRegion = "Back",
            targetMuscles = "Latissimus Dorsi, Rhomboids, Trapezius, Biceps",
            description = "The dumbbell bent over row is a compound exercise that targets the muscles of the upper and middle back, as well as the biceps. It is performed by rowing dumbbells towards the hips from a bent over position.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 14,
            exerciseName = "Dumbbell Chest Press",
            bodyRegion = "Chest",
            targetMuscles = "Pectoralis Major, Anterior Deltoids, Triceps",
            description = "The dumbbell chest press is an effective exercise for targeting the muscles of the chest, shoulders, and triceps. It is performed by pressing dumbbells upward from chest level to a fully extended position.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 15,
            exerciseName = "Dumbbell Lunges",
            bodyRegion = "Legs",
            targetMuscles = "Quadriceps, Hamstrings, Glutes, Calves",
            description = "Dumbbell lunges are a unilateral lower body exercise that targets the quadriceps, hamstrings, glutes, and calves. Holding dumbbells at your sides, step forward into a lunge position and then return to the starting position.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 16,
            exerciseName = "Dumbbell Romanian Deadlift",
            bodyRegion = "Back, Hamstrings",
            targetMuscles = "Hamstrings, Glutes, Lower Back",
            description = "The dumbbell Romanian deadlift is a variation of the conventional deadlift that primarily targets the muscles of the posterior chain, including the hamstrings, glutes, and lower back. Hold dumbbells in front of your thighs and hinge at the hips while keeping your back straight.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 17,
            exerciseName = "Dumbbell Front Raise",
            bodyRegion = "Shoulders",
            targetMuscles = "Anterior Deltoids",
            description = "The dumbbell front raise is an isolation exercise that targets the anterior deltoids. It is performed by raising dumbbells from a neutral position to shoulder height in front of the body.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 18,
            exerciseName = "Dumbbell Tricep Kickback",
            bodyRegion = "Arms",
            targetMuscles = "Triceps",
            description = "The dumbbell tricep kickback is an isolation exercise that targets the triceps. It is performed by extending the arm backward while holding a dumbbell, focusing on squeezing the triceps at the top of the movement.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 19,
            exerciseName = "Dumbbell Lateral Raise",
            bodyRegion = "Shoulders",
            targetMuscles = "Lateral Deltoids",
            description = "The dumbbell side lateral raise is an isolation exercise that targets the lateral deltoids. It is performed by raising dumbbells from a neutral position to shoulder height to the sides of the body.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 20,
            exerciseName = "Cable Lat Pulldown",
            bodyRegion = "Back",
            targetMuscles = "Latissimus Dorsi, Rhomboids, Biceps",
            description = "The cable lat pulldown is a compound exercise that targets the muscles of the back and arms. It is performed by pulling a cable attachment down towards the chest while seated.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 21,
            exerciseName = "Cable Row",
            bodyRegion = "Back",
            targetMuscles = "Rhomboids, Trapezius, Biceps",
            description = "The cable row is a compound exercise that targets the muscles of the upper back and arms. It is performed by pulling a cable attachment towards the torso while seated.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 22,
            exerciseName = "Cable Chest Fly",
            bodyRegion = "Chest",
            targetMuscles = "Pectoralis Major, Anterior Deltoids",
            description = "The cable chest fly is an isolation exercise that targets the muscles of the chest and shoulders. It is performed by extending the arms outward from the sides of the body.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 23,
            exerciseName = "Cable Bicep Curl",
            bodyRegion = "Arms",
            targetMuscles = "Biceps, Forearms",
            description = "The cable bicep curl is an isolation exercise that targets the muscles of the biceps and forearms. It is performed by curling a cable attachment towards the shoulders while keeping the upper arms stationary.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 24,
            exerciseName = "Cable Tricep Pushdown",
            bodyRegion = "Arms",
            targetMuscles = "Triceps",
            description = "The cable tricep pushdown is an isolation exercise that targets the muscles of the triceps. It is performed by pushing a cable attachment downward until the arms are fully extended.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 25,
            exerciseName = "Cable Face Pull",
            bodyRegion = "Shoulders, Upper Back",
            targetMuscles = "Rear Deltoids, Rhomboids, Trapezius",
            description = "The cable face pull is an exercise that targets the muscles of the shoulders and upper back. It is performed by pulling a cable attachment towards the face at eye level.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 26,
            exerciseName = "Cable Woodchopper",
            bodyRegion = "Core, Obliques",
            targetMuscles = "Obliques, Core",
            description = "The cable woodchopper is a functional exercise that targets the muscles of the core and obliques. It simulates the motion of chopping wood and is performed by rotating the torso and pulling a cable attachment across the body.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 27,
            exerciseName = "Cable Reverse Fly",
            bodyRegion = "Shoulders, Upper Back",
            targetMuscles = "Rear Deltoids, Rhomboids, Trapezius",
            description = "The cable reverse fly is an exercise that targets the muscles of the shoulders and upper back. It is performed by pulling a cable attachment rearward, squeezing the shoulder blades together at the end of the movement.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 28,
            exerciseName = "Cable Crunch",
            bodyRegion = "Core",
            targetMuscles = "Rectus Abdominis, Obliques",
            description = "The cable crunch is an isolation exercise that targets the muscles of the core. It is performed by kneeling in front of a cable machine with a rope attachment overhead, then flexing the spine forward while keeping the hips stationary.",
            isWeighted = true,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 29,
            exerciseName = "Treadmill Running",
            bodyRegion = "Lower Body",
            targetMuscles = "Quadriceps, Hamstrings, Calves",
            description = "Treadmill running is a popular cardiovascular exercise that primarily targets the muscles of the legs and improves cardiovascular endurance. It involves running on a motorized treadmill at various speeds and inclines.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = true,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 30,
            exerciseName = "Stationary Bike",
            bodyRegion = "Lower Body",
            targetMuscles = "Quadriceps, Hamstrings, Calves",
            description = "The stationary bike is a low-impact cardiovascular exercise that primarily targets the muscles of the legs. It involves pedaling on a stationary bike at various resistance levels to simulate outdoor cycling.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 31,
            exerciseName = "Elliptical Training",
            bodyRegion = "Legs, Cardiovascular",
            targetMuscles = "Quadriceps, Hamstrings, Calves",
            description = "Elliptical training is a low-impact cardiovascular exercise that targets the muscles of the legs. It involves using an elliptical machine to simulate running or walking while minimizing impact on the joints.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 32,
            exerciseName = "Jump Rope",
            bodyRegion = "Legs, Cardiovascular",
            targetMuscles = "Calves, Quadriceps, Hamstrings",
            description = "Jump rope is a high-intensity cardiovascular exercise that targets the muscles of the legs and improves coordination. It involves jumping over a rope while swinging it continuously.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 33,
            exerciseName = "Stair Climbing",
            bodyRegion = "Legs, Cardiovascular",
            targetMuscles = "Quadriceps, Hamstrings, Calves",
            description = "Stair climbing is a cardiovascular exercise that targets the muscles of the legs and improves cardiovascular endurance. It involves climbing up and down a flight of stairs or using a stair climber machine.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 34,
            exerciseName = "Rowing Machine",
            bodyRegion = "Legs, Back, Cardiovascular",
            targetMuscles = "Quadriceps, Hamstrings, Glutes, Back",
            description = "Rowing machine is a full-body cardiovascular exercise that targets the muscles of the legs, back, and arms. It simulates rowing a boat and provides a low-impact but effective workout.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 35,
            exerciseName = "Sprinting",
            bodyRegion = "Legs, Cardiovascular",
            targetMuscles = "Quadriceps, Hamstrings, Calves",
            description = "Sprinting is a high-intensity cardiovascular exercise that primarily targets the muscles of the legs. It involves running at maximum speed for short distances.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = true,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 36,
            exerciseName = "Cycling",
            bodyRegion = "Legs, Cardiovascular",
            targetMuscles = "Quadriceps, Hamstrings, Calves",
            description = "Cycling is a low-impact cardiovascular exercise that targets the muscles of the legs. It can be performed outdoors on a bike or indoors on a stationary bike.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = true,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 37,
            exerciseName = "Swimming",
            bodyRegion = "Full Body, Cardiovascular",
            targetMuscles = "Various",
            description = "Swimming is a full-body cardiovascular exercise that targets multiple muscle groups. It provides a low-impact but effective workout and improves overall endurance and strength.",
            isWeighted = false,
            hasReps = false,
            isCardio = true,
            isCalisthenic = false,
            isTimed = true,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 38,
            exerciseName = "Banded Squats",
            bodyRegion = "Legs",
            targetMuscles = "Quadriceps, Hamstrings, Glutes",
            description = "Banded squats are a variation of the traditional squat exercise that add resistance using a resistance band. They primarily target the muscles of the legs and glutes.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 39,
            exerciseName = "Banded Push-Ups",
            bodyRegion = "Chest, Shoulders, Arms",
            targetMuscles = "Pectoralis Major, Deltoids, Triceps",
            description = "Banded push-ups are a variation of the traditional push-up exercise that add resistance using a resistance band. They primarily target the muscles of the chest, shoulders, and arms.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 40,
            exerciseName = "Banded Rows",
            bodyRegion = "Back",
            targetMuscles = "Latissimus Dorsi, Rhomboids, Biceps",
            description = "Banded rows are a variation of the traditional rowing exercise that add resistance using a resistance band. They primarily target the muscles of the back and arms.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 41,
            exerciseName = "Banded Glute Bridges",
            bodyRegion = "Glutes, Hamstrings",
            targetMuscles = "Gluteus Maximus, Hamstrings",
            description = "Banded glute bridges are a variation of the traditional glute bridge exercise that add resistance using a resistance band. They primarily target the muscles of the glutes and hamstrings.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 42,
            exerciseName = "Banded Lateral Walk",
            bodyRegion = "Legs, Glutes",
            targetMuscles = "Gluteus Medius, Gluteus Minimus, Quadriceps",
            description = "Banded lateral walks are a resistance exercise that primarily target the muscles of the legs and glutes, particularly the gluteus medius and minimus. They involve taking sideways steps against the resistance of a looped resistance band placed around the legs.",
            isWeighted = false,
            hasReps = false,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        ),
        ExerciseDefinition(
            exerciseDefinitionId = 43,
            exerciseName = "Banded Shoulder External Rotation",
            bodyRegion = "Shoulders",
            targetMuscles = "Rotator Cuff Muscles",
            description = "Banded shoulder external rotation is a strengthening exercise that targets the muscles of the rotator cuff. It involves securing a resistance band at waist height and pulling it away from the body while keeping the elbow bent at a 90-degree angle.",
            isWeighted = false,
            hasReps = true,
            isCardio = false,
            isCalisthenic = false,
            isTimed = false,
            defaultDuration = 0,
            hasDistance = false,
            defaultDistance = 0,
            isFavorite = false,
            dateCreated = null
        )
    )

}


fun ExerciseDefinitionDummyData.getListOfDummyExerciseRecords(): List<ExerciseRecord>{

    val dummyExerciseRecords = mutableListOf<ExerciseRecord>()
    val sizeOfDefinitionLibrary = definitionList.size

    for (i in 0..100){
        val randomDef = definitionList[Random.nextInt(sizeOfDefinitionLibrary)]

        dummyExerciseRecords.add(
            ExerciseRecord(
                recordId = randomDef.exerciseDefinitionId,
                exerciseDefId = randomDef.exerciseDefinitionId ?: -1,
                dateCompleted = Clock.System.now().toEpochMilliseconds(),
                exerciseName = randomDef.exerciseName,
                weightUsed = if (randomDef.isWeighted){ Random.nextInt(300).toFloat() } else { 0.toFloat() },
                isCardio = randomDef.isCardio,
                isCalisthenic = randomDef.isCalisthenic,
                duration =
                if (randomDef.isCardio) {
                    "${Random.nextInt(60)}:${Random.nextInt(from = 10, until = 60)}"
                }
                else { "0" },
                distance =
                if (randomDef.isCardio) {
                    ((((Random.nextInt(5).toFloat() + Random.nextFloat()) * 100.0).toInt()).toFloat()
                            / 100.0).toFloat()
                }
                else { 0f },
                distanceUnit = "miles",
                repsCompleted = if (randomDef.hasReps){ Random.nextInt(20) } else { 0 },
                rir = Random.nextInt(5),
                notes = "",
                userId = 0.toLong(),
                currentBodyWeight = 0
            )
        )
    }

    return dummyExerciseRecords
}
