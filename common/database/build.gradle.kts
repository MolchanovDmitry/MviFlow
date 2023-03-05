plugins {
    kotlin("multiplatform")
    id("com.android.library")
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
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "database"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                arrayOf(
                    project(Modules.Domain),
                    Deps.Koin.core,
                    Deps.Sqldelight.runtime,
                    Deps.Sqldelight.coroutinesExt,
                ).forEach(::implementation)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting{
            dependencies {
                implementation(Deps.Sqldelight.android_driver)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "dmitry.molchanov.database"
    compileSdk = 32
    defaultConfig {
        minSdk = 28
        targetSdk = 32
    }
}