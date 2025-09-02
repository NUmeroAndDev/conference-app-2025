package io.github.droidkaigi.confsched.droidkaigiui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun Modifier.bindMouseBackForward(
    onBackPressed: () -> Unit,
    onForwardPressed: () -> Unit,
): Modifier = this // NOOP
