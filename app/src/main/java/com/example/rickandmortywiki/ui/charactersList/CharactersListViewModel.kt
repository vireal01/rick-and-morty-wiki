package com.example.rickandmortywiki.ui.charactersList

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.databse.AppDatabase
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.data.entities.EpisodeCharacterCrossRef
import com.example.rickandmortywiki.data.entities.EpisodeWithCharacters
import com.example.rickandmortywiki.network.api.Api
import com.example.rickandmortywiki.utils.mapNetworkCharacterToDataCharacterEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharactersListViewModel.CharactersListViewModelFactory::class)
class CharactersListViewModel @AssistedInject constructor(
    private val apiService: Api,
    private val db: AppDatabase,
    @Assisted episodeId: Int
) : ViewModel() {

    @SuppressLint("LogNotTimber")
    val exceptionHandler =
        CoroutineExceptionHandler { _, e ->
            Log.e("Error!!", e.toString())
        }

    private val _episodeWithCharacters = MutableStateFlow<EpisodeWithCharacters?>(null)
    val episodeWithCharacters: StateFlow<EpisodeWithCharacters?> = _episodeWithCharacters
    val activeItem: MutableStateFlow<CharacterEntity?> = MutableStateFlow(null)

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            db.episodeWithCharacterDao().observerCharactersFromEpisode(episodeId = episodeId)
                .collect {
                    _episodeWithCharacters.value = it
                }
        }

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val charactersIds = db.episodeDao().getEpisodeCharactersIds(episodeId)
            val listOfNetworkCharacters =
                apiService.getMultipleCharacters(charactersIds.joinToString(","))
            listOfNetworkCharacters.forEach {
                val character = mapNetworkCharacterToDataCharacterEntity(it)
                if (character != null) {
                    db.episodeWithCharacterDao()
                        .insert(EpisodeCharacterCrossRef(episodeId, character.characterId))
                    character.episodes?.forEach {
                        db.episodeWithCharacterDao()
                            .insert(EpisodeCharacterCrossRef(it.toInt(), character.characterId))
                    }
                    db.characterDao().insertCharacter(character)
                }
            }
        }
    }

    @AssistedFactory
    interface CharactersListViewModelFactory {
        fun build(episodeId: Int): CharactersListViewModel
    }
}
