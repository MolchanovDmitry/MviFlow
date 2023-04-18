object Deps {

    const val material = "com.google.android.material:material:1.4.0"

    object Coroutines {
        private const val version = "1.6.4"
        const val js = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }

    object Androidx {

        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        const val activityKtx = "androidx.activity:activity-ktx:1.2.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.0"

        object Lifecycle {
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-alpha03"
        }
    }

    object Koin {
        private const val koinVersion = "3.3.2"
        const val core = "io.insert-koin:koin-core:$koinVersion"
        const val js = "io.insert-koin:koin-core-js:$koinVersion"
        const val android = "io.insert-koin:koin-android:$koinVersion"
        const val compose = "io.insert-koin:koin-androidx-compose:3.4.1"
    }
    
    object Sqldelight {
        private const val sqlDelightVersion = "1.5.4"
        const val androidDriver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"
        const val runtime = "com.squareup.sqldelight:runtime:$sqlDelightVersion"
        const val coroutinesExt = "com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion"
        const val jsDriver = "com.squareup.sqldelight:sqljs-driver:$sqlDelightVersion"
    }

}