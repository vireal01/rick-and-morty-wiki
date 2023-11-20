package com.example.rickandmortywiki.utils

import android.util.Log
import java.lang.Exception

inline fun <R> tryOrLog(block: () -> R?): R? {
    return try {
        block()
    } catch (e: Exception) {
        Log.d("TryOrLog", e.message.toString())
        null
    }
}