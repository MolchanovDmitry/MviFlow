object Deps {

    const val androidx_core_ktx = "androidx.core:core-ktx:1.6.0"
    const val androidx_appcompat = "androidx.appcompat:appcompat:1.3.0"

    // Design
    const val material = "com.google.android.material:material:1.4.0"

    // for by viewModels and result api
    const val androidx_activity_ktx = "androidx.activity:activity-ktx:1.2.0"
    const val androidx_fragment_ktx = "androidx.fragment:fragment-ktx:1.3.0"

    object Kotlinx {
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"
    }

    // kotlinx
    object Coroutines {
        private const val version = "1.6.3-native-mt"
        const val js = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }

    object Serialization {
        const val kotlinx_core = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.1"
        const val kotlinx_json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"
    }

    // Lifecycle
    const val androidx_lifecycle_runtime_ktx =
        "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
    const val androidx_lifecycle_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-alpha03"

    /* Compose */
    const val compose_version = "1.3.1"

    const val compose_activity = "androidx.activity:activity-compose:1.3.1"
    const val compose_ui = "androidx.compose.ui:ui:$compose_version"

    // Tooling support (Previews, etc.)
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling:$compose_version"

    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    const val compose_foundation = "androidx.compose.foundation:foundation:$compose_version"

    // Material Design
    const val compose_material = "androidx.compose.material:material:$compose_version"

    // Material design icons
    const val compose_icons_core = "androidx.compose.material:material-icons-core:$compose_version"
    const val compose_icons_extended =
        "androidx.compose.material:material-icons-extended:$compose_version"

    // Integration with ViewModels
    const val compose_view_model = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"

    const val compose_navigation = "androidx.navigation:navigation-compose:2.4.0-rc01"

    //Koin
    object Koin {
        private const val koin_version = "3.3.2"
        const val core = "io.insert-koin:koin-core:$koin_version"
        const val js = "io.insert-koin:koin-core-js:$koin_version"
        const val android = "io.insert-koin:koin-android:$koin_version"
        const val compose = "io.insert-koin:koin-androidx-compose:3.4.1"
    }

    // Sql
    object Sqldelight {
        private const val sqlDelightVersion = "1.5.4"
        const val android_driver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"
        const val runtime = "com.squareup.sqldelight:runtime:$sqlDelightVersion"
        const val coroutinesExt = "com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion"
        const val js_driver = "com.squareup.sqldelight:sqljs-driver:$sqlDelightVersion"
    }

}