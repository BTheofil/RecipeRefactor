package hu.tb.recipe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import hu.tb.core.domain.product.Product
import hu.tb.presentation.theme.AppTheme
import hu.tb.presentation.theme.Icon
import hu.tb.recipe.presentation.components.preview.ExampleProductParameterProvider

@Composable
fun SuggestionSection(
    modifier: Modifier = Modifier,
    suggestions: List<Product>,
    onChipClick: (productName: String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Suggestions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .verticalScroll(
                    state = rememberScrollState()
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            suggestions.forEach { product ->
                ElevatedSuggestionChip(
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    onClick = { onChipClick(product.name) },
                    label = {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(Icon.add),
                            contentDescription = "add product icon",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                )
            }
        }
        HorizontalDivider(
            Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview
@Composable
private fun SuggestionSectionPreview(
    @PreviewParameter(ExampleProductParameterProvider::class) mockProducts: List<Product>
) {
    AppTheme {
        SuggestionSection(
            suggestions = mockProducts,
            onChipClick = {}
        )
    }
}