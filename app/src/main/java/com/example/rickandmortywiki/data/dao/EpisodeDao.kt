package com.example.rickandmortywiki.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import com.example.rickandmortywiki.data.entities.EpisodeWithCharacters
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episodes")
    fun getAll(): List<EpisodeEntity>

    @Query("SELECT * FROM episodes WHERE episodeId IN (:episodeIds)")
    fun loadAllByIds(episodeIds: IntArray): List<EpisodeEntity>

    @Query("SELECT * FROM episodes WHERE episode LIKE :episodeMark LIMIT 1")
    fun findEpisodeByMark(episodeMark: String): EpisodeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodeEntities: List<EpisodeEntity>)

    @Delete
    fun delete(episodeEntity: EpisodeEntity)

    @Query("SELECT * FROM episodes")
    fun observeEpisodes():Flow<List<EpisodeEntity>>

    @Transaction
    @Query("SELECT * FROM episodes")
    fun getEpisodesWithCharacters(): List<EpisodeWithCharacters?>
}