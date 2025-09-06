package io.github.droidkaigi.confsched.sessions.components

import androidx.compose.runtime.Composable
import io.github.droidkaigi.confsched.sessions.grid.TimetableGridScope

data class SimpleContextMenuItem(
    val label: String,
    val onClick: () -> Unit,
)

@Composable
expect fun TimetableGridScope.ContextMenuProviderForDesktop(
    items: () -> List<SimpleContextMenuItem>,
    content: @Composable () -> Unit,
)
