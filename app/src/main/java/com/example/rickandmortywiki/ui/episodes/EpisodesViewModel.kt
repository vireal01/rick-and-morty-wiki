package com.example.rickandmortywiki.ui.episodes
import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.databse.DatabaseApi
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.navigation.Router
import com.example.rickandmortywiki.network.api.Api
import com.example.rickandmortywiki.ui.BaseViewModel
import com.example.rickandmortywiki.ui.charactersList.CharactersListScreen
import com.example.rickandmortywiki.utils.mapNetworkEpisodeToDataEpisodeEntity
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodesViewModel @AssistedInject constructor(
    private val router: Router,
    private val apiService: Api,
    private val db: DatabaseApi
) : BaseViewModel() {

    private val _loadMoreBtnState = MutableStateFlow(View.VISIBLE)
    val loadMoreBtnState: StateFlow<Int> = _loadMoreBtnState

    private val _episodesList = MutableStateFlow<List<EpisodeEntity>>(mutableListOf())
    val episodesList: StateFlow<List<EpisodeEntity>> = _episodesList

    init {
        updateEpisodesDataInDB()
        onLoadMoreBtnClicked()
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            db.episodeDao().observeEpisodes().collectLatest {
                _episodesList.value = it
            }
        }
    }

    @Suppress("LogNotTimber")
    private fun getElementsFromNextPage() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _loadMoreBtnState.value = View.GONE

            val episodesInDB = db.episodeDao().getNumberOfEpisodesInDataBase()
            val nextPack = episodesInDB / NUMBER_OF_EPISODES_IN_PACK + 1

            val packOfEpisodes = apiService.getEpisodesPage(nextPack)

            packOfEpisodes.results.let {
                if (it != null) {
                    db.episodeDao().insertAll(it.mapNotNull { episode ->
                        mapNetworkEpisodeToDataEpisodeEntity(episode)
                    })
                }
            }

            if (packOfEpisodes.results?.size == NUMBER_OF_EPISODES_IN_PACK) {
                _loadMoreBtnState.value = View.VISIBLE
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
        }
    }

    fun onLoadMoreBtnClicked() {
        getElementsFromNextPage()
    }

    fun onEpisodeClick(episode: EpisodeEntity) {
        router.navigateTo(CharactersListScreen(episode.episodeId))
    }

    @AssistedFactory
    interface Factory {
        fun build(): EpisodesViewModel
    }

    companion object {
        const val NUMBER_OF_EPISODES_IN_PACK = 20
    }
}
