package com.example.rickandmortywiki.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class EpisodeWithCharacters(
    @Embedded val episode: EpisodeEntity,
    @Relation(
        parentColumn = "episodeId",
        entityColumn = "characterId",
        associateBy = Junction(EpisodeCharacterCrossRef::class)
    )
    val characters: List<CharacterEntity>
)

data class CharacterWithEpisodes(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "episodeId",
        associateBy = Junction(EpisodeCharacterCrossRef::class)
    )
    val episodes: List<EpisodeEntity>
)

@Entity(primaryKeys = ["episodeId", "characterId"])
data class EpisodeCharacterCrossRef(
    val episodeId: Int,
    val characterId: Int
)
