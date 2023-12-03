package com.syed.top100albums

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AlbumsDataModel(

	@field:SerializedName("feed")
	val feed: Feed? = null
)

@Parcelize
data class GenresItem(

	@field:SerializedName("genreId")
	val genreId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
): Parcelable

data class Feed(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("copyright")
	val copyright: String? = null,

	@field:SerializedName("author")
	val author: Author? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("links")
	val links: List<LinksItem?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("updated")
	val updated: String? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class LinksItem(

	@field:SerializedName("self")
	val self: String? = null
)

@Parcelize
data class ResultsItem(

	@field:SerializedName("artworkUrl100")
	var artworkUrl100: String? = null,

	@field:SerializedName("releaseDate")
	var releaseDate: String? = null,

	@field:SerializedName("kind")
	var kind: String? = null,

	@field:SerializedName("artistUrl")
	var artistUrl: String? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("artistName")
	var artistName: String? = null,

	@field:SerializedName("artistId")
	var artistId: String? = null,

	@field:SerializedName("id")
	var id: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("contentAdvisoryRating")
	var contentAdvisoryRating: String? = null
): Parcelable

data class Author(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
