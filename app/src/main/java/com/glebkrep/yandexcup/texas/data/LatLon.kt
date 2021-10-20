package com.glebkrep.yandexcup.texas.data

data class LatLon(
    val lat: Double = 0.0,
    val lon: Double = 0.0
)

val LatLon.readableString:String
    get() {
        return "${lat};${lon}"
    }