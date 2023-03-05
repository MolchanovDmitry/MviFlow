plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

android {
    namespace = "dmitry.molchanov.mvi_kotlin_app"
    compileSdk = 33

    defaultConfig {
        applicationId = "dmitry.molchanov.mvi_kotlin_app"
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
}

dependencies {

    arrayOf(
        project(Modules.Database)
    ).forEach(::implementation)


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    implementation("com.arkivanov.mvikotlin:mvikotlin:3.1.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.1.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-timetravel:3.1.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-logging:3.1.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.1.0")
}