package com.glebkrep.yandexcup.texas.data

import kotlinx.serialization.Serializable

@Serializable
data class ObjectDataForSending(
    val coordinates:LatLon,
    val objects:ObjectForSending
)
@Serializable
data class ObjectForSending(
    val `object`:TypeCountForSending
)
@Serializable
data class TypeCountForSending(
    val type: String,
    val count:Int
)