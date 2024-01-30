package com.example.rickandmortywiki.appium_tests.pages

import com.example.rickandmortywiki.appium_tests.AppiumBaseTest
import com.example.rickandmortywiki.ui.components.RickAndMortyTopAppBarTags
import com.example.rickandmortywiki.ui.episodes.EpisodesListTags
import io.appium.java_client.AppiumBy
import org.openqa.selenium.WebElement

@Suppress("MaximumLineLength", "MaxLineLength")
class EpisodesListScreen : AppiumBaseTest() {

    private fun getNthElementContainer(index: Int): WebElement? {
        return driver.findElement(
            AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View[$index]/android.view.View")
        )
    }

    fun checkAppbarTitle(title: String) {
        val topAppBarTitle: WebElement = driver.findElement(
            AppiumBy.accessibilityId(RickAndMortyTopAppBarTags.TopAppBarTitle)
        )

        try {
            assert(topAppBarTitle.text == title)
        } catch (e: AssertionError) {
            throw AssertionError("${topAppBarTitle.text} not equal to $title")
        }
    }

    fun checkEpisodeCardContent(episodeTitleStr: String) {

        val episodeTitle = driver.findElement(
            AppiumBy.accessibilityId(EpisodesListTags.episodeTitle)
        )

        episodeTitle.isDisplayed
        assert(episodeTitle.text == episodeTitleStr)
    }

    fun clickOnNthEpisode(episodeIndex: Int) {
        val episodeContainer = getNthElementContainer(episodeIndex)
        episodeContainer?.isDisplayed
        episodeContainer?.click()
    }
}
