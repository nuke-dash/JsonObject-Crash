import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.io.ByteArrayOutputStream

// cspell:ignore getenv .pullrequest

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kotest)
    alias(libs.plugins.ksp)
    `maven-publish`
}

group = "com.company.app"
version = "1.0"

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        publishLibraryVariants("release", "debug")
    }

    val xcf = XCFramework("AppSharedLogicMultiPlatform")
    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64(),
//        tvosX64(),
//        tvosArm64(),
        tvosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "AppSharedLogicMultiPlatform"
            // set a CFBundleIdentifier
            binaryOption("bundleId", "com.company.app.appsharedlogic.multiplatform")
            xcf.add(this)
        }
    }

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)

                implementation(libs.ktor.client.android)
                implementation(libs.ktor.client.okhttp)

                implementation(libs.koin.core)
                implementation(libs.timber)
            }
        }
        val appleMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)

                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlin.coroutines)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.logging)


                implementation(libs.uuid)
                implementation(libs.uri)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
                implementation(libs.ktor.client.mock)
                implementation(libs.mockative)
                implementation(kotlin("test"))
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.kotest.runner.junit5.jvm)
                implementation(libs.mockk.android)
                implementation(libs.mockk.agent)
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
                implementation(libs.kotest.runner.junit5.jvm)
                implementation(libs.mockk.android)
                implementation(libs.mockk.agent)
            }
        }
    }

}

dependencies {
    val mockativeVersion = libs.versions.mockative.get()
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, "io.mockative:mockative-processor:$mockativeVersion")
        }

    debugImplementation(compose.uiTooling)
}

ksp {
    // avoid the need to provide a a value/unit for every field
    arg("mockative.stubsUnitByDefault", "true")
}

android {
    namespace = "com.company.app.appsharedlogic"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "35.0.0"
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

publishing {
    repositories {
        mavenLocal()
        maven {
            name = "LocalTestRepo"
            url = uri("file://${layout.buildDirectory.get()}/local-repository")
        }
    }
}
android.testOptions {
    unitTests.all {
        it.useJUnitPlatform()
    }
}