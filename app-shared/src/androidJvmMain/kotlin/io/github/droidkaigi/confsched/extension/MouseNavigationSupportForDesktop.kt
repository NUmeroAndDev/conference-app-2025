package io.github.droidkaigi.confsched.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import io.github.droidkaigi.confsched.component.rememberOneStepForwardControllerForDesktop
import io.github.droidkaigi.confsched.navkey.TimetableNavKey

@Composable
@OptIn(ExperimentalComposeUiApi::class)
internal fun Modifier.mouseNavigationSupportForDesktop(
    backStack: SnapshotStateList<NavKey>,
): Modifier {
    val doForward = rememberOneStepForwardControllerForDesktop(backStack)

    return this.bindMouseBackForwardForDesktop(
        onBackPressed = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            } else {
                backStack.clear()
                backStack.add(TimetableNavKey)
            }
        },
        onForwardPressed = {
            doForward()
        },
    )
}
