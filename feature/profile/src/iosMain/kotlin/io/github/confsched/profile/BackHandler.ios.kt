package io.github.confsched.profile

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // iOS handles back navigation differently through the navigation controller
    // The back button in the TopAppBar should handle this case
}
