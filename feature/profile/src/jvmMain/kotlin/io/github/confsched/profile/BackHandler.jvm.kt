package io.github.confsched.profile

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // JVM Desktop doesn't have system back navigation
    // The back button in the TopAppBar should handle this case
}
