package com.example.rickandmortywiki.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.rickandmortywiki.data.entities.CharacterWithEpisodes
import com.example.rickandmortywiki.data.entities.EpisodeCharacterCrossRef
import com.example.rickandmortywiki.data.entities.EpisodeWithCharacters
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeWithCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(join: EpisodeCharacterCrossRef)

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId")
    fun getEpisodesWithCharacter(): List<CharacterWithEpisodes>

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
