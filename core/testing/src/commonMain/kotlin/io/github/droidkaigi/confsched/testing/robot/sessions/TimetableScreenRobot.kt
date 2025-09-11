package io.github.droidkaigi.confsched.testing.robot.sessions

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import dev.zacsweers.metro.Inject
import io.github.droidkaigi.confsched.droidkaigiui.architecture.DefaultErrorFallbackContentRetryTestTag
import io.github.droidkaigi.confsched.droidkaigiui.architecture.DefaultErrorFallbackContentTestTag
import io.github.droidkaigi.confsched.droidkaigiui.architecture.DefaultSuspenseFallbackContentTestTag
import io.github.droidkaigi.confsched.droidkaigiui.session.TimetableItemCardBookmarkButtonTestTag
import io.github.droidkaigi.confsched.droidkaigiui.session.TimetableItemCardTestTag
import io.github.droidkaigi.confsched.droidkaigiui.session.TimetableListTestTag
import io.github.droidkaigi.confsched.model.core.DroidKaigi2025Day
import io.github.droidkaigi.confsched.sessions.TimetableConferenceDayTestTag
import io.github.droidkaigi.confsched.sessions.TimetableItemDetailScreenLazyColumnTestTag
import io.github.droidkaigi.confsched.sessions.TimetableScreenContext
import io.github.droidkaigi.confsched.sessions.TimetableScreenRoot
import io.github.droidkaigi.confsched.sessions.TimetableScreenTestTag
import io.github.droidkaigi.confsched.sessions.components.TimetableGridItemTestTag
import io.github.droidkaigi.confsched.sessions.components.TimetableUiTypeChangeTestTag
import io.github.droidkaigi.confsched.sessions.grid.TimetableGridTestTag
import io.github.droidkaigi.confsched.testing.compose.TestDefaultsProvider
import io.github.droidkaigi.confsched.testing.robot.core.CaptureScreenRobot
import io.github.droidkaigi.confsched.testing.robot.core.DefaultCaptureScreenRobot
import io.github.droidkaigi.confsched.testing.robot.core.DefaultWaitRobot
import io.github.droidkaigi.confsched.testing.robot.core.WaitRobot
import io.github.droidkaigi.confsched.testing.util.onAllNodesWithTag
import kotlinx.coroutines.test.TestDispatcher

@Inject
class TimetableScreenRobot(
    private val screenContext: TimetableScreenContext,
    private val testDispatcher: TestDispatcher,
    timetableServerRobot: DefaultTimetableServerRobot,
    captureScreenRobot: DefaultCaptureScreenRobot,
    waitRobot: DefaultWaitRobot,
) : TimetableServerRobot by timetableServerRobot,
    CaptureScreenRobot by captureScreenRobot,
    WaitRobot by waitRobot {

    context(composeUiTest: ComposeUiTest)
    fun setupTimetableScreenContent() {
        composeUiTest.setContent {
            with(screenContext) {
                TestDefaultsProvider(testDispatcher) {
                    TimetableScreenRoot(
                        onSearchClick = {},
                        onTimetableItemClick = {},
                    )
                }
            }
        }
    }

    context(composeUiTest: ComposeUiTest)
    fun checkLoadingIndicatorDisplayed() {
        composeUiTest.onNodeWithTag(DefaultSuspenseFallbackContentTestTag).assertExists()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkLoadingIndicatorNotDisplayed() {
        composeUiTest.onNodeWithTag(DefaultSuspenseFallbackContentTestTag).assertDoesNotExist()
    }

    context(composeUiTest: ComposeUiTest)
    fun clickFirstSessionBookmark() {
        composeUiTest
            .onAllNodesWithTag(TimetableItemCardBookmarkButtonTestTag)
            .onFirst()
            .performClick()
        waitUntilIdle()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkTimetableListDisplayed() {
        composeUiTest.onNodeWithTag(TimetableListTestTag).assertExists()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkTimetableListItemsDisplayed() {
        composeUiTest
            .onNodeWithTag(TimetableListTestTag)
            .assertIsDisplayed()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkTimetableTabSelected(day: DroidKaigi2025Day) {
        composeUiTest
            .onNodeWithTag(TimetableConferenceDayTestTag.plus(day.ordinal))
            .assertIsSelected()
    }

    context(composeUiTest: ComposeUiTest)
    fun clickFirstSession() {
        composeUiTest
            .onAllNodesWithTag(TimetableItemCardTestTag)
            .onFirst()
            .performClick()
        waitUntilIdle()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkClickedItemsExists() {
        // TODO: Implement this method to verify that clicked items exist
    }

    context(composeUiTest: ComposeUiTest)
    fun scrollTimetable() {
        composeUiTest
            .onNodeWithTag(TimetableScreenTestTag)
            .performTouchInput {
                swipeUp()
            }
    }

    context(composeUiTest: ComposeUiTest)
    fun checkTimetableListFirstItemNotDisplayed() {
        composeUiTest
            .onAllNodesWithTag(TimetableItemCardTestTag)
            .onFirst()
            .assertIsNotDisplayed()
    }

    context(composeUiTest: ComposeUiTest)
    fun clickTimetableTab(day: DroidKaigi2025Day) {
        composeUiTest
            .onNodeWithTag(TimetableConferenceDayTestTag.plus(day.ordinal))
            .performClick()
        waitUntilIdle()
    }

    context(composeUiTest: ComposeUiTest)
    fun clickTimetableUiTypeChangeButton() {
        composeUiTest
            .onNodeWithTag(TimetableUiTypeChangeTestTag)
            .performClick()
        waitUntilIdle()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkTimetableGridDisplayed() {
        composeUiTest
            .onNodeWithTag(TimetableGridTestTag)
            .assertIsDisplayed()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkTimetableGridItemsDisplayed() {
        composeUiTest
            .onAllNodesWithTag(TimetableGridItemTestTag)
            .onFirst()
            .assertIsDisplayed()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkTimetableGridFirstItemNotDisplayed() {
        composeUiTest
            .onAllNodesWithTag(TimetableGridItemTestTag)
            .onFirst()
            .assertIsNotDisplayed()
    }

    context(composeUiTest: ComposeUiTest)
    fun checkErrorFallbackDisplayed() {
        composeUiTest.onNodeWithTag(DefaultErrorFallbackContentTestTag).assertExists()
    }

    context(composeUiTest: ComposeUiTest)
    fun clickRetryButton() {
        composeUiTest.onNodeWithTag(DefaultErrorFallbackContentRetryTestTag).performClick()
    }
}
