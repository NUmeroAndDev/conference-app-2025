package io.github.droidkaigi.confsched.droidkaigiui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.isMetaPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import kotlin.math.abs

private const val VerticalWheelDeltaThreshold = 0.01f

private enum class ZoomDirection { ZoomIn, ZoomOut }

private fun PointerEvent.verticalWheelDeltaY(): Float = changes.firstOrNull()?.scrollDelta?.y ?: 0f

/**
 * Treat very small vertical wheel deltas as "no scroll".
 *
 * Typical causes:
 * - Horizontal trackpad scroll (dx ≠ 0, dy = 0)
 * - Start/end frames (momentum tail) reporting zero delta
 * - Device/OS rounding tiny deltas down to 0
 */
private fun Float.isEffectivelyNoVerticalScroll(): Boolean = abs(this) < VerticalWheelDeltaThreshold

private fun verticalDeltaToZoomDirection(
    verticalWheelDelta: Float,
): ZoomDirection = if (verticalWheelDelta < 0f) ZoomDirection.ZoomIn else ZoomDirection.ZoomOut

private fun ZoomDirection.multiplier(
    step: Float,
): Float = when (this) {
    ZoomDirection.ZoomIn -> step
    ZoomDirection.ZoomOut -> 1f / step
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun Modifier.enableMouseWheelZoom(
    multiplyVerticalScaleBy: (Float) -> Unit,
    zoomStep: Float,
    requireModifierKey: Boolean,
): Modifier = this.onPointerEvent(
    eventType = PointerEventType.Scroll,
    pass = PointerEventPass.Initial,
) { event ->
    val mods = event.keyboardModifiers
    val wantsZoom = if (requireModifierKey) (mods.isCtrlPressed || mods.isMetaPressed) else true
    if (!wantsZoom) return@onPointerEvent

    val verticalWheelDelta = event.verticalWheelDeltaY()
    // Bail out if there's no significant vertical scroll.
    if (verticalWheelDelta.isEffectivelyNoVerticalScroll()) return@onPointerEvent

    val zoomDirection = verticalDeltaToZoomDirection(verticalWheelDelta)
    val zoomMultiplier = zoomDirection.multiplier(zoomStep)
    multiplyVerticalScaleBy(zoomMultiplier)

    event.changes.forEach { it.consume() }
}
