plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "dmitry.molchanov.flowmvi.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "dmitry.molchanov.flowmvi.android"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    packagingOptions {
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

dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    arrayOf(
        project(Modules.Mvi),
        project(Modules.Domain),
        project(Modules.Database),
        project(Modules.Presentation),
        Deps.Koin.android,
        Deps.Koin.core
    ).forEach(::implementation)
}