package com.example.rickandmortywiki.ui.episodes

import android.util.Log
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

class EpisodesViewModel @AssistedInject constructor(
    private val router: Router,
    private val apiService: Api,
    private val db: DatabaseApi
) : ViewModel() {

    // TODO: make BaseViewModel open class and store exceptionHandler

    @Suppress("LogNotTimber")
    private val exceptionHandler =
        CoroutineExceptionHandler { _, e ->
            Log.e("Error!!", e.toString())
        } // make it protected instead of private

    private val _loadMoreBtnState = MutableStateFlow(View.VISIBLE)
    val loadMoreBtnState: StateFlow<Int> = _loadMoreBtnState

    val _episodesListForRecyclerView =
        MutableStateFlow<List<RecyclerViewItemDataModel>>(mutableListOf())
    val episodesListForRecyclerView: StateFlow<List<RecyclerViewItemDataModel>> =
        _episodesListForRecyclerView


    init {
        updateEpisodesDataInDB()
        onLoadMoreBtnClicked()
        // TODO: Add add AndroidWorkManagerWorker to check outdated episodes (based on lastUpdateTime + episode TTL)
        // lastUpdateTime field to db, after last update add SystemCurrentTimeMillis
        // and AndroidWorkManagerWorker to check outdated episodes (based on lastUpdateTime + episode TTL)

        // TODO: Add Last Time Updated info to the episode item (to check is the episode data update works)
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            db.episodeDao().observeEpisodes().collectLatest {
                _episodesListForRecyclerView.value =
                    it.map { episodeEntity -> RecyclerViewItemDataModel.Item(episodeEntity) } // It should be on ViewModel level (with background dispatchers)
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
        val NUMBER_OF_EPISODES_IN_PACK = 20
    }
}
