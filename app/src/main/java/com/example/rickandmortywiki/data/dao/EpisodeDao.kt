package com.example.rickandmortywiki.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortywiki.data.entities.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episodes")
    fun getAll(): List<EpisodeEntity>

    @Query("SELECT * FROM episodes WHERE episodeId IN (:episodeIds)")
    fun loadAllByIds(episodeIds: IntArray): List<EpisodeEntity>

    @Query("SELECT * FROM episodes WHERE episodeId IN (:episodeId) LIMIT 1")
    fun loadEpisodeById(episodeId: Int): EpisodeEntity

    @Query("SELECT * FROM episodes WHERE episode LIKE :episodeMark LIMIT 1")
    fun findEpisodeByMark(episodeMark: String): EpisodeEntity

    @Query("SELECT characters FROM episodes WHERE episodeId IN (:episodeId) LIMIT 1")
    fun getEpisodeCharactersIds(episodeId: Int): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodeEntities: List<EpisodeEntity?>)

    @Delete
    fun delete(episodeEntity: EpisodeEntity)

    @Query("SELECT * FROM episodes LIMIT (:limit)")
    fun observeEpisodes(limit: Int):Flow<List<EpisodeEntity>>
}