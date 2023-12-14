package com.langley.exercisestattracker.core.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.langley.exercisestattracker.database.ExerciseStatTrackerDatabase


actual class DatabaseDriverFactory {
   actual fun createDriver(): SqlDriver {
      return NativeSqliteDriver(
         ExerciseStatTrackerDatabase.Schema,
         "ExerciseStatTrackerDatabase.db"
      )
   }
}