package io.github.droidkaigi.confsched.sessions.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CursorDropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import io.github.droidkaigi.confsched.sessions.grid.TimetableGridScope

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun TimetableGridScope.ContextMenuProviderForDesktop(
    items: () -> List<SimpleContextMenuItem>,
    content: @Composable (() -> Unit),
) {
    val (open, setOpen) = remember { mutableStateOf(false) }

    // TODO Improve the appearance.
    Box(
        Modifier.onPointerEvent(
            eventType = PointerEventType.Press,
            pass = PointerEventPass.Initial,
        ) { e ->
            if (e.buttons.isSecondaryPressed) {
                setOpen(true)
                e.changes.forEach { it.consume() }
            }
        },
    ) {
        content()

        CursorDropdownMenu(
            expanded = open,
            onDismissRequest = { setOpen(false) },
        ) {
            for (item in items()) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item.label,
                            color = Color.Black,
                        )
                    },
                    onClick = {
                        setOpen(false)
                        item.onClick()
                    },
                )
            }
        }
    }
}
