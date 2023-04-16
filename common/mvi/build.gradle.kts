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
                    Deps.androidx_lifecycle_viewmodel_ktx,
                    Deps.androidx_lifecycle_runtime_ktx,
                    Deps.androidx_fragment_ktx,
                    Deps.androidx_activity_ktx,
                    Deps.Koin.android
                ).forEach(::implementation)
            }
        }
    }
}