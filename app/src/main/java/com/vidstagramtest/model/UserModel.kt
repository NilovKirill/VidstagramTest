package com.vidstagramtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val id: Long?,
    var name: String? = null
) : Parcelable {
    constructor(
        name: String
    ) : this(
        null,
        name
    )
}