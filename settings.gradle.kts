pluginManagement {
    plugins {
        kotlin("multiplatform").version("1.7.10")
    }
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenCentral()
        google()
    }
}

rootProject.name = "FlowMvi"
include(":web")
include(":mviKotlinApp")
include(":androidApp")

include(":common:database")
include(":common:domain")
include(":common:presentation")
include(":common:mvi")
include(":common:model")
