package io.github.ricardomorarey.composeui.rating

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.ricardomorarey.composeui.internal.starPath

/**
 * Barra de valoración con estrellas.
 *
 * Si se proporciona [onRatingChange], las estrellas son pulsables;
 * si es null, la barra es de solo lectura.
 *
 * @param rating Valoración actual (número de estrellas llenas).
 * @param maxRating Número total de estrellas.
 * @param onRatingChange Callback al pulsar una estrella (1..maxRating), o null para solo lectura.
 * @param starSize Tamaño de cada estrella.
 * @param filledColor Color de las estrellas llenas; por defecto, primary del tema.
 * @param emptyColor Color del borde de las estrellas vacías.
 */
@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    onRatingChange: ((Int) -> Unit)? = null,
    starSize: Dp = 28.dp,
    filledColor: Color = MaterialTheme.colorScheme.primary,
    emptyColor: Color = MaterialTheme.colorScheme.outline,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (index in 1..maxRating) {
            val filled = index <= rating
            Canvas(
                modifier = Modifier
                    .size(starSize)
                    .then(
                        if (onRatingChange != null) {
                            Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) { onRatingChange(index) }
                        } else {
                            Modifier
                        },
                    ),
            ) {
                val path = starPath(
                    center = center,
                    outerRadius = size.minDimension / 2f,
                    innerRadius = size.minDimension / 5f,
                )
                if (filled) {
                    drawPath(path = path, color = filledColor)
                } else {
                    drawPath(
                        path = path,
                        color = emptyColor,
                        style = Stroke(width = size.minDimension * 0.06f),
                    )
                }
            }
        }
    }
}
