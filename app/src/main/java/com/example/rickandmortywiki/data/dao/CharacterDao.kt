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

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE characterId IN (:characterIds)")
    fun loadAllByIds(characterIds: IntArray): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE characterId IN (:characterId) LIMIT 1")
    fun getCharacterById(characterId: Int): CharacterEntity?

    @Query("SELECT * FROM characters WHERE name LIKE :first LIMIT 1")
    fun findByName(first: String): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characterEntities: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characterEntity: CharacterEntity)

    @Delete
    fun delete(characterEntity: CharacterEntity)

    @Update
    fun updateUsers(vararg characterEntity: CharacterEntity)

    @Transaction
    @Query("SELECT * FROM characters")
    fun getEpisodesWithCharacter(): List<CharacterWithEpisodes?>
}