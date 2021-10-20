package com.glebkrep.yandexcup.texas.data

data class ObjectData(
    var id:Int = 0,
    val location:LatLon,
    val objects:List<Object>,
    var isSent:Boolean = false
)

data class Object(
    val type:ItemType,
    val count:Int
)