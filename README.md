# Compose UI Material3

[![Maven Central](https://img.shields.io/maven-central/v/io.github.ricardomorarey/compose-ui-material3)](https://central.sonatype.com/artifact/io.github.ricardomorarey/compose-ui-material3)
[![Build](https://github.com/ricardomorarey/librer-a-compose-ui-material3-/actions/workflows/build.yml/badge.svg)](https://github.com/ricardomorarey/librer-a-compose-ui-material3-/actions/workflows/build.yml)
[![Docs](https://img.shields.io/badge/docs-Dokka-blue)](https://ricardomorarey.github.io/librer-a-compose-ui-material3-/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Librería de componentes reutilizables de **Jetpack Compose** con **Material 3 de Google**, construida con **Kotlin Multiplatform** y **Compose Multiplatform**: escribe la UI una vez y úsala en:

| Plataforma | Target |
|---|---|
| Android | `androidTarget` |
| Escritorio (Windows/macOS/Linux) | `jvm("desktop")` |
| iOS | `iosArm64`, `iosSimulatorArm64`, `iosX64` |
| Web | `wasmJs` |

Licencia [MIT](LICENSE) — úsala libremente en cualquier proyecto.

## Instalación

Disponible en [Maven Central](https://central.sonatype.com/artifact/io.github.ricardomorarey/compose-ui-material3) — no necesitas añadir ningún repositorio extra:

```kotlin
// build.gradle.kts (módulo)
dependencies {
    implementation("io.github.ricardomorarey:compose-ui-material3:0.1.0")
}
```

En un proyecto Kotlin Multiplatform, añádela a `commonMain`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.ricardomorarey:compose-ui-material3:0.1.0")
        }
    }
}
```

Para probar cambios locales sin publicar, puedes instalarla en tu Maven local con `./gradlew publishToMavenLocal` y consumirla añadiendo `mavenLocal()` a tus repositorios.

## Componentes

Todos usan `MaterialTheme` de Material 3, así que heredan automáticamente los colores, tipografía y formas del tema de tu app.

### Botones — `io.github.ricardomorarey.composeui.buttons`

```kotlin
// Botón con estado de carga integrado
LoadingButton(
    text = "Guardar",
    loading = isSaving,
    onClick = { viewModel.save() },
)

SecondaryButton(text = "Cancelar", onClick = { /* ... */ })

// Botón con degradado (por defecto primary -> tertiary del tema)
GradientButton(text = "Empezar", onClick = { /* ... */ })

// Acciones destructivas (usa los colores de error del tema)
DestructiveButton(text = "Eliminar", onClick = { /* ... */ })
```

<img src="docs/screenshots/buttons.png" width="450" alt="LoadingButton, SecondaryButton y GradientButton">


### Tarjetas — `...composeui.cards`

```kotlin
InfoCard(
    title = "Ajustes",
    subtitle = "Notificaciones, tema, idioma",
    onClick = { /* navegar */ },
)

ExpandableCard(title = "Detalles del pedido") {
    Text("Contenido que se despliega con animación")
}
```

<img src="docs/screenshots/cards.png" width="450" alt="InfoCard y ExpandableCard">


### Campos de texto — `...composeui.textfields`

```kotlin
LabeledTextField(
    value = email,
    onValueChange = { email = it },
    label = "Correo",
    errorMessage = if (emailValido) null else "Correo no válido",
)

PasswordTextField(value = password, onValueChange = { password = it })

SearchField(value = query, onValueChange = { query = it })
```

<img src="docs/screenshots/textfields.png" width="450" alt="LabeledTextField, PasswordTextField y SearchField">


### Chips — `...composeui.chips`

```kotlin
// Selección múltiple
FilterChipGroup(
    options = listOf("Kotlin", "Compose", "KMP"),
    selectedOptions = selected,
    onSelectionChange = { selected = it },
)

// Selección única
ChoiceChipRow(
    options = listOf("Día", "Semana", "Mes"),
    selectedOption = period,
    onOptionSelected = { period = it },
)
```

<img src="docs/screenshots/chips.png" width="450" alt="FilterChipGroup y ChoiceChipRow">


### Diálogos — `...composeui.dialogs`

```kotlin
if (showDeleteDialog) {
    ConfirmDialog(
        title = "Eliminar elemento",
        message = "Esta acción no se puede deshacer.",
        destructive = true,
        onConfirm = { viewModel.delete() },
        onDismiss = { showDeleteDialog = false },
    )
}
```

### Avisos — `...composeui.banners`

```kotlin
InlineBanner(
    message = "Hay una nueva versión disponible.",
    severity = BannerSeverity.Info, // Info, Success, Warning o Error
    actionText = "Actualizar",
    onAction = { /* ... */ },
    onDismiss = { /* muestra una X para descartar */ },
)
```

<img src="docs/screenshots/banners.png" width="450" alt="InlineBanner en sus cuatro severidades">

### Ajustes — `...composeui.settings`

```kotlin
SwitchRow(
    title = "Notificaciones",
    subtitle = "Avisos de actividad en tu cuenta",
    checked = notificationsEnabled,
    onCheckedChange = { notificationsEnabled = it },
)

CheckboxRow(
    title = "Boletín semanal",
    checked = newsletter,
    onCheckedChange = { newsletter = it },
)

RadioGroup(
    options = listOf("Claro", "Oscuro", "Sistema"),
    selectedOption = theme,
    onOptionSelected = { theme = it },
)
```

<img src="docs/screenshots/settings.png" width="450" alt="SwitchRow, CheckboxRow y RadioGroup">

### Avatares — `...composeui.avatars`

```kotlin
// Iniciales con color determinista derivado del nombre
InitialsAvatar(name = "Ada Lovelace")

// Grupo solapado; si hay más de maxVisible muestra "+N"
AvatarGroup(
    names = listOf("Ada Lovelace", "Grace Hopper", "Alan Turing", /* ... */),
    maxVisible = 4,
)
```

<img src="docs/screenshots/avatars.png" width="450" alt="InitialsAvatar y AvatarGroup">

### Steppers — `...composeui.steppers`

```kotlin
// Selector de cantidad (carritos, entradas...)
QuantityStepper(value = qty, onValueChange = { qty = it }, range = 0..99)

// Progreso de un asistente por pasos
StepProgressIndicator(
    steps = listOf("Carrito", "Envío", "Pago", "Confirmar"),
    currentStep = 2,
)
```

<img src="docs/screenshots/steppers.png" width="450" alt="QuantityStepper y StepProgressIndicator">

### Carga — `...composeui.loading`

```kotlin
FullScreenLoading(message = "Cargando…")

LoadingOverlay(visible = isLoading) {
    MiContenido()
}

// Placeholder shimmer
Box(Modifier.fillMaxWidth().height(20.dp).shimmer())
```

<img src="docs/screenshots/loading.png" width="450" alt="Placeholders shimmer y LoadingOverlay">


### Estados — `...composeui.states`

```kotlin
EmptyState(
    title = "Sin resultados",
    message = "Prueba con otros filtros",
    actionText = "Limpiar filtros",
    onAction = { /* ... */ },
)

ErrorState(
    title = "Algo salió mal",
    message = error.message,
    onRetry = { viewModel.reload() },
)
```

<img src="docs/screenshots/states.png" width="450" alt="EmptyState">


### Valoración — `...composeui.rating`

```kotlin
RatingBar(
    rating = rating,
    onRatingChange = { rating = it }, // null para solo lectura
)
```

<img src="docs/screenshots/rating.png" width="450" alt="RatingBar">


### Varios — `...composeui.misc`

```kotlin
SectionHeader(title = "Populares", actionText = "Ver todo", onAction = { /* ... */ })

CounterBadge(count = 128) // muestra "99+"

// Divisor con etiqueta centrada
LabeledDivider(text = "o continúa con")
```

<img src="docs/screenshots/misc.png" width="450" alt="SectionHeader y CounterBadge">


## App de demo

El módulo [demo/](demo/) es una galería interactiva de escritorio con todos los componentes. Para lanzarla:

```bash
./gradlew :demo:run
```

## Documentación de la API

La referencia completa (generada con Dokka a partir de los KDoc) se publica automáticamente en
**https://ricardomorarey.github.io/librer-a-compose-ui-material3-/** con cada push a `main`.
Para generarla en local: `./gradlew :library:dokkaGenerate` (queda en `library/build/dokka/html`).

## Estabilidad de la API

- La librería usa el modo [`explicitApi()`](https://kotlinlang.org/docs/api-guidelines-simplicity.html#use-explicit-api-mode) de Kotlin: toda la API pública declara su visibilidad de forma explícita.
- El [binary compatibility validator](https://github.com/Kotlin/binary-compatibility-validator) vigila que ninguna release rompa la API sin querer: los ficheros `library/api/*.api` describen la API pública y el CI falla si cambian sin actualizarse. Tras un cambio intencionado de API, ejecuta `./gradlew :library:apiDump` y revisa el diff en el commit.

## Compilar el proyecto

```bash
./gradlew :library:assemble
```

- En **Windows/Linux** se compilan Android, Desktop y Web (los targets de iOS se omiten automáticamente).
- En **macOS** se compilan todos los targets, incluido iOS.

Las capturas del README se generan renderizando los componentes con Compose Desktop (sin emulador):

```bash
./gradlew :library:generateScreenshots
```

## Publicar una versión nueva

Todo el ciclo está automatizado en el workflow [release.yml](.github/workflows/release.yml):

1. Ve a *Actions → Release → Run workflow* e indica la versión (por ejemplo `0.2.0`).
2. El workflow actualiza `VERSION_NAME` en `gradle.properties` (con commit incluido), publica en Maven Central (compilando en macOS para incluir iOS) y crea la release en GitHub con el tag `vX.Y.Z` y notas generadas.

También puede hacerse a la antigua: crear una release a mano en GitHub dispara [publish.yml](.github/workflows/publish.yml), que solo publica (en ese caso, recuerda subir antes `VERSION_NAME`).

La publicación usa el [Central Portal de Sonatype](https://central.sonatype.com) mediante el plugin de Vanniktech. **Ninguna credencial vive en este repositorio**: los workflows leen los secrets `MAVEN_CENTRAL_USERNAME`, `MAVEN_CENTRAL_PASSWORD`, `SIGNING_KEY` y `SIGNING_KEY_PASSWORD` de *Settings → Secrets and variables → Actions*.

## Seguridad

- `local.properties` (rutas locales de tu máquina) está en `.gitignore` y no se sube.
- Las claves de firma y tokens solo se leen de variables de entorno / GitHub Secrets.
- El `.gitignore` bloquea además `*.jks`, `*.keystore`, `*.gpg`, `*.asc` y similares por si acaso.

## Licencia

[MIT](LICENSE) © ricardomorarey
