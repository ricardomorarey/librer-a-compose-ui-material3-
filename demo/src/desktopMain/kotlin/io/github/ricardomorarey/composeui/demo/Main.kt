package io.github.ricardomorarey.composeui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import io.github.ricardomorarey.composeui.avatars.AvatarGroup
import io.github.ricardomorarey.composeui.avatars.InitialsAvatar
import io.github.ricardomorarey.composeui.banners.BannerSeverity
import io.github.ricardomorarey.composeui.banners.InlineBanner
import io.github.ricardomorarey.composeui.buttons.DestructiveButton
import io.github.ricardomorarey.composeui.buttons.GradientButton
import io.github.ricardomorarey.composeui.buttons.LoadingButton
import io.github.ricardomorarey.composeui.buttons.SecondaryButton
import io.github.ricardomorarey.composeui.cards.ExpandableCard
import io.github.ricardomorarey.composeui.cards.InfoCard
import io.github.ricardomorarey.composeui.chips.ChoiceChipRow
import io.github.ricardomorarey.composeui.chips.FilterChipGroup
import io.github.ricardomorarey.composeui.dialogs.ConfirmDialog
import io.github.ricardomorarey.composeui.dialogs.InfoDialog
import io.github.ricardomorarey.composeui.loading.LoadingOverlay
import io.github.ricardomorarey.composeui.loading.shimmer
import io.github.ricardomorarey.composeui.misc.CounterBadge
import io.github.ricardomorarey.composeui.misc.LabeledDivider
import io.github.ricardomorarey.composeui.misc.SectionHeader
import io.github.ricardomorarey.composeui.rating.RatingBar
import io.github.ricardomorarey.composeui.settings.CheckboxRow
import io.github.ricardomorarey.composeui.settings.RadioGroup
import io.github.ricardomorarey.composeui.settings.SwitchRow
import io.github.ricardomorarey.composeui.states.EmptyState
import io.github.ricardomorarey.composeui.steppers.QuantityStepper
import io.github.ricardomorarey.composeui.steppers.StepProgressIndicator
import io.github.ricardomorarey.composeui.textfields.LabeledTextField
import io.github.ricardomorarey.composeui.textfields.PasswordTextField
import io.github.ricardomorarey.composeui.textfields.SearchField
import kotlinx.coroutines.delay

fun main() = singleWindowApplication(
    title = "Compose UI Material3 — Demo",
    state = WindowState(width = 560.dp, height = 900.dp),
) {
    MaterialTheme {
        DemoGallery()
    }
}

@Composable
fun DemoGallery() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // ---- Botones ----
            SectionHeader(title = "Botones")
            var saving by remember { mutableStateOf(false) }
            LaunchedEffect(saving) {
                if (saving) {
                    delay(2000)
                    saving = false
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LoadingButton(text = "Guardar", loading = saving, onClick = { saving = true })
                SecondaryButton(text = "Cancelar", onClick = {})
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                GradientButton(text = "Empezar", onClick = {})
                DestructiveButton(text = "Eliminar", onClick = {})
            }

            // ---- Campos de texto ----
            SectionHeader(title = "Campos de texto")
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var query by remember { mutableStateOf("") }
            LabeledTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo",
                errorMessage = if (email.isNotEmpty() && '@' !in email) "Correo no válido" else null,
            )
            PasswordTextField(value = password, onValueChange = { password = it })
            SearchField(value = query, onValueChange = { query = it })

            // ---- Tarjetas ----
            SectionHeader(title = "Tarjetas")
            InfoCard(
                title = "Ajustes",
                subtitle = "Notificaciones, tema, idioma",
                trailingContent = { CounterBadge(count = 3) },
            )
            ExpandableCard(title = "Detalles del pedido") {
                Text("Contenido que se despliega con animación")
            }

            // ---- Chips ----
            SectionHeader(title = "Chips")
            var tags by remember { mutableStateOf(setOf("Kotlin")) }
            var period by remember { mutableStateOf<String?>("Semana") }
            FilterChipGroup(
                options = listOf("Kotlin", "Compose", "KMP", "Material 3"),
                selectedOptions = tags,
                onSelectionChange = { tags = it },
            )
            ChoiceChipRow(
                options = listOf("Día", "Semana", "Mes"),
                selectedOption = period,
                onOptionSelected = { period = it },
            )

            // ---- Avisos ----
            SectionHeader(title = "Avisos")
            var showErrorBanner by remember { mutableStateOf(true) }
            InlineBanner(
                message = "Hay una nueva versión disponible.",
                severity = BannerSeverity.Info,
                actionText = "Actualizar",
                onAction = {},
            )
            InlineBanner(
                message = "Los cambios se han guardado.",
                severity = BannerSeverity.Success,
            )
            InlineBanner(
                title = "Sin conexión",
                message = "Se reintentará automáticamente.",
                severity = BannerSeverity.Warning,
            )
            if (showErrorBanner) {
                InlineBanner(
                    message = "No se pudo completar el pago.",
                    severity = BannerSeverity.Error,
                    onDismiss = { showErrorBanner = false },
                )
            }

            // ---- Ajustes ----
            SectionHeader(title = "Ajustes")
            var notifications by remember { mutableStateOf(true) }
            var newsletter by remember { mutableStateOf(false) }
            var theme by remember { mutableStateOf<String?>("Sistema") }
            SwitchRow(
                title = "Notificaciones",
                subtitle = "Avisos de actividad en tu cuenta",
                checked = notifications,
                onCheckedChange = { notifications = it },
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

            // ---- Avatares ----
            SectionHeader(title = "Avatares")
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                InitialsAvatar(name = "Ada Lovelace")
                AvatarGroup(
                    names = listOf(
                        "Ada Lovelace",
                        "Grace Hopper",
                        "Alan Turing",
                        "Margaret Hamilton",
                        "Katherine Johnson",
                        "Dennis Ritchie",
                    ),
                )
            }

            // ---- Steppers ----
            SectionHeader(title = "Steppers")
            var qty by remember { mutableStateOf(1) }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                QuantityStepper(value = qty, onValueChange = { qty = it })
                CounterBadge(count = qty)
            }
            var wizardStep by remember { mutableStateOf(1) }
            StepProgressIndicator(
                steps = listOf("Carrito", "Envío", "Pago", "Confirmar"),
                currentStep = wizardStep,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SecondaryButton(
                    text = "Anterior",
                    onClick = { if (wizardStep > 0) wizardStep-- },
                )
                SecondaryButton(
                    text = "Siguiente",
                    onClick = { if (wizardStep < 3) wizardStep++ },
                )
            }

            // ---- Valoración ----
            SectionHeader(title = "Valoración")
            var rating by remember { mutableStateOf(3) }
            RatingBar(rating = rating, onRatingChange = { rating = it })

            // ---- Diálogos ----
            SectionHeader(title = "Diálogos")
            var showConfirm by remember { mutableStateOf(false) }
            var showInfo by remember { mutableStateOf(false) }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SecondaryButton(text = "ConfirmDialog", onClick = { showConfirm = true })
                SecondaryButton(text = "InfoDialog", onClick = { showInfo = true })
            }
            if (showConfirm) {
                ConfirmDialog(
                    title = "Eliminar elemento",
                    message = "Esta acción no se puede deshacer.",
                    destructive = true,
                    onConfirm = { showConfirm = false },
                    onDismiss = { showConfirm = false },
                )
            }
            if (showInfo) {
                InfoDialog(
                    title = "Acerca de",
                    message = "Galería de componentes de compose-ui-material3.",
                    onDismiss = { showInfo = false },
                )
            }

            // ---- Carga ----
            SectionHeader(title = "Carga")
            var overlayVisible by remember { mutableStateOf(false) }
            Box(Modifier.fillMaxWidth().height(20.dp).shimmer())
            Box(Modifier.fillMaxWidth(0.7f).height(20.dp).shimmer())
            SwitchRow(
                title = "Mostrar LoadingOverlay",
                checked = overlayVisible,
                onCheckedChange = { overlayVisible = it },
            )
            LoadingOverlay(visible = overlayVisible) {
                InfoCard(
                    title = "Contenido bloqueado durante la carga",
                    subtitle = "Activa el interruptor de arriba",
                )
            }

            // ---- Varios ----
            LabeledDivider(text = "o continúa con")

            // ---- Estados ----
            SectionHeader(title = "Estados")
            Box(modifier = Modifier.height(260.dp)) {
                EmptyState(
                    title = "Sin resultados",
                    message = "Prueba con otros filtros",
                    actionText = "Limpiar filtros",
                    onAction = {},
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
