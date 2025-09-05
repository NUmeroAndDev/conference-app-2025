package io.github.droidkaigi.confsched.designsystem.component

import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ContextMenuState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.LocalTextContextMenu
import androidx.compose.foundation.text.TextContextMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import io.github.droidkaigi.confsched.designsystem.DesignsystemRes
import io.github.droidkaigi.confsched.designsystem.web_search
import org.jetbrains.compose.resources.stringResource
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun provideSelectionContainerCustomContextMenu(
    onWebSearchClick: (encodedSelectedText: String) -> Unit,
    buildSearchUrl: (encodedSelectedText: String) -> String,
    content: @Composable () -> Unit,
) {
    val baseContextMenu = LocalTextContextMenu.current

    CompositionLocalProvider(
        LocalTextContextMenu provides object : TextContextMenu {
            @Composable
            override fun Area(
                textManager: TextContextMenu.TextManager,
                state: ContextMenuState,
                content: @Composable () -> Unit,
            ) {
                val webSearchLabel = stringResource(DesignsystemRes.string.web_search)

                ContextMenuDataProvider(
                    items = {
                        val selected = textManager.selectedText.toString().trim()
                        if (selected.isNotEmpty()) {
                            listOf(
                                ContextMenuItem(webSearchLabel) {
                                    val encoded = URLEncoder.encode(
                                        selected,
                                        StandardCharsets.UTF_8.toString(),
                                    )
                                    val url = buildSearchUrl(encoded)
                                    onWebSearchClick(url)
                                },
                            )
                        } else {
                            emptyList()
                        }
                    },
                ) {
                    baseContextMenu.Area(textManager, state, content)
                }
            }
        },
        content = content,
    )
}
