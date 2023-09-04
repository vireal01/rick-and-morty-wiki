package com.example.rickandmortywiki.ui.episodes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.navigation.Router
import com.example.rickandmortywiki.network.api.Api
import com.example.rickandmortywiki.network.models.Episode
import com.example.rickandmortywiki.ui.charactersList.CharactersListScreen
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class EpisodesViewModel @AssistedInject constructor(
    private val router: Router,
    private val apiService: Api
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, e -> Timber.e(e) }

    private val _episodesList = MutableStateFlow<List<Episode>>(mutableListOf())
    val episodesList: StateFlow<List<Episode>> = _episodesList

    fun onEpisodeClick(episode: Episode) {
        router.navigateTo(CharactersListScreen(episode))
    }

    private fun updateEpisodesList(listOfEpisodes: List<Episode>) {
        _episodesList.value = listOfEpisodes
    }


    fun getAllEpisodesData() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val allEpisodes = apiService.getAllEpisodes()
            allEpisodes.results?.let { updateEpisodesList(it) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun build(): EpisodesViewModel
    }
}