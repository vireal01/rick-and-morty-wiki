package com.example.rickandmortywiki.network.models

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("species")
    val species: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("location")
    val location: Location? = null,

    @SerializedName("episode")
    val episodeUrl: List<String>? = null,

    var appearsInEpisodes: MutableList<Episode>? = null,

    var characterImageBitmap: Bitmap? = null,
) : Parcelable

@Parcelize
data class Location(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("url")
    var url: String? = null,
) : Parcelable