plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
}

dependencies{
    implementation("com.android.tools.build:gradle:7.3.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
}

kotlinDslPluginOptions {

}
