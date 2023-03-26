plugins {
    id("multiplatform-setup")
}

kotlin {

    sourceSets {
        commonMain {
            dependencies{
                arrayOf(
                    Deps.Koin.core,
                    Deps.Coroutines.core
                ).forEach(::implementation)
                api(project(Modules.Model))
            }
        }
    }
}