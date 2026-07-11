package io.github.ricardomorarey.composeui.screenshots

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import io.github.ricardomorarey.composeui.buttons.GradientButton
import io.github.ricardomorarey.composeui.buttons.LoadingButton
import io.github.ricardomorarey.composeui.buttons.SecondaryButton
import io.github.ricardomorarey.composeui.cards.ExpandableCard
import io.github.ricardomorarey.composeui.cards.InfoCard
import io.github.ricardomorarey.composeui.chips.ChoiceChipRow
import io.github.ricardomorarey.composeui.chips.FilterChipGroup
import io.github.ricardomorarey.composeui.loading.LoadingOverlay
import io.github.ricardomorarey.composeui.loading.shimmer
import io.github.ricardomorarey.composeui.misc.CounterBadge
import io.github.ricardomorarey.composeui.misc.SectionHeader
import io.github.ricardomorarey.composeui.rating.RatingBar
import io.github.ricardomorarey.composeui.states.EmptyState
import io.github.ricardomorarey.composeui.textfields.LabeledTextField
import io.github.ricardomorarey.composeui.textfields.PasswordTextField
import io.github.ricardomorarey.composeui.textfields.SearchField
import org.jetbrains.skia.EncodedImageFormat
import java.io.File
import kotlin.test.Test

/**
 * Genera las capturas de pantalla del README renderizando los componentes
 * con Compose Desktop (sin emulador). Ejecutar con:
 * `./gradlew :library:generateScreenshots`
 * Las imágenes se escriben en `docs/screenshots/`.
 */
class ScreenshotGenerator {

    private val outputDir = File("../docs/screenshots").apply { mkdirs() }

    private fun capture(
        name: String,
        widthPx: Int,
        heightPx: Int,
        content: @Composable () -> Unit,
    ) {
        val scene = ImageComposeScene(
            width = widthPx,
            height = heightPx,
            density = Density(2f),
        ) {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.padding(20.dp)) {
                        content()
                    }
                }
            }
        }
        try {
            // Renderiza varios fotogramas para que las animaciones (spinner,
            // expansión de tarjetas, shimmer) se asienten antes de capturar
            scene.render(nanoTime = 0L)
            scene.render(nanoTime = 500_000_000L)
            val image = scene.render(nanoTime = 1_000_000_000L)
            val png = requireNotNull(image.encodeToData(EncodedImageFormat.PNG)) {
                "No se pudo codificar $name a PNG"
            }
            File(outputDir, "$name.png").writeBytes(png.bytes)
        } finally {
            scene.close()
        }
    }

    @Test
    fun generateAllScreenshots() {
        capture("buttons", 900, 340) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    LoadingButton(text = "Guardar", onClick = {})
                    LoadingButton(text = "Guardar", onClick = {}, loading = true)
                }
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SecondaryButton(text = "Cancelar", onClick = {})
                    GradientButton(text = "Empezar", onClick = {})
                }
            }
        }

        capture("textfields", 900, 560) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LabeledTextField(
                    value = "hola@ejemplo.com",
                    onValueChange = {},
                    label = "Correo",
                )
                PasswordTextField(value = "secreto123", onValueChange = {})
                SearchField(value = "compose", onValueChange = {})
            }
        }

        capture("cards", 900, 500) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoCard(
                    title = "Ajustes",
                    subtitle = "Notificaciones, tema, idioma",
                    trailingContent = { CounterBadge(count = 3) },
                )
                ExpandableCard(title = "Detalles del pedido", initiallyExpanded = true) {
                    Text("Contenido que se despliega con animación")
                }
            }
        }

        capture("chips", 900, 300) {
            var selected by remember { mutableStateOf(setOf("Kotlin", "KMP")) }
            var period by remember { mutableStateOf<String?>("Semana") }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                FilterChipGroup(
                    options = listOf("Kotlin", "Compose", "KMP", "Material 3"),
                    selectedOptions = selected,
                    onSelectionChange = { selected = it },
                )
                ChoiceChipRow(
                    options = listOf("Día", "Semana", "Mes"),
                    selectedOption = period,
                    onOptionSelected = { period = it },
                )
            }
        }

        capture("rating", 900, 170) {
            RatingBar(rating = 3)
        }

        capture("misc", 900, 300) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                SectionHeader(
                    title = "Populares",
                    actionText = "Ver todo",
                    onAction = {},
                    modifier = Modifier.width(400.dp),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    CounterBadge(count = 5)
                    CounterBadge(count = 128)
                }
            }
        }

        capture("loading", 900, 460) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(Modifier.fillMaxWidth().height(20.dp).shimmer())
                Box(Modifier.fillMaxWidth(0.7f).height(20.dp).shimmer())
                LoadingOverlay(visible = true) {
                    InfoCard(
                        title = "Contenido bloqueado durante la carga",
                        subtitle = "LoadingOverlay superpone un velo con spinner",
                    )
                }
            }
        }

        capture("states", 900, 560) {
            EmptyState(
                title = "Sin resultados",
                message = "Prueba con otros filtros",
                actionText = "Limpiar filtros",
                onAction = {},
            )
        }
    }
}

// Punto de entrada para la tarea :library:generateScreenshots. Se usa JavaExec
// en lugar del task de test porque el worker de tests de Gradle corrompe los
// classpath con caracteres no ASCII en Windows (esta carpeta contiene "í")
fun main() {
    ScreenshotGenerator().generateAllScreenshots()
    println("Capturas generadas en docs/screenshots/")
}
