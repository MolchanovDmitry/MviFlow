plugins {
    kotlin(GradlePlugins.Kotlin.MULTIPLATFORM)
    id(GradlePlugins.Id.COMPOSE)
}

group = "dmitry.molchanov"
version = "1.0"

kotlin {

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.Mvi),
                    project(Modules.Domain),
                    project(Modules.Database),
                    project(Modules.Presentation),
                    compose.web.core,
                    compose.runtime,
                    Deps.Koin.js,
                    Deps.Coroutines.js,
                ).forEach(::implementation)
            }
        }
    }
}