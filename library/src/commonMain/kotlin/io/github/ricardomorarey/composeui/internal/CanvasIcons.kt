package io.github.ricardomorarey.composeui.internal

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// Iconos mínimos dibujados con Canvas para no depender de material-icons
// (mantiene la librería ligera y multiplataforma).

/** Dibuja un ojo (mostrar contraseña). Si [crossed] es true, lo tacha (ocultar). */
internal fun DrawScope.drawEye(color: Color, crossed: Boolean) {
    val stroke = size.minDimension * 0.09f
    drawOval(
        color = color,
        topLeft = Offset(size.width * 0.08f, size.height * 0.28f),
        size = androidx.compose.ui.geometry.Size(size.width * 0.84f, size.height * 0.44f),
        style = Stroke(width = stroke),
    )
    drawCircle(
        color = color,
        radius = size.minDimension * 0.12f,
        center = center,
    )
    if (crossed) {
        drawLine(
            color = color,
            start = Offset(size.width * 0.15f, size.height * 0.85f),
            end = Offset(size.width * 0.85f, size.height * 0.15f),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
        )
    }
}

/** Dibuja una lupa (buscar). */
internal fun DrawScope.drawMagnifier(color: Color) {
    val stroke = size.minDimension * 0.09f
    val radius = size.minDimension * 0.28f
    val circleCenter = Offset(size.width * 0.42f, size.height * 0.42f)
    drawCircle(
        color = color,
        radius = radius,
        center = circleCenter,
        style = Stroke(width = stroke),
    )
    val handleStart = Offset(
        circleCenter.x + radius * 0.72f,
        circleCenter.y + radius * 0.72f,
    )
    drawLine(
        color = color,
        start = handleStart,
        end = Offset(size.width * 0.85f, size.height * 0.85f),
        strokeWidth = stroke,
        cap = StrokeCap.Round,
    )
}

/** Dibuja una X (cerrar / limpiar). */
internal fun DrawScope.drawClose(color: Color) {
    val stroke = size.minDimension * 0.1f
    drawLine(
        color = color,
        start = Offset(size.width * 0.2f, size.height * 0.2f),
        end = Offset(size.width * 0.8f, size.height * 0.8f),
        strokeWidth = stroke,
        cap = StrokeCap.Round,
    )
    drawLine(
        color = color,
        start = Offset(size.width * 0.8f, size.height * 0.2f),
        end = Offset(size.width * 0.2f, size.height * 0.8f),
        strokeWidth = stroke,
        cap = StrokeCap.Round,
    )
}

/** Dibuja un chevron apuntando hacia abajo (expandir). */
internal fun DrawScope.drawChevronDown(color: Color) {
    val stroke = size.minDimension * 0.1f
    drawLine(
        color = color,
        start = Offset(size.width * 0.2f, size.height * 0.38f),
        end = Offset(size.width * 0.5f, size.height * 0.66f),
        strokeWidth = stroke,
        cap = StrokeCap.Round,
    )
    drawLine(
        color = color,
        start = Offset(size.width * 0.5f, size.height * 0.66f),
        end = Offset(size.width * 0.8f, size.height * 0.38f),
        strokeWidth = stroke,
        cap = StrokeCap.Round,
    )
}

/** Crea el trazado de una estrella de 5 puntas centrada en [center]. */
internal fun starPath(center: Offset, outerRadius: Float, innerRadius: Float): Path {
    val path = Path()
    val points = 5
    for (i in 0 until points * 2) {
        val r = if (i % 2 == 0) outerRadius else innerRadius
        val angle = PI / points * i - PI / 2
        val x = center.x + (r * cos(angle)).toFloat()
        val y = center.y + (r * sin(angle)).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    return path
}
