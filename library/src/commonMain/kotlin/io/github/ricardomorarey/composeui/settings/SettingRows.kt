package io.github.ricardomorarey.composeui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

/**
 * Fila de ajustes con un interruptor: título, subtítulo opcional y
 * [Switch] a la derecha. Toda la fila es pulsable y accesible.
 *
 * @param title Título de la opción.
 * @param checked Estado del interruptor.
 * @param onCheckedChange Callback al cambiar el estado.
 * @param subtitle Descripción secundaria opcional.
 * @param enabled Si false, deshabilita la fila entera.
 * @param leadingContent Contenido opcional al inicio (icono, avatar...).
 */
@Composable
fun SwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    enabled: Boolean = true,
    leadingContent: (@Composable () -> Unit)? = null,
) {
    SettingRowLayout(
        title = title,
        subtitle = subtitle,
        enabled = enabled,
        leadingContent = leadingContent,
        modifier = modifier.toggleable(
            value = checked,
            enabled = enabled,
            role = Role.Switch,
            onValueChange = onCheckedChange,
        ),
    ) {
        Switch(checked = checked, onCheckedChange = null, enabled = enabled)
    }
}

/**
 * Fila de ajustes con una casilla: título, subtítulo opcional y
 * [Checkbox] a la derecha. Toda la fila es pulsable y accesible.
 *
 * @param title Título de la opción.
 * @param checked Estado de la casilla.
 * @param onCheckedChange Callback al cambiar el estado.
 * @param subtitle Descripción secundaria opcional.
 * @param enabled Si false, deshabilita la fila entera.
 * @param leadingContent Contenido opcional al inicio (icono, avatar...).
 */
@Composable
fun CheckboxRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    enabled: Boolean = true,
    leadingContent: (@Composable () -> Unit)? = null,
) {
    SettingRowLayout(
        title = title,
        subtitle = subtitle,
        enabled = enabled,
        leadingContent = leadingContent,
        modifier = modifier.toggleable(
            value = checked,
            enabled = enabled,
            role = Role.Checkbox,
            onValueChange = onCheckedChange,
        ),
    ) {
        Checkbox(checked = checked, onCheckedChange = null, enabled = enabled)
    }
}

/**
 * Grupo de opciones exclusivas con [RadioButton], una por fila.
 * Toda la fila es pulsable y accesible.
 *
 * @param options Lista de opciones a mostrar.
 * @param selectedOption Opción seleccionada actualmente (o null).
 * @param onOptionSelected Callback al seleccionar una opción.
 * @param enabled Si false, deshabilita el grupo entero.
 */
@Composable
fun RadioGroup(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(modifier = modifier) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = option == selectedOption,
                        enabled = enabled,
                        role = Role.RadioButton,
                        onClick = { onOptionSelected(option) },
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = null,
                    enabled = enabled,
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    },
                )
            }
        }
    }
}

/** Estructura común de las filas de ajustes: textos a la izquierda, control a la derecha. */
@Composable
private fun SettingRowLayout(
    title: String,
    subtitle: String?,
    enabled: Boolean,
    leadingContent: (@Composable () -> Unit)?,
    modifier: Modifier,
    control: @Composable () -> Unit,
) {
    val textColor = if (enabled) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingContent != null) {
            leadingContent()
            Spacer(Modifier.width(16.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Spacer(Modifier.width(16.dp))
        control()
    }
}
