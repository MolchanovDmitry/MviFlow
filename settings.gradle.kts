pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FlowMvi"
include(":androidApp")
include(":common:database")
include(":common:domain")
include(":common:presentation")
include(":common:mvi")
