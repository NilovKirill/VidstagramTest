package com.vidstagramtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostModel(
    val userId: String?,
    val userName: String?,
    var title: String?,
    var videoUrl: String?,
    var timestamp: Long?

) : Parcelable {
    constructor() : this(null, null, null, null, null)
}

