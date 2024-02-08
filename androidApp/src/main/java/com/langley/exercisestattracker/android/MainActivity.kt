package com.langley.exercisestattracker.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import com.langley.exercisestattracker.App
import com.langley.exercisestattracker.di.AppModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                isDarkTheme = isSystemInDarkTheme(),
                AppModule(LocalContext.current.applicationContext)
            )
        }
    }
}

