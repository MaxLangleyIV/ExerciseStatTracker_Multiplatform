plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.compose") version "1.5.11"
    id("app.cash.sqldelight") version "2.0.1"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation("app.cash.sqldelight:android-driver:2.0.1")
        }
        iosMain.dependencies {
            implementation("app.cash.sqldelight:ios-driver:2.0.1")
        }
    }
}

android {
    namespace = "com.langley.exercisestattracker"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
dependencies {
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")
}

sqldelight {
    databases {
        create("ExerciseStatTrackerDatabase") {
            packageName.set("com.langley.exercisestattracker.database")
        }
    }
}
