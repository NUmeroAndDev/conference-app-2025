package io.github.confsched.profile

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(enabled: Boolean, onBack: () -> Unit)
