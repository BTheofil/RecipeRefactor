package hu.tb.recipe.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tb.presentation.theme.AppTheme
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Composable
fun CustomSnackbar(
    modifier: Modifier = Modifier,
    text: String,
    progressDuration: Duration = 1.seconds
) {
    val progressAnimation = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(Unit) {
        progressAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = progressDuration.toInt(DurationUnit.MILLISECONDS)
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(snackbarShape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = snackbarShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(snackbarShape)
            .padding(top = 2.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.primary,
            progress = { progressAnimation.value }
        )
    }
}

private val snackbarShape = RoundedCornerShape(16.dp)

@Preview
@Composable
private fun CustomSnackbarPreview() {
    AppTheme {
        CustomSnackbar(
            text = "Some information goes here..."
        )
    }
}