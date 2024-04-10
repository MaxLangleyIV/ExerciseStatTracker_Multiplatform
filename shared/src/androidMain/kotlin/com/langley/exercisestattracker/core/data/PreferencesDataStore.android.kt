package com.langley.exercisestattracker.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual fun createDataStorePreferences(
    context: Any?
): DataStore<Preferences> = createDataStoreWithDefaults(
    path = {
        File((context as Context).filesDir, "datastore/$SETTINGS_PREFERENCES").path
    }
)