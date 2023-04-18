plugins {
    id("multiplatform-setup")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("AppDatabase") {
        packageName = "dmitry.molchanov.database"
        sourceFolders = listOf("sqldelight")
    }
    linkSqlite = true
}

kotlin {

    sourceSets {
        commonMain {
            dependencies {
                arrayOf(
                    project(Modules.Domain),
                    project(Modules.Model),
                    Deps.Koin.core,
                    Deps.Coroutines.core,
                    Deps.Sqldelight.runtime,
                    Deps.Sqldelight.coroutinesExt,
                ).forEach(::implementation)
            }
        }

        jsMain {
            dependencies {
                arrayOf(
                    Deps.Sqldelight.jsDriver,
                    Deps.Coroutines.js,
                ).forEach(::implementation)
            }
        }
        androidMain {
            dependencies {
                implementation(Deps.Sqldelight.androidDriver)
            }
        }
    }
}