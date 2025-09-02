package io.github.droidkaigi.confsched.droidkaigiui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isBackPressed
import androidx.compose.ui.input.pointer.isForwardPressed
import androidx.compose.ui.input.pointer.onPointerEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun Modifier.bindMouseBackForward(
    onBackPressed: () -> Unit,
    onForwardPressed: () -> Unit,
): Modifier = this.onPointerEvent(
    eventType = PointerEventType.Press,
    pass = PointerEventPass.Initial,
) { e ->
    when {
        e.buttons.isBackPressed -> {
            onBackPressed()
            e.changes.forEach { it.consume() }
        }
        e.buttons.isForwardPressed -> {
            onForwardPressed()
            e.changes.forEach { it.consume() }
        }
    }
}
