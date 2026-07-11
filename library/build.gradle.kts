import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.dokka)
    alias(libs.plugins.binaryCompatibilityValidator)
}

kotlin {
    // Toda la API pública debe declarar su visibilidad explícitamente;
    // evita exponer cosas por accidente
    explicitApi()

    // Android
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    // Escritorio (Windows, macOS, Linux)
    jvm("desktop")

    // iOS (se compila desde macOS; en Windows estos targets se desactivan solos)
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    // Web (WebAssembly)
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        // Solo para tests de escritorio: permite renderizar los componentes
        // a PNG y generar las capturas del README (no se publica)
        val desktopTest by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

// Genera las capturas del README (docs/screenshots) renderizando los
// componentes con Compose Desktop. Se usa JavaExec en vez de desktopTest
// porque el worker de tests de Gradle corrompe en Windows los classpath
// con caracteres no ASCII (la carpeta del proyecto contiene "í")
tasks.register<JavaExec>("generateScreenshots") {
    group = "documentation"
    description = "Renderiza los componentes y guarda los PNG en docs/screenshots"
    dependsOn("desktopTestClasses")
    val desktopTestCompilation = kotlin.targets.getByName("desktop")
        .compilations.getByName("test")
    classpath = files(
        desktopTestCompilation.output.allOutputs,
        kotlin.targets.getByName("desktop").compilations.getByName("main").output.allOutputs,
        configurations.getByName("desktopTestRuntimeClasspath"),
    )
    mainClass.set("io.github.ricardomorarey.composeui.screenshots.ScreenshotGeneratorKt")
}

android {
    namespace = "io.github.ricardomorarey.composeui"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dokka {
    moduleName.set("compose-ui-material3")
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Solo firma si hay una clave configurada (en CI llega por variables de
    // entorno ORG_GRADLE_PROJECT_signingInMemoryKey...; nunca va en el repo)
    if (providers.gradleProperty("signingInMemoryKey").isPresent ||
        providers.gradleProperty("signing.keyId").isPresent
    ) {
        signAllPublications()
    }

    // Las coordenadas (GROUP, POM_ARTIFACT_ID, VERSION_NAME) se leen
    // automáticamente de gradle.properties

    pom {
        name.set("Compose UI Material3")
        description.set(
            "Librería de componentes reutilizables de Jetpack Compose (Material 3) " +
                "para Kotlin Multiplatform: Android, Desktop, iOS y Web."
        )
        url.set("https://github.com/ricardomorarey/librer-a-compose-ui-material3-")
        inceptionYear.set("2026")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("ricardomorarey")
                name.set("ricardomorarey")
                url.set("https://github.com/ricardomorarey")
            }
        }
        scm {
            url.set("https://github.com/ricardomorarey/librer-a-compose-ui-material3-")
            connection.set("scm:git:git://github.com/ricardomorarey/librer-a-compose-ui-material3-.git")
            developerConnection.set("scm:git:ssh://git@github.com/ricardomorarey/librer-a-compose-ui-material3-.git")
        }
    }
}
