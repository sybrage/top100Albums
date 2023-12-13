package com.syed.top100albums

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class AlbumData(): RealmObject {
    @PrimaryKey
    var artworkUrl100: String = ""
    var releaseDate: String = ""
    var kind: String = ""
    var artistUrl: String = ""
    var name: String = ""
    var artistName: String = ""
    var artistId: String = ""
    var id: String = ""
    var url: String = ""
    var genres: String = ""
    var contentAdvisoryRating: String = ""
}