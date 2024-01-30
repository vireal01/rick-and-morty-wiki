package com.example.rickandmortywiki.ui.episodes

import com.example.rickandmortywiki.data.entities.EpisodeEntity

sealed class RecyclerViewItemDataModel {
    data class Item(
        val episode: EpisodeEntity
    ) : RecyclerViewItemDataModel()

    data class Button(
        val buttonTitle: String
    ) : RecyclerViewItemDataModel()
}
