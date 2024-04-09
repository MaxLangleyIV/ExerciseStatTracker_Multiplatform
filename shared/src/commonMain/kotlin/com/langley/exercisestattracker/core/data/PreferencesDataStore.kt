package com.langley.exercisestattracker.core.data

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath

internal const val SETTINGS_PREFERENCES = "exercise_stat_tracker.preferences_pb"

private lateinit var dataStore: DataStore<Preferences>

internal fun createDataStoreWithDefaults(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    migrations: List<DataMigration<Preferences>> = emptyList(),
    path: () -> String,
) = PreferenceDataStoreFactory
    .createWithPath(
        corruptionHandler = corruptionHandler,
        scope = coroutineScope,
        migrations = migrations,
        produceFile = {
            path().toPath()
        }
    )

expect fun createDataStorePreferences(
    context: Any? = null
): DataStore<Preferences>

