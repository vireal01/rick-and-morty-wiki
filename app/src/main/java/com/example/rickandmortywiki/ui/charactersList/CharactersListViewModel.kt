package com.example.rickandmortywiki.ui.charactersList

import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.databse.DatabaseApi
import com.example.rickandmortywiki.data.entities.EpisodeCharacterCrossRef
import com.example.rickandmortywiki.data.entities.EpisodeWithCharacters
import com.example.rickandmortywiki.navigation.Router
import com.example.rickandmortywiki.network.api.Api
import com.example.rickandmortywiki.ui.BaseViewModel
import com.example.rickandmortywiki.ui.characterInfo.CharacterInfoScreen
import com.example.rickandmortywiki.utils.mapNetworkCharacterToDataCharacterEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersListViewModel @AssistedInject constructor(
    private val router: Router,
    private val apiService: Api,
    private val db: DatabaseApi,
    @Assisted episodeId: Int
) : BaseViewModel() {

    private val _episodeWithCharacters = MutableStateFlow<EpisodeWithCharacters?>(null)
    val episodeWithCharacters: StateFlow<EpisodeWithCharacters?> = _episodeWithCharacters

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            db.episodeWithCharacterDao().observerCharactersFromEpisode(episodeId = episodeId)
                .collect{
                    _episodeWithCharacters.value = it
                }
        }

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val charactersIds = db.episodeDao().getEpisodeCharactersIds(episodeId)
            val listOfNetworkCharacters = apiService.getMultipleCharacters(charactersIds.joinToString(","))
            listOfNetworkCharacters.forEach {
                val character = mapNetworkCharacterToDataCharacterEntity(it)
                if (character != null) {
                    db.episodeWithCharacterDao().insert(EpisodeCharacterCrossRef(episodeId, character.characterId))
                    character.episodes?.forEach{
                        db.episodeWithCharacterDao().insert(EpisodeCharacterCrossRef(it.toInt(), character.characterId))
                    }
                    db.characterDao().insertCharacter(character)
                }
            }
        }
    }

    fun onViewCharacterItemClick(characterId: Int) {
        router.navigateTo(CharacterInfoScreen(characterId))
    }

    fun onBackPressed() {
        router.closeScreen()
    }

    @AssistedFactory
    interface Factory {
        fun build(episodeId: Int): CharactersListViewModel
    }
}