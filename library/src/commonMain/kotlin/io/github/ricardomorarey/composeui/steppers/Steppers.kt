package io.github.ricardomorarey.composeui.steppers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.ricardomorarey.composeui.internal.drawCheck
import io.github.ricardomorarey.composeui.internal.drawMinus
import io.github.ricardomorarey.composeui.internal.drawPlus

/**
 * Selector de cantidad con botones − y +, típico de carritos de compra.
 *
 * @param value Cantidad actual.
 * @param onValueChange Callback con la nueva cantidad.
 * @param range Rango permitido; los botones se deshabilitan en los extremos.
 */
@Composable
fun QuantityStepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    range: IntRange = 0..99,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StepperButton(
            enabled = value > range.first,
            onClick = { onValueChange((value - 1).coerceIn(range)) },
        ) { color -> drawMinus(color) }
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(min = 40.dp),
        )
        StepperButton(
            enabled = value < range.last,
            onClick = { onValueChange((value + 1).coerceIn(range)) },
        ) { color -> drawPlus(color) }
    }
}

/** Botón circular de − / + del [QuantityStepper]. */
@Composable
private fun StepperButton(
    enabled: Boolean,
    onClick: () -> Unit,
    icon: DrawScope.(Color) -> Unit,
) {
    val contentColor = if (enabled) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(14.dp)) {
            icon(contentColor)
        }
    }
}

/**
 * Indicador de progreso por pasos para asistentes/wizards: círculos
 * numerados unidos por líneas, con etiqueta opcional bajo cada paso.
 * Los pasos completados muestran una marca de verificación.
 *
 * @param steps Etiquetas de los pasos, en orden.
 * @param currentStep Índice (desde 0) del paso activo.
 */
@Composable
fun StepProgressIndicator(
    steps: List<String>,
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    val primary = MaterialTheme.colorScheme.primary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val inactive = MaterialTheme.colorScheme.surfaceVariant

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        steps.forEachIndexed { index, label ->
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Connector(
                        visible = index > 0,
                        highlighted = index <= currentStep,
                        primary = primary,
                        inactive = inactive,
                        modifier = Modifier.weight(1f),
                    )
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                color = if (index <= currentStep) primary else inactive,
                                shape = CircleShape,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (index < currentStep) {
                            Canvas(modifier = Modifier.size(14.dp)) {
                                drawCheck(onPrimary)
                            }
                        } else {
                            Text(
                                text = (index + 1).toString(),
                                style = MaterialTheme.typography.labelMedium,
                                color = if (index <= currentStep) {
                                    onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            )
                        }
                    }
                    Connector(
                        visible = index < steps.lastIndex,
                        highlighted = index < currentStep,
                        primary = primary,
                        inactive = inactive,
                        modifier = Modifier.weight(1f),
                    )
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    color = if (index <= currentStep) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    modifier = Modifier.padding(top = 6.dp),
                )
            }
        }
    }
}

/** Media línea que une un círculo del [StepProgressIndicator] con el vecino. */
@Composable
private fun Connector(
    visible: Boolean,
    highlighted: Boolean,
    primary: Color,
    inactive: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(3.dp)
            .background(
                when {
                    !visible -> Color.Transparent
                    highlighted -> primary
                    else -> inactive
                },
            ),
    )
}
