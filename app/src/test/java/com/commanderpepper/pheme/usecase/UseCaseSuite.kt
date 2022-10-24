package com.commanderpepper.pheme.usecase

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    ConvertArticleEntityToArticleInBetweenUseCaseTest::class,
    ConvertArticleEntityToNewsItemUIStateTest::class,
    ConvertISODateToStringUseCaseUnitTest::class,
    CreateArticleEntityUseCaseTest::class,
    CreateNewsPreviewItemUseCaseTest::class
)
class UseCaseSuite {
}