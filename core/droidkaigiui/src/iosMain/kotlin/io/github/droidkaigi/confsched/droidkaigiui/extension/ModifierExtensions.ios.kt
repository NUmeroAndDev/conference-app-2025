package io.github.droidkaigi.confsched.droidkaigiui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun Modifier.enableMouseWheelZoom(
    multiplyVerticalScaleBy: (Float) -> Unit,
    zoomStep: Float,
    requireModifierKey: Boolean,
): Modifier = this // NOOP
