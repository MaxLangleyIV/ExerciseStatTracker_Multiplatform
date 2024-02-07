package com.langley.exercisestattracker.navigation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ExerciseAppNavControllerTest {

    private lateinit var navController: ExerciseAppNavController

    @Before
    fun setUp() {
        navController = ExerciseAppNavController()
    }

    @Test
    fun `navigateTo sets current screen`() {
        val screen = Screen.Library
        navController.navigateTo(screen)

        val currentScreen = navController.currentScreen.value
        assertEquals(screen, currentScreen)
    }

    @Test
    fun `navigateBack navigates to previous screen`() {
        val screen1 = Screen.Library
        val screen2 = Screen.Records
        navController.navigateTo(screen1)
        navController.navigateTo(screen2)

        navController.navigateBack()

        val currentScreen = navController.currentScreen.value
        assertEquals(screen1, currentScreen)
    }

    @Test
    fun `navigateBack does not navigate if back stack is empty`() {
        val screen = Screen.Library
        navController.navigateTo(screen)

        navController.navigateBack() // Navigate to previous screen
        navController.navigateBack() // Attempt to navigate back again

        val currentScreen = navController.currentScreen.value
        assertEquals(screen, currentScreen) // Should remain on the same screen
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `currentScreen emits correct values`() = runTest {
        val screen1 = Screen.Library
        val screen2 = Screen.Records
        val emittedValues = mutableListOf<Screen?>()

        // Collect the values emitted by currentScreen
        val job = launch {
            navController.currentScreen.collect { emittedValues.add(it) }
        }

        // Navigate to a new screen
        navController.navigateTo(screen1)
        advanceUntilIdle()

        navController.navigateTo(screen2)
        advanceUntilIdle()

        // Assert that the correct value was emitted
        assertEquals(listOf(screen1, screen2), emittedValues)

        // Cancel the collection job
        job.cancel()

    }
}
