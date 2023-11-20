package com.example.rickandmortywiki.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.utils.click
import com.example.rickandmortywiki.utils.waitUntilAppears


class CharactersScreen {
    fun checkCharactersListOfTheEpisodeOpen(toolbarTitle: String) {
        val toolbarTitle =
            onView(allOf(withId(R.id.charactersListToolbar), withChild(withText(toolbarTitle))))
        toolbarTitle.waitUntilAppears().click()
    }
}

fun onCharactersScreen(operator: CharactersScreen.() -> Unit) {
    CharactersScreen().also(operator)
}