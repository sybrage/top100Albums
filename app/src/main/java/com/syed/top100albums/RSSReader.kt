package com.syed.top100albums

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.Gson
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import java.net.URL

fun rssData(context: Context): List<ResultsItem> {
    val sharedPrefs = context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE)
    val realmDB = Realm.open(RealmConfiguration.create(schema = setOf(AlbumData::class)))
    val albumsRssResults: List<ResultsItem>
    val albumsRssFeed = if (isOnline(context)) tryRSS() else ""
    if (albumsRssFeed != "") {
        val albumsRssData = Gson().fromJson(albumsRssFeed, AlbumsDataModel::class.java)
        albumsRssResults = extractAlbumResults(albumsRssData)
        updateDB(realmDB, albumsRssResults)
        with (sharedPrefs.edit()) {
            putString(context.getString(R.string.copyright), albumsRssData.feed?.copyright)
            apply()
        }
    } else {
        albumsRssResults = extractAlbumResults(readDB(realmDB))
    }
    realmDB.close()
    return albumsRssResults
}

fun tryRSS(): String {
    return try {
        URL("https://rss.applemarketingtools.com/api/v2/us/music/most-played/100/albums.json").readText()
    } catch (ex: Exception) {
        ""
    }
}

fun readDB(realmDB: Realm): RealmResults<AlbumData> {
    return realmDB.query(AlbumData::class).find()
}

fun updateDB(realmDB: Realm, albumRssResults: List<ResultsItem>) {
    val albumsRealmData: MutableList<RealmObject> = mutableListOf()
    albumRssResults.forEach() {
        var genreString = ""
        it.genres?.forEach {
            genreString += it?.name + ","
        }
        val albumRealmData = AlbumData().apply {
            artworkUrl100 = it.artworkUrl100 ?: ""
            releaseDate = it.releaseDate ?: ""
            kind = it.kind ?: ""
            artistUrl = it.artistUrl ?: ""
            name = it.name ?: ""
            artistName = it.artistName ?: ""
            artistId = it.artistId ?: ""
            id = it.id ?: ""
            genres = genreString.dropLast(1)
            contentAdvisoryRating = it.contentAdvisoryRating ?: ""
        }
        albumsRealmData += albumRealmData
    }
    if (albumsRealmData.size == 100) {
        realmDB.writeBlocking {
            deleteAll()
            albumsRealmData.forEach {
                copyToRealm(it)
            }
        }
    }
}

fun extractAlbumResults(albumsRssData: AlbumsDataModel): List<ResultsItem> {
    val albumData: MutableList<ResultsItem> = mutableListOf()
    albumsRssData.feed?.results?.forEach() {
        val tempAlbum = ResultsItem()
        tempAlbum.artworkUrl100 = it?.artworkUrl100?.replace("100x100", "600x600")
        tempAlbum.releaseDate = it?.releaseDate
        tempAlbum.kind = it?.kind
        tempAlbum.artistUrl = it?.artistUrl
        tempAlbum.name = it?.name
        tempAlbum.artistName = it?.artistName
        tempAlbum.artistId = it?.artistId
        tempAlbum.id = it?.id
        tempAlbum.url = it?.url
        tempAlbum.contentAdvisoryRating = it?.contentAdvisoryRating
        tempAlbum.genres = it?.genres
        albumData += tempAlbum
    }
    return albumData
}

fun extractAlbumResults(realmResults: RealmResults<AlbumData>): List<ResultsItem> {
    val albumData: MutableList<ResultsItem> = mutableListOf()
    realmResults.forEach() {
        val tempAlbum = ResultsItem()
        tempAlbum.artworkUrl100 = it.artworkUrl100
        tempAlbum.releaseDate = it.releaseDate
        tempAlbum.kind = it.kind
        tempAlbum.artistUrl = it.artistUrl
        tempAlbum.name = it.name
        tempAlbum.artistName = it.artistName
        tempAlbum.artistId = it.artistId
        tempAlbum.id = it.id
        tempAlbum.contentAdvisoryRating = it.contentAdvisoryRating
        val genres: List<String> = it.genres.split(",")
        val genreList: MutableList<GenresItem> = mutableListOf()
        genres.forEach() {
            val tempGenre = GenresItem()
            tempGenre.name = it
            genreList += tempGenre
        }
        tempAlbum.genres = genreList
        albumData += tempAlbum
    }
    return albumData
}

fun isOnline(context: Context): Boolean {
    var online = false
    val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeConn = connManager.activeNetwork
    if (activeConn != null) {
        val availableConn = connManager.getNetworkCapabilities(activeConn)
        if (availableConn != null)
            online = availableConn.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    availableConn.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
    return online
}