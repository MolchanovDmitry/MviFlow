plugins {
    id("multiplatform-setup")
}

kotlin {

    sourceSets {
        commonMain {
            dependencies {
                implementation(Deps.Coroutines.core)
            }
        }
        androidMain{
            dependencies {
                implementation(Deps.androidx_lifecycle_viewmodel_ktx)
            }
        }
    }
}