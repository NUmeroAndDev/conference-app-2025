package io.github.droidkaigi.confsched.sessions

import androidx.compose.ui.test.runComposeUiTest
import io.github.droidkaigi.confsched.model.core.DroidKaigi2025Day
import io.github.droidkaigi.confsched.testing.annotations.ComposeTest
import io.github.droidkaigi.confsched.testing.annotations.RunWith
import io.github.droidkaigi.confsched.testing.annotations.UiTestRunner
import io.github.droidkaigi.confsched.testing.behavior.describeBehaviors
import io.github.droidkaigi.confsched.testing.behavior.execute
import io.github.droidkaigi.confsched.testing.di.createTimetableScreenTestGraph
import io.github.droidkaigi.confsched.testing.robot.sessions.TimetableScreenRobot
import io.github.droidkaigi.confsched.testing.robot.sessions.TimetableServerRobot.ServerStatus

@RunWith(UiTestRunner::class)
class TimetableScreenTest {
    val testAppGraph = createTimetableScreenTestGraph()

    @ComposeTest
    fun runTest() {
        describedBehaviors.forEach { behavior ->
            val robot = testAppGraph.timetableScreenRobotProvider()
            runComposeUiTest {
                behavior.execute(robot)
            }
        }
    }

    val describedBehaviors = describeBehaviors<TimetableScreenRobot>("TimetableScreen") {
        describe("when server is operational") {
            doIt {
                setupTimetableServer(ServerStatus.Operational)
                setupTimetableScreenContent()
            }
            itShould("show loading indicator") {
                captureScreenWithChecks {
                    checkLoadingIndicatorDisplayed()
                }
            }
            describe("after loading") {
                doIt {
                    waitFor5Seconds()
                }
                itShould("show timetable items") {
                    captureScreenWithChecks {
                        checkLoadingIndicatorNotDisplayed()
                        checkTimetableListDisplayed()
                        checkTimetableListItemsDisplayed()
                        checkTimetableTabSelected(DroidKaigi2025Day.ConferenceDay1)
                    }
                }
                describe("click first session bookmark") {
                    doIt {
                        clickFirstSessionBookmark()
                    }
                }
                describe("click first session") {
                    doIt {
                        clickFirstSession()
                    }
                    itShould("show session detail") {
                        checkClickedItemsExists()
                    }
                }
                describe("scroll timetable") {
                    doIt {
                        scrollTimetable()
                    }
                    itShould("first session is not displayed") {
                        captureScreenWithChecks(checks = {
                            checkTimetableListFirstItemNotDisplayed()
                        })
                    }
                }
                describe("click conference day2 tab") {
                    doIt {
                        clickTimetableTab(DroidKaigi2025Day.ConferenceDay2)
                    }
                    itShould("change displayed day") {
                        captureScreenWithChecks(checks = {
                            checkTimetableListItemsDisplayed()
                        })
                    }
                }
                describe("click timetable ui type change") {
                    doIt {
                        clickTimetableUiTypeChangeButton()
                    }
                    itShould("change timetable ui type") {
                        captureScreenWithChecks(checks = {
                            checkTimetableGridDisplayed()
                            checkTimetableGridItemsDisplayed()
                        })
                    }
                    describe("scroll timetable") {
                        doIt {
                            scrollTimetable()
                        }
                        // TODO: Fix fail test - Grid scroll behavior needs investigation
//                        itShould("first session is not displayed") {
//                            captureScreenWithChecks(checks = {
//                                checkTimetableGridFirstItemNotDisplayed()
//                            })
//                        }
                    }
                    describe("click conference day2 tab") {
                        doIt {
                            clickTimetableTab(DroidKaigi2025Day.ConferenceDay2)
                        }
                        itShould("change displayed day") {
                            captureScreenWithChecks(checks = {
                                checkTimetableGridItemsDisplayed()
                            })
                        }
                    }
                }
            }
        }
        describe("when server is error") {
            doIt {
                setupTimetableServer(ServerStatus.Error)
                setupTimetableScreenContent()
            }
            itShould("show loading indicator") {
                captureScreenWithChecks {
                    checkLoadingIndicatorDisplayed()
                }
            }
            describe("after loading") {
                doIt {
                    waitFor5Seconds()
                }
                itShould("show error message") {
                    captureScreenWithChecks {
                        checkErrorFallbackDisplayed()
                    }
                }
                describe("click retry after server gets operational") {
                    doIt {
                        setupTimetableServer(ServerStatus.Operational)
                        clickRetryButton()
                    }
                    describe("after waiting for 5 seconds") {
                        doIt {
                            waitFor5Seconds()
                        }
                        itShould("show timetable items") {
                            captureScreenWithChecks {
                                checkLoadingIndicatorNotDisplayed()
                                checkTimetableListDisplayed()
                            }
                        }
                    }
                }
            }
        }
    }
}
