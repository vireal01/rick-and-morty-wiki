package com.example.rickandmortywiki.uitests

import com.example.rickandmortywiki.screens.onCharactersScreen
import com.example.rickandmortywiki.screens.onEpisodesScreen
import com.example.rickandmortywiki.utils.MainTest

import org.junit.Test

class EpisodesTests : MainTest() {

    @Test
    fun openEpisode(){
        val title = "Look Who's Purging Now"
        val episodeTag = "S02E09"
        onEpisodesScreen {
            Thread.sleep(500)
            clickOnEpisode(episodeTag, title)
        }

        onCharactersScreen {
            checkCharactersListOfTheEpisodeOpen(title)
        }
    }

    @Test
    fun openLastEpisode(){
        Companion.clearData()

        val title = "Interdimensional Cable 2: Tempting Fate"
        val episodeTag = "S02E08"
        onEpisodesScreen {
            Thread.sleep(500)
            clickOnPenultimateEpisode(episodeTag, title)
        }

        onCharactersScreen {
            checkCharactersListOfTheEpisodeOpen(title)
        }
        Companion.clearData()
    }

    @Test
    fun open13thEpisodeInTheList(){
        val title = "Get Schwifty"
        val episodeTag = "S02E05"
        val episodeId = 16
        onEpisodesScreen {
            Thread.sleep(500)
            clickOnNthEpisode(episodeTag, title, episodeId - 1)
        }

        onCharactersScreen {
            checkCharactersListOfTheEpisodeOpen(title)
        }
    }
}