package com.example.rickandmortywiki.utils

import android.util.Log
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
        characters = convertCharactersUrlToIds(episode.characters)
    )
}

//fun mapDataEpisodeEntityToNetworkEpisode(episode: EpisodeEntity): Episode? {
//    return Episode(
//        id = episode.episodeId,
//        airDate = episode.airDate,
//        name = episode.name,
//        url = episode.url,
//        episode = episode.episode,
//        characters = convertCharactersUrlToIds(episode.characters)
//    )
//}

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
//        location = character.location,
        episodes = convertEpisodeUrlToIds(character.episodeUrl)
    )
}

fun convertEpisodeUrlToIds(charactersList: List<String>?):List<String>? {
    return charactersList?.map { it.replace(
        "https://rickandmortyapi.com/api/episode/", "")
    }
}