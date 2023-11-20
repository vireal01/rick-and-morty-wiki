package com.example.rickandmortywiki.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.rickandmortywiki.utils.actions.ScrollToBottomAction
import com.example.rickandmortywiki.utils.checkDisplayed
import com.example.rickandmortywiki.utils.click
import com.example.rickandmortywiki.utils.waitUntilAppears
import org.hamcrest.Matchers.allOf
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.utils.actions.ScrollRecyclerViewToElement

class EpisodesScreen {

    fun clickOnEpisode(episodeTag: String, episodeName: String){
        val item = onView(allOf(withChild(withText(episodeTag)), withChild(withSubstring(episodeName))))
        item.waitUntilAppears().click()
    }

    fun clickOnPenultimateEpisode(episodeTag: String, episodeName: String){
        val recyclerView = onView(allOf(withId(R.id.episodes_recycler_view), isDisplayed()))
        val item = onView(allOf(withChild(withText(episodeTag)), withChild(withSubstring(episodeName))))
        recyclerView.perform(ScrollToBottomAction())
        item.checkDisplayed()
        item.click()
    }

    fun clickOnNthEpisode(episodeTag: String, episodeName: String, episodeIndexInRecyclerView: Int){
        val recyclerView = onView(allOf(withId(R.id.episodes_recycler_view))).checkDisplayed()
        val item = onView(allOf(withChild(withText(episodeTag)), withChild(withSubstring(episodeName))))
        recyclerView.perform(ScrollRecyclerViewToElement(episodeIndexInRecyclerView))
        item.checkDisplayed()
        item.click()
    }
}

fun onEpisodesScreen(operations: EpisodesScreen.() -> Unit) {
    EpisodesScreen().also(operations)
}