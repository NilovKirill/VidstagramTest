package com.vidstagramtest.model.network

data class PostBodyRequest(
    var userId: String = "",
    var userName: String = "",
    var title: String = "",
    var videoUrl: String = "",
    var timestamp: Long = -1

) {
    constructor() : this("", "", "", "", -1)
}