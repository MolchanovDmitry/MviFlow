plugins {
    id("multiplatform-setup")
}

kotlin {

    sourceSets {
        commonMain {
            dependencies {
                arrayOf(
                    project(Modules.Mvi),
                    project(Modules.Domain),
                    Deps.Coroutines.core,
                    Deps.Koin.core
                ).forEach(::implementation)
            }
        }

        androidMain {
            dependencies {
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
            }
        }
    }
}