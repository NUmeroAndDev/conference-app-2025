package io.github.droidkaigi.confsched.naventry

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import io.github.droidkaigi.confsched.AppGraph
import io.github.droidkaigi.confsched.navigation.listDetailSceneStrategyDetailPaneMetaData
import io.github.droidkaigi.confsched.navkey.AboutItemNavKey.StaffNavKey
import io.github.droidkaigi.confsched.staff.StaffScreenRoot
import io.github.droidkaigi.confsched.staff.rememberStaffScreenContextRetained

context(appGraph: AppGraph)
fun EntryProviderBuilder<NavKey>.staffEntry(
    onBackClick: () -> Unit,
    onStaffItemClick: (url: String) -> Unit,
) {
    entry<StaffNavKey>(
        metadata = listDetailSceneStrategyDetailPaneMetaData(),
    ) {
        with(rememberStaffScreenContextRetained()) {
            StaffScreenRoot(
                onBackClick = onBackClick,
                onStaffItemClick = onStaffItemClick,
            )
        }
    }
}
