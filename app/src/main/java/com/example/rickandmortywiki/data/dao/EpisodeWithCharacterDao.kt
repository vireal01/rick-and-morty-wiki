package com.example.rickandmortywiki.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.data.entities.CharacterWithEpisodes
import com.example.rickandmortywiki.data.entities.EpisodeCharacterCrossRef
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.data.entities.EpisodeWithCharacters
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeWithCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(join: EpisodeCharacterCrossRef)

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId")
    fun getEpisodesWithCharacter(): List<CharacterWithEpisodes>

//    @Query("SELECT * FROM characters INNER JOIN episodecharactercrossref ON characters.characterId = episodecharactercrossref.characterId WHERE episodecharactercrossref.episodeId = :episodeId")
//    fun getCharactersForEpisode(episodeId: Int): List<CharacterEntity>
//
//    @Query("SELECT * FROM episodes INNER JOIN episodecharactercrossref ON episodes.episodeId = episodecharactercrossref.episodeId WHERE episodecharactercrossref.characterId = :characterId")
//    fun getEpisodesWithCharacter(characterId: Int): List<EpisodeEntity>

    @Transaction
    @Query("SELECT * FROM episodes")
    fun getCharactersFromEpisode(): List<EpisodeWithCharacters>

    @Transaction
    @Query("SELECT * FROM episodes WHERE episodeId = :episodeId")
    fun observerCharactersFromEpisode(episodeId: Int): Flow<EpisodeWithCharacters>

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    fun observerEpisodesOfCharacter(characterId: Int): Flow<CharacterWithEpisodes>
}