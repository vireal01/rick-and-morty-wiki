package com.example.rickandmortywiki.ui.characterInfo

import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.databse.DatabaseApi
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.data.entities.CharacterWithEpisodes
import com.example.rickandmortywiki.navigation.Router
import com.example.rickandmortywiki.ui.BaseViewModel
import com.example.rickandmortywiki.ui.charactersList.CharactersListScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterInfoViewModel @AssistedInject constructor(
    private val router: Router,
    private val db: DatabaseApi,
    @Assisted characterId: Int
) : BaseViewModel() {

    private val _character = MutableStateFlow<CharacterEntity?>(null)
    val character: StateFlow<CharacterEntity?> = _character

    private val _appearsAtEpisodes = MutableStateFlow<CharacterWithEpisodes?>(null)
    val appearsAtEpisodes: StateFlow<CharacterWithEpisodes?> = _appearsAtEpisodes

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val characterEntity = db.characterDao().getCharacterById(characterId = characterId)
            _character.value = characterEntity

            db.episodeWithCharacterDao().observerEpisodesOfCharacter(characterId)
                .collect {
                    _appearsAtEpisodes.value = it
                }
        }
    }

    fun onBackPressed() {
        router.closeScreen()
    }

    fun onViewEpisodeItemClick(episodeId: Int) {
        router.navigateTo(CharactersListScreen(episodeId))
    }

    @AssistedFactory
    interface Factory {
        fun build(characterId: Int): CharacterInfoViewModel
    }
}