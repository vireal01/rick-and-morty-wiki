package com.example.rickandmortywiki.ui.characterInfo

import androidx.lifecycle.ViewModel
import com.example.rickandmortywiki.data.databse.DatabaseApi
import com.example.rickandmortywiki.navigation.Router
import com.example.rickandmortywiki.network.api.Api
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CharacterInfoViewModel @AssistedInject constructor(
    private val router: Router,
    private val db: DatabaseApi,
    private val apiService: Api
) : ViewModel() {

    fun onBackPressed() {
        router.closeScreen()
    }

    @AssistedFactory
    interface Factory {
        fun build(): CharacterInfoViewModel
    }
}