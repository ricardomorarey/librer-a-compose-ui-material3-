package io.github.ricardomorarey.composeui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * Diálogo de confirmación con botones de confirmar y cancelar.
 *
 * @param title Título del diálogo.
 * @param message Mensaje explicativo.
 * @param onConfirm Acción al confirmar.
 * @param onDismiss Acción al cancelar o cerrar el diálogo.
 * @param confirmText Texto del botón de confirmación.
 * @param dismissText Texto del botón de cancelación.
 * @param destructive Si true, el botón de confirmar se pinta en color de error
 *   (útil para acciones como eliminar).
 */
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "Aceptar",
    dismissText: String = "Cancelar",
    destructive: Boolean = false,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmText,
                    color = if (destructive) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        },
    )
}

/**
 * Diálogo informativo con un único botón de cierre.
 *
 * @param title Título del diálogo.
 * @param message Mensaje a mostrar.
 * @param onDismiss Acción al cerrar.
 * @param buttonText Texto del botón.
 */
@Composable
fun InfoDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    buttonText: String = "Entendido",
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(buttonText)
            }
        },
    )
}
