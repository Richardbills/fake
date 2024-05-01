buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
        classpath("com.google.gms:google-services:4.4.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    // Firebase
    id("org.jetbrains.kotlin.kapt") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false

    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false
}