package com.example.rickandmortywiki.utils

import androidx.test.platform.app.InstrumentationRegistry

class Preconditions {
    fun getNthEpisodeTitle(episodeId: Int) {
        InstrumentationRegistry.getInstrumentation().targetContext.cacheDir
    }
}

fun preconditions(operator: Preconditions.() -> Unit): Preconditions {
    return Preconditions().also(operator)
}