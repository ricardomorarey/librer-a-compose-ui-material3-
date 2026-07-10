package io.github.ricardomorarey.composeui.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.matchParentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Pantalla completa de carga con mensaje opcional.
 *
 * @param message Texto opcional bajo el indicador de progreso.
 */
@Composable
fun FullScreenLoading(
    modifier: Modifier = Modifier,
    message: String? = null,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
        if (message != null) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}

/**
 * Superpone un velo con indicador de carga sobre [content] cuando
 * [visible] es true, bloqueando la interacción con lo que hay debajo.
 *
 * @param visible Si true, muestra el velo de carga.
 * @param content Contenido sobre el que se superpone la carga.
 */
@Composable
fun LoadingOverlay(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        content()
        if (visible) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.4f))
                    // Consume los gestos para bloquear el contenido de debajo
                    .pointerInput(Unit) {},
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

/**
 * Efecto shimmer (brillo animado) para placeholders de carga.
 *
 * Uso: `Box(Modifier.size(200.dp, 20.dp).shimmer())`
 *
 * @param cornerRadius Radio de las esquinas del rectángulo dibujado.
 */
fun Modifier.shimmer(cornerRadius: Dp = 8.dp): Modifier = composed {
    val baseColor = MaterialTheme.colorScheme.surfaceVariant
    val highlightColor = MaterialTheme.colorScheme.surface

    val transition = rememberInfiniteTransition(label = "shimmer")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerProgress",
    )

    drawWithCache {
        val width = size.width
        val start = -width + 2 * width * progress
        val brush = Brush.linearGradient(
            colors = listOf(baseColor, highlightColor, baseColor),
            start = Offset(start, 0f),
            end = Offset(start + width, size.height),
        )
        onDrawBehind {
            drawRoundRect(
                brush = brush,
                cornerRadius = CornerRadius(cornerRadius.toPx()),
            )
        }
    }
}
