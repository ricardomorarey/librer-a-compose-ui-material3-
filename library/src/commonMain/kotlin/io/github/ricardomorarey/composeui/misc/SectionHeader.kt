package io.github.ricardomorarey.composeui.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Cabecera de sección con título y acción opcional a la derecha
 * (por ejemplo, "Ver todo").
 *
 * @param title Título de la sección.
 * @param actionText Texto de la acción opcional.
 * @param onAction Callback de la acción; solo se muestra si [actionText] no es null.
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
        )
        if (actionText != null && onAction != null) {
            TextButton(onClick = onAction) {
                Text(actionText)
            }
        }
    }
}

/**
 * Divisor horizontal con una etiqueta centrada, típico para separar
 * secciones ("o continúa con", fechas en un chat, etc.).
 *
 * @param text Etiqueta que se muestra en el centro.
 */
@Composable
fun LabeledDivider(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 12.dp),
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}

/**
 * Insignia circular con un contador, típica para carritos o notificaciones.
 * Si [count] supera [maxCount] se muestra "99+" (o el máximo configurado).
 *
 * @param count Número a mostrar.
 * @param maxCount A partir de este valor se muestra "N+".
 * @param containerColor Color de fondo; por defecto, error del tema.
 * @param contentColor Color del texto.
 */
@Composable
fun CounterBadge(
    count: Int,
    modifier: Modifier = Modifier,
    maxCount: Int = 99,
    containerColor: Color = MaterialTheme.colorScheme.error,
    contentColor: Color = MaterialTheme.colorScheme.onError,
) {
    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 20.dp, minHeight = 20.dp)
            .background(color = containerColor, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (count > maxCount) "$maxCount+" else count.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
        )
    }
}
