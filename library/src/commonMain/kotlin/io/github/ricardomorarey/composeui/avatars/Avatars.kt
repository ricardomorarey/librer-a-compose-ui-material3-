package io.github.ricardomorarey.composeui.avatars

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

// Paleta fija para avatares: colores agradables con buen contraste
// sobre texto blanco; el color se elige de forma determinista por nombre
private val avatarPalette = listOf(
    Color(0xFF5C6BC0), // índigo
    Color(0xFF26A69A), // teal
    Color(0xFFEF6C00), // naranja
    Color(0xFF7E57C2), // violeta
    Color(0xFFC2185B), // magenta
    Color(0xFF2E7D32), // verde
    Color(0xFF0288D1), // azul
    Color(0xFF8D6E63), // marrón
)

/** Extrae las iniciales (máximo 2) de un nombre: "Ada Lovelace" -> "AL". */
private fun initialsOf(name: String): String =
    name.split(' ', '-')
        .filter { it.isNotBlank() }
        .take(2)
        .map { it.first().uppercaseChar() }
        .joinToString("")

/**
 * Avatar circular con las iniciales de un nombre. El color de fondo se
 * elige de una paleta interna de forma determinista (el mismo nombre
 * produce siempre el mismo color).
 *
 * @param name Nombre completo del que se extraen las iniciales.
 * @param size Diámetro del avatar.
 * @param containerColor Color de fondo; si es null se deriva del nombre.
 */
@Composable
public fun InitialsAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    containerColor: Color? = null,
) {
    val background = containerColor
        ?: avatarPalette[abs(name.hashCode()) % avatarPalette.size]
    Box(
        modifier = modifier
            .size(size)
            .background(color = background, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initialsOf(name),
            style = TextStyle(
                fontSize = (size.value * 0.38f).sp,
                fontWeight = FontWeight.Medium,
            ),
            color = Color.White,
        )
    }
}

/**
 * Grupo de avatares solapados. Si hay más nombres que [maxVisible],
 * muestra un último círculo con "+N".
 *
 * @param names Nombres de las personas del grupo.
 * @param maxVisible Número máximo de avatares visibles.
 * @param size Diámetro de cada avatar.
 */
@Composable
public fun AvatarGroup(
    names: List<String>,
    modifier: Modifier = Modifier,
    maxVisible: Int = 4,
    size: Dp = 40.dp,
) {
    val visible = names.take(maxVisible)
    val remaining = names.size - visible.size
    val overlap = size / 4
    val borderColor = MaterialTheme.colorScheme.surface

    Row(modifier = modifier) {
        visible.forEachIndexed { index, name ->
            InitialsAvatar(
                name = name,
                size = size,
                modifier = Modifier
                    .offset(x = -overlap * index)
                    .border(width = 2.dp, color = borderColor, shape = CircleShape),
            )
        }
        if (remaining > 0) {
            Box(
                modifier = Modifier
                    .offset(x = -overlap * visible.size)
                    .size(size)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape,
                    )
                    .border(width = 2.dp, color = borderColor, shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "+$remaining",
                    style = TextStyle(
                        fontSize = (size.value * 0.32f).sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
