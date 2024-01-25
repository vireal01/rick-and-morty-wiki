package com.example.rickandmortywiki.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun prettifyLastUpdateText(timestamp: Long?): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM, HH:mm:ss", Locale.ENGLISH)
    return "upd: " + simpleDateFormat.format(timestamp)
}
