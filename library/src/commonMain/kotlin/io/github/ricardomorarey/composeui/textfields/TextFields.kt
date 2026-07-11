package io.github.ricardomorarey.composeui.textfields

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.github.ricardomorarey.composeui.internal.drawClose
import io.github.ricardomorarey.composeui.internal.drawEye
import io.github.ricardomorarey.composeui.internal.drawMagnifier

/**
 * Campo de texto con etiqueta y soporte de error integrado.
 *
 * @param value Valor actual.
 * @param onValueChange Callback al cambiar el texto.
 * @param label Etiqueta del campo.
 * @param errorMessage Si no es null, el campo se marca en error y muestra este mensaje.
 * @param singleLine Si true (por defecto), una sola línea.
 */
@Composable
public fun LabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(label) },
        enabled = enabled,
        singleLine = singleLine,
        isError = errorMessage != null,
        supportingText = errorMessage?.let { message -> { Text(message) } },
    )
}

/**
 * Campo de contraseña con botón para mostrar/ocultar el texto.
 *
 * @param value Valor actual.
 * @param onValueChange Callback al cambiar el texto.
 * @param label Etiqueta del campo.
 * @param errorMessage Si no es null, el campo se marca en error y muestra este mensaje.
 */
@Composable
public fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Contraseña",
    errorMessage: String? = null,
    enabled: Boolean = true,
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(label) },
        enabled = enabled,
        singleLine = true,
        isError = errorMessage != null,
        supportingText = errorMessage?.let { message -> { Text(message) } },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Canvas(modifier = Modifier.size(22.dp)) {
                    drawEye(color = iconColor, crossed = !visible)
                }
            }
        },
    )
}

/**
 * Campo de búsqueda con icono de lupa y botón para limpiar el texto.
 *
 * @param value Texto de búsqueda actual.
 * @param onValueChange Callback al cambiar el texto.
 * @param placeholder Texto de ayuda cuando el campo está vacío.
 */
@Composable
public fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar…",
    enabled: Boolean = true,
) {
    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text(placeholder) },
        enabled = enabled,
        singleLine = true,
        leadingIcon = {
            Canvas(modifier = Modifier.size(20.dp)) {
                drawMagnifier(iconColor)
            }
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        drawClose(iconColor)
                    }
                }
            }
        },
    )
}
