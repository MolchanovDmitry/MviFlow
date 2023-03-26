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
    }
}