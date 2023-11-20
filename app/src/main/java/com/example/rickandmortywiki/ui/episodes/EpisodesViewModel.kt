package com.example.rickandmortywiki.ui.episodes

import android.view.View
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

    private val _loadMoreBtnState = MutableStateFlow<Int?>(View.VISIBLE)
    val loadMoreBtnState: StateFlow<Int?> = _loadMoreBtnState

    private var defaultEpisodesLimit = 2000

    private val _episodesList = MutableStateFlow<List<EpisodeEntity>>(mutableListOf())
    val episodesList: StateFlow<List<EpisodeEntity>> = _episodesList

    private val _numberOfEpisodesFromApi: MutableStateFlow<Int?> = MutableStateFlow(0)
    private val _numberOfEpisodesInPack: MutableStateFlow<Int?> = MutableStateFlow(0)

    init {
        updateEpisodesDataInDB()
        getFirstPackOfEpisodes()
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            db.episodeDao().observeEpisodes(defaultEpisodesLimit).collectLatest {
                _episodesList.value = it
            }
            updateLoadMoreBtnState()
        }
    }

    private fun getFirstPackOfEpisodes() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val allEpisodes = apiService.getAllEpisodes()
            _numberOfEpisodesFromApi.value = allEpisodes.info?.count
            _numberOfEpisodesInPack.value = allEpisodes.results?.size
            allEpisodes.results?.let {
                db.episodeDao().insertAll(it.mapNotNull { episode ->
                    mapNetworkEpisodeToDataEpisodeEntity(episode)
                })
            }
        }

    }

    private fun getElementsFromNextPage() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val episodesInDB = db.episodeDao().getNumberOfEpisodesInDataBase()
            if (_numberOfEpisodesFromApi.value != null && episodesInDB < _numberOfEpisodesFromApi.value!!) {
                val nextPack = episodesInDB / _numberOfEpisodesInPack.value!! + 1
                val packOfEpisodes = apiService.getEpisodesPage(nextPack)
                packOfEpisodes.results?.let {
                    db.episodeDao().insertAll(it.mapNotNull { episode ->
                        mapNetworkEpisodeToDataEpisodeEntity(episode)
                    })
                }

                if (db.episodeDao().getNumberOfEpisodesInDataBase() >= (_numberOfEpisodesFromApi.value ?: 0)) {
                    _loadMoreBtnState.value = View.GONE
                }
            }

        }
    }

    private fun updateEpisodesDataInDB() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val episodesToUpdate = db.episodeDao().getListOfAllEpisodesInDataBase()
            val episodes = apiService.getEpisodes(episodesToUpdate.joinToString(","))
            episodes.let {
                db.episodeDao().insertAll(it.mapNotNull { episode ->
                    mapNetworkEpisodeToDataEpisodeEntity(episode)
                })
            }
            updateLoadMoreBtnState()
        }
    }

    private fun updateLoadMoreBtnState() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val numberOfEpisodesInDB = db.episodeDao().getNumberOfEpisodesInDataBase()
            if (numberOfEpisodesInDB >= (_numberOfEpisodesFromApi.value ?: 0)) {
                _loadMoreBtnState.value = View.GONE
            }
        }
    }

    fun onLoadMoreBtnClicked() {
        getElementsFromNextPage()
        updateLoadMoreBtnState()
    }

    fun onEpisodeClick(episode: EpisodeEntity) {
        router.navigateTo(CharactersListScreen(episode.episodeId))
    }

    @AssistedFactory
    interface Factory {
        fun build(): EpisodesViewModel
    }
}
