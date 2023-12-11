package com.example.rickandmortywiki.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel: AndroidViewModel(application = Application()) {
    @SuppressLint("LogNotTimber")
    protected val exceptionHandler =
        CoroutineExceptionHandler { _, e ->
            Log.e("Error!!", e.toString())
        }
}
