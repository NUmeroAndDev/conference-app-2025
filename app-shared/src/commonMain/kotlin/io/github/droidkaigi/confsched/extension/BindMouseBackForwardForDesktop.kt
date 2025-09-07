package io.github.droidkaigi.confsched.extension

import androidx.compose.ui.Modifier

expect fun Modifier.bindMouseBackForwardForDesktop(
    onBackPressed: () -> Unit,
    onForwardPressed: () -> Unit,
): Modifier
