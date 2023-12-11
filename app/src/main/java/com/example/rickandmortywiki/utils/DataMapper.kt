package com.example.rickandmortywiki.utils

import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.network.models.Character
import com.example.rickandmortywiki.network.models.Episode

fun mapNetworkEpisodeToDataEpisodeEntity(episode: Episode): EpisodeEntity? {
    return EpisodeEntity(
        episodeId = episode.id ?: return null,
        airDate = episode.airDate,
        name = episode.name,
        url = episode.url,
        episode = episode.episode,
        characters = convertCharactersUrlToIds(episode.characters),
        lastUpdate = System.currentTimeMillis()
    )
}

fun convertCharactersUrlToIds(charactersList: List<String>?):List<String>? {
    return charactersList?.map { it.replace(
        "https://rickandmortyapi.com/api/character/", "")
    }
}

fun mapNetworkCharacterToDataCharacterEntity(character: Character): CharacterEntity? {
    return CharacterEntity(
        characterId = character.id ?: return null,
        name = character.name,
        status = character.status,
        species = character.species,
        gender = character.gender,
        imageUrl = character.image,
        episodes = convertEpisodeUrlToIds(character.episodeUrl)
    )
}

fun convertEpisodeUrlToIds(charactersList: List<String>?):List<String>? {
    return charactersList?.map { it.replace(
        "https://rickandmortyapi.com/api/episode/", "")
    }
}