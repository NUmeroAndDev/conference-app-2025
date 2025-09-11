package io.github.droidkaigi.confsched.navkey

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object AboutNavKey : NavKey

@Serializable
data object LicensesNavKey : NavKey

sealed interface AboutItemNavKey : NavKey {
    @Serializable
    data object ContributorsNavKey : AboutItemNavKey

    @Serializable
    data object SettingsNavKey : AboutItemNavKey

    @Serializable
    data object StaffNavKey : AboutItemNavKey

    @Serializable
    data object SponsorsNavKey : AboutItemNavKey
}
