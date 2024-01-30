package com.example.rickandmortywiki.data.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmortywiki.data.dao.CharacterDao
import com.example.rickandmortywiki.data.dao.EpisodeDao
import com.example.rickandmortywiki.data.dao.EpisodeWithCharacterDao
import com.example.rickandmortywiki.data.entities.CharacterEntity
import com.example.rickandmortywiki.data.entities.EpisodeCharacterCrossRef
import com.example.rickandmortywiki.data.entities.EpisodeEntity

@Database(entities = [CharacterEntity::class, EpisodeEntity::class, EpisodeCharacterCrossRef::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun episodeWithCharacterDao(): EpisodeWithCharacterDao
}
