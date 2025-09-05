package io.github.droidkaigi.confsched.designsystem.component

import androidx.compose.runtime.Composable

@Composable
actual fun provideSelectionContainerCustomContextMenu(
    onWebSearchClick: (encodedSelectedText: String) -> Unit,
    buildSearchUrl: (encodedSelectedText: String) -> String,
    content: @Composable () -> Unit,
) {
    // NOOP
    content()
}
