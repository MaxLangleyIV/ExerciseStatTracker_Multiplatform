package com.langley.exercisestattracker.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


actual fun createDataStorePreferences(
    context: Any?
): DataStore<Preferences> = createDataStoreWithDefaults(
    path = {
        val documentDirectory: NSURL = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        (requireNotNull(documentDirectory).path + "/$SETTINGS_PREFERENCES")
    }
)