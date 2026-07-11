// App de demo: galería interactiva de todos los componentes de la librería.
// Ejecutar con: ./gradlew :demo:run
// No se publica; es solo para probar y enseñar los componentes.
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(project(":library"))
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "io.github.ricardomorarey.composeui.demo.MainKt"
    }
}
