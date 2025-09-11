package io.github.droidkaigi.confsched.naventry

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import io.github.droidkaigi.confsched.AppGraph
import io.github.droidkaigi.confsched.navigation.listDetailSceneStrategyDetailPaneMetaData
import io.github.droidkaigi.confsched.navkey.AboutItemNavKey.SponsorsNavKey
import io.github.droidkaigi.confsched.sponsors.SponsorScreenRoot
import io.github.droidkaigi.confsched.sponsors.rememberSponsorsScreenContextRetained

context(appGraph: AppGraph)
fun EntryProviderBuilder<NavKey>.sponsorsEntry(
    onBackClick: () -> Unit,
    onSponsorClick: (sponsorUrl: String) -> Unit,
) {
    entry<SponsorsNavKey>(
        metadata = listDetailSceneStrategyDetailPaneMetaData(),
    ) {
        with(rememberSponsorsScreenContextRetained()) {
            SponsorScreenRoot(
                onBackClick = onBackClick,
                onSponsorClick = onSponsorClick,
            )
        }
    }
}
