buildscript {
    dependencies {
        val gradleVersion = libs.versions.agp.get()
        classpath("com.android.tools.build:gradle:$gradleVersion")
    }
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.kotest) apply false
    alias(libs.plugins.ksp) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}