package com.example.rickandmortywiki.ui.charactersList

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.navigation.Router
import com.example.rickandmortywiki.network.api.Api
import com.example.rickandmortywiki.network.models.Character
import com.example.rickandmortywiki.network.models.Episode
import com.example.rickandmortywiki.ui.characterInfo.CharacterInfoScreen
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class CharactersListViewModel @AssistedInject constructor(
    private val router: Router,
    private val apiService: Api
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, e -> Timber.e(e) }

    private val _charactersList = MutableStateFlow<List<Character>>(mutableListOf())
    val charactersList: StateFlow<List<Character>> = _charactersList

    private fun addCharacter(character: Character) {
        _charactersList.value = _charactersList.value + listOf(character)
    }

    private fun addNewCharacterToTheList(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val character = apiService.getCharacter(characterId)
            character.characterImageBitmap = getImageBitmap(character.image)
            character.appearsInEpisodes = getListOfEpisodesCharacterAppears(character)
            withContext(Dispatchers.Main) {
                addCharacter(character = character)
            }
        }
    }

    fun onViewCharacterItemClick(character: Character) {
        router.navigateTo(CharacterInfoScreen(character))
    }

    fun onBackPressed() {
        router.closeScreen()
    }

    fun showListOfCharactersOfEpisode(episode: EpisodeEntity) {
        _charactersList.value = emptyList()
        episode.characters?.forEach {
//            val characterId = parseCharacterIdFromUrl(it).toInt()
            addNewCharacterToTheList(it.toInt())
        }
    }

    private fun parseCharacterIdFromUrl(url: String): String {
        return url.replace("https://rickandmortyapi.com/api/character/", "")
    }

//    private fun getEpisodeData(episodeNumber: Int, character: Character): Character {
//      viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//          val episode = apiService.getEpisode(episodeNumber)
//          character.appearsInEpisodes?.add(episode)
//      }
//        return character
//    }

    private fun parseEpisodeIdFromUrl(url: String): String {
        return url.replace("https://rickandmortyapi.com/api/episode/", "")
    }


    private fun getListOfEpisodesCharacterAppears(character: Character): MutableList<Episode> {
        val appearsInEpisodes: MutableList<Episode> = mutableListOf()
        character.episodeUrl?.forEach {
            val episodeId = parseEpisodeIdFromUrl(it).toInt()
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                val episode = apiService.getEpisode(episodeId)
                appearsInEpisodes.add(episode)
            }
        }
        return appearsInEpisodes
    }

    private fun getImageBitmap(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @AssistedFactory
    interface Factory {
        fun build(): CharactersListViewModel
    }
}