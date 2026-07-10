package io.github.ricardomorarey.composeui.chips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Grupo de chips de filtro con selección múltiple.
 *
 * @param options Lista de opciones a mostrar.
 * @param selectedOptions Conjunto de opciones seleccionadas.
 * @param onSelectionChange Callback con el nuevo conjunto al pulsar un chip.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipGroup(
    options: List<String>,
    selectedOptions: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            val selected = option in selectedOptions
            FilterChip(
                selected = selected,
                onClick = {
                    onSelectionChange(
                        if (selected) selectedOptions - option else selectedOptions + option,
                    )
                },
                label = { Text(option) },
            )
        }
    }
}

/**
 * Fila de chips con selección única (excluyente).
 *
 * @param options Lista de opciones a mostrar.
 * @param selectedOption Opción seleccionada actualmente (o null si ninguna).
 * @param onOptionSelected Callback con la opción pulsada.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChoiceChipRow(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            FilterChip(
                selected = option == selectedOption,
                onClick = { onOptionSelected(option) },
                label = { Text(option) },
            )
        }
    }
}
