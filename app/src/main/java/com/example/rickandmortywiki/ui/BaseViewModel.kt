package com.example.rickandmortywiki.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler


//@HiltViewModel
//open class BaseViewModel : AndroidViewModel(application = Application()) {
//    @SuppressLint("LogNotTimber")
//    protected val exceptionHandler =
//        CoroutineExceptionHandler { _, e ->
//            Log.e("Error!!", e.toString())
//        }
//}
//
//class RickAndMortyViewModelFactory @Inject constructor(
//    private val apiService: Api,
//    private val db: DatabaseApi
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(EpisodesViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return EpisodesViewModel(apiService, db) as T
//        }
//        if (modelClass.isAssignableFrom(CharactersListViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return CharactersListViewModel(apiService, db) as T
//        }
//        if (modelClass.isAssignableFrom(CharacterInfoViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return CharacterInfoViewModel(db) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}