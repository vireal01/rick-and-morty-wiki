package com.example.rickandmortywiki.data.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val characterId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "species") val species: String?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "episode") val episodes: List<String>?
    //    @ColumnInfo(name = "location") val location: String?,
) : Parcelable {
    @IgnoredOnParcel
    @Ignore var characterImageBitmap: Bitmap? = null

    @Ignore var appearsInEpisodes: List<EpisodeEntity>? = null
}
