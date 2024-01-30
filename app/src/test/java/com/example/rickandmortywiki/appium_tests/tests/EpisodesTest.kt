package com.example.rickandmortywiki.appium_tests.tests

import com.example.rickandmortywiki.appium_tests.pages.EpisodesListScreen
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EpisodesTest {
    private val screen = EpisodesListScreen()

    @After
    fun threadDown() {
        screen.tearDown()
    }

    @Before
    fun setUp() {
        screen.makeDriver()
    }

    @Test
    fun checkTopAppBArTitleTest() {
        val title = "Episodes"
        with(screen) {
            checkAppbarTitle(title)
        }
    }

    @Test
    fun checkFirstEpisode() {
        val episodeTitle = "Pilot"

        with(screen) {
            checkEpisodeCardContent(episodeTitle)
            clickOnNthEpisode(1)
            checkAppbarTitle(episodeTitle)
        }
    }
}
