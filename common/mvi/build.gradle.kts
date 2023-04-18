plugins {
    id("multiplatform-setup")
}

kotlin {

    sourceSets {
        commonMain {
            dependencies {
                arrayOf(
                    Deps.Coroutines.core,
                ).forEach(::implementation)
            }
        }
        androidMain {
            dependencies {
                arrayOf(
                    Deps.Androidx.Lifecycle.viewModelKtx,
                    Deps.Androidx.Lifecycle.runtimeKtx,
                    Deps.Androidx.fragmentKtx,
                    Deps.Androidx.activityKtx,
                    Deps.Koin.android
                ).forEach(::implementation)
            }
        }
    }
}