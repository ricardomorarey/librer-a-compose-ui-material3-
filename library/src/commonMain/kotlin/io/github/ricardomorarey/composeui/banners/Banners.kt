package io.github.ricardomorarey.composeui.banners

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.ricardomorarey.composeui.internal.drawCheck
import io.github.ricardomorarey.composeui.internal.drawClose
import io.github.ricardomorarey.composeui.internal.drawErrorCircle
import io.github.ricardomorarey.composeui.internal.drawInfoCircle
import io.github.ricardomorarey.composeui.internal.drawWarningTriangle

/** Severidad de un [InlineBanner]; determina el color y el icono. */
enum class BannerSeverity { Info, Success, Warning, Error }

/**
 * Aviso en línea (banner) para mensajes contextuales: información,
 * éxito, advertencia o error. Con acción y cierre opcionales.
 *
 * @param message Texto del aviso.
 * @param severity Severidad; determina color e icono.
 * @param title Título opcional en negrita sobre el mensaje.
 * @param actionText Texto de la acción opcional (por ejemplo, "Reintentar").
 * @param onAction Callback de la acción; solo se muestra si [actionText] no es null.
 * @param onDismiss Si se indica, muestra una X para descartar el banner.
 */
@Composable
fun InlineBanner(
    message: String,
    modifier: Modifier = Modifier,
    severity: BannerSeverity = BannerSeverity.Info,
    title: String? = null,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
) {
    val containerColor = when (severity) {
        BannerSeverity.Info -> MaterialTheme.colorScheme.secondaryContainer
        BannerSeverity.Success -> Color(0xFFDCEFDC)
        BannerSeverity.Warning -> Color(0xFFFFF3DC)
        BannerSeverity.Error -> MaterialTheme.colorScheme.errorContainer
    }
    val contentColor = when (severity) {
        BannerSeverity.Info -> MaterialTheme.colorScheme.onSecondaryContainer
        BannerSeverity.Success -> Color(0xFF1E4620)
        BannerSeverity.Warning -> Color(0xFF5C4400)
        BannerSeverity.Error -> MaterialTheme.colorScheme.onErrorContainer
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = containerColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Canvas(modifier = Modifier.size(22.dp)) {
            when (severity) {
                BannerSeverity.Info -> drawInfoCircle(contentColor)
                BannerSeverity.Success -> drawCheck(contentColor)
                BannerSeverity.Warning -> drawWarningTriangle(contentColor)
                BannerSeverity.Error -> drawErrorCircle(contentColor)
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = contentColor,
                )
            }
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
            )
        }
        if (actionText != null && onAction != null) {
            Spacer(Modifier.width(8.dp))
            TextButton(
                onClick = onAction,
                colors = ButtonDefaults.textButtonColors(contentColor = contentColor),
            ) {
                Text(actionText)
            }
        }
        if (onDismiss != null) {
            Spacer(Modifier.width(8.dp))
            Canvas(
                modifier = Modifier
                    .size(16.dp)
                    .clickable(onClick = onDismiss),
            ) {
                drawClose(contentColor)
            }
        }
    }
}
