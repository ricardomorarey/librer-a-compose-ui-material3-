// Build raíz: solo declara los plugins para que los módulos los apliquen.
plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.mavenPublish) apply false
}
