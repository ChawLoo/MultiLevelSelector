@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    kotlin("android") version libs.versions.kotlin.get() apply false
    alias(libs.plugins.kotlin.serialization)
}

task<Delete>("clean") {
    group = "build"
    delete(rootProject.buildDir)
}