package com.glebkrep.yandexcup.texas.data

import kotlinx.serialization.Serializable

@Serializable
data class LatLon(
    val lat: Float = 0.0f,
    val lon: Float = 0.0f
)

val LatLon.readableString:String
    get() {
        return "${lat};${lon}"
    }