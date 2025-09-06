package io.github.droidkaigi.confsched.sessions.components

import androidx.compose.runtime.Composable
import io.github.droidkaigi.confsched.sessions.grid.TimetableGridScope

@Composable
actual fun TimetableGridScope.ContextMenuProviderForDesktop(
    items: () -> List<SimpleContextMenuItem>,
    content: @Composable (() -> Unit),
) {
    // NOOP
    content()
}
