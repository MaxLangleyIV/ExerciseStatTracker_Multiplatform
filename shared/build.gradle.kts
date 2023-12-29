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
                jvmTarget = "17"
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
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqlite.driver)
            implementation(libs.sqldelight.coroutines.extensions)
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
            implementation(libs.sqldelight.android.driver)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activity.compose.v172)
        }
        nativeMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
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
    commonMainApi(libs.mvvm.core)
    commonMainApi(libs.mvvm.compose)
    commonMainApi(libs.mvvm.flow)
    commonMainApi(libs.mvvm.flow.compose)
}

sqldelight {
    databases {
        create("ExerciseStatTrackerDatabase") {
            packageName.set("com.langley.exercisestattracker.database")
        }
    }
}

