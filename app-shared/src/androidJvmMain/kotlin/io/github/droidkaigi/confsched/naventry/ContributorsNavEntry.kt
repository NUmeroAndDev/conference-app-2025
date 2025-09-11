package io.github.droidkaigi.confsched.naventry

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import io.github.droidkaigi.confsched.AppGraph
import io.github.droidkaigi.confsched.contributors.ContributorsScreenRoot
import io.github.droidkaigi.confsched.contributors.rememberContributorsScreenContextRetained
import io.github.droidkaigi.confsched.navigation.listDetailSceneStrategyDetailPaneMetaData
import io.github.droidkaigi.confsched.navkey.AboutItemNavKey.ContributorsNavKey

context(appGraph: AppGraph)
fun EntryProviderBuilder<NavKey>.contributorsEntry(
    onBackClick: () -> Unit,
    onContributorClick: (profileUrl: String) -> Unit,
) {
    entry<ContributorsNavKey>(
        metadata = listDetailSceneStrategyDetailPaneMetaData(),
    ) {
        with(rememberContributorsScreenContextRetained()) {
            ContributorsScreenRoot(
                onBackClick = onBackClick,
                onContributorClick = onContributorClick,
            )
        }
    }
}
