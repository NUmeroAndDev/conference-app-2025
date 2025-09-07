package io.github.droidkaigi.confsched.extension

import androidx.compose.ui.Modifier

actual fun Modifier.bindMouseBackForwardForDesktop(
    onBackPressed: () -> Unit,
    onForwardPressed: () -> Unit,
): Modifier = this // NOOP
