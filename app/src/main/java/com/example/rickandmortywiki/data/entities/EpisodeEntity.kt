package com.example.rickandmortywiki.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey val episodeId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "air_date") val airDate: String?,
    @ColumnInfo(name = "episode") val episode: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "characters") val characters: List<String>?,
) : Parcelable
