package com.example.rickandmortywiki.ui.characterInfo

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.databse.DatabaseApi
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.data.entities.CharacterWithEpisodes
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterInfoViewModel.CharacterInfoViewModelFactory::class)
class CharacterInfoViewModel @AssistedInject constructor(
    private val db: DatabaseApi,
    @Assisted characterId: Int
) : ViewModel() {
    @SuppressLint("LogNotTimber")
    protected val exceptionHandler =
        CoroutineExceptionHandler { _, e ->
            Log.e("Error!!", e.toString())
        }

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

    @AssistedFactory
    interface CharacterInfoViewModelFactory {
        fun build(characterId: Int): CharacterInfoViewModel
    }
}
