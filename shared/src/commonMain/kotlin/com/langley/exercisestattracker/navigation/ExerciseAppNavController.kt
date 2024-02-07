package com.langley.exercisestattracker.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExerciseAppNavController(initialScreen: Screen = Screen.Home) {

    private val backStack = mutableListOf<Screen>()
    private val _currentScreen = MutableStateFlow(initialScreen)

    val currentScreen: StateFlow<Screen> = _currentScreen

    fun navigateTo(screen: Screen) {
        backStack.add(screen)
        _currentScreen.value = screen
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
            _currentScreen.value = backStack.last()
        }
    }
}