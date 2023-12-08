package com.example.rickandmortywiki.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("air_date")
    val airDate: String? = null,

    @SerializedName("episode")
    val episode: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("characters")
    val characters: List<String>? = emptyList(),
) : Parcelable

data class AllEpisodes(
    @SerializedName("info")
    val info: EpisodesInfo? = null,

    @SerializedName("results")
    val results: List<Episode>? = null,

    @SerializedName("error")
    val error: String? = null,
)

data class EpisodesInfo(
    @SerializedName("count")
    val count: Int? = null,

    @SerializedName("pages")
    val pages: Int? = null,

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("prev")
    val prev: String? = null,
)