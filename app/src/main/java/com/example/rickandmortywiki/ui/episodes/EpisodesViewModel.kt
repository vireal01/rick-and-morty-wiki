package com.example.rickandmortywiki.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.databse.DatabaseApi
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.navigation.Router
import com.example.rickandmortywiki.network.api.Api
import com.example.rickandmortywiki.ui.charactersList.CharactersListScreen
import com.example.rickandmortywiki.utils.mapNetworkEpisodeToDataEpisodeEntity
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class EpisodesViewModel @AssistedInject constructor(
    private val router: Router,
    private val apiService: Api,
    private val db: DatabaseApi
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, e -> Timber.e(e) }

    private var nextEpisodesPage: String? = null
    private var defaultEpisodesLimit = 20

    private val _episodesList = MutableStateFlow<List<EpisodeEntity>>(mutableListOf())
    val episodesList: StateFlow<List<EpisodeEntity>> = _episodesList

    init {
        getFirstPAckOfEpisodes()
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            db.episodeDao().observeEpisodes(defaultEpisodesLimit).collectLatest {
                _episodesList.value = it
            }
        }
    }

    fun onEpisodeClick(episode: EpisodeEntity) {
        router.navigateTo(CharactersListScreen(episode.episodeId))
    }

    private fun getFirstPAckOfEpisodes() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val allEpisodes = apiService.getAllEpisodes()
//            Log.d("123", allEpisodes.info)
            allEpisodes.results?.let {
                db.episodeDao().insertAll(it.mapNotNull { episode ->
                    mapNetworkEpisodeToDataEpisodeEntity(episode)
                })
            }
            nextEpisodesPage = allEpisodes.info?.next


//            while (allEpisodes.info?.next != null) {
//                allEpisodes = apiService.getEpisodesByUrl(allEpisodes.info?.next.toString())
//                allEpisodes.results?.let {
//                    db.episodeDao().insertAll(it.mapNotNull { episode ->
//                        mapNetworkEpisodeToDataEpisodeEntity(episode)
//                    })
//                }
//            }
        }
    }

    fun onLoadMoreBtnClicked() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val nextEpisodesPack = nextEpisodesPage?.let { apiService.getEpisodesByUrl(it) }
            nextEpisodesPack?.results?.let {
                    db.episodeDao().insertAll(it.mapNotNull { episode ->
                        mapNetworkEpisodeToDataEpisodeEntity(episode)
                    })
                }

            nextEpisodesPage = nextEpisodesPack?.info?.next

            defaultEpisodesLimit += nextEpisodesPack?.results?.size ?: 0
            db.episodeDao().observeEpisodes(defaultEpisodesLimit).collectLatest {
                _episodesList.value = it
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun build(): EpisodesViewModel
    }
}
