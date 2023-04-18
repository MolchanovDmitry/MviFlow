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
                arrayOf(
                    Deps.Koin.core,
                    Deps.Koin.android,
                    Deps.Androidx.Lifecycle.viewModelKtx
                ).forEach(::implementation)
            }
        }
    }
}