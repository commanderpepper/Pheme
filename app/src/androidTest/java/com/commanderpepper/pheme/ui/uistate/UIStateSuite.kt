package com.commanderpepper.pheme.ui.uistate

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    CategoryButtonUIStateTest::class,
    NewsItemUIStateTest::class,
    NewsPreviewItemUIStateTest::class
)
class UIStateSuite {
}