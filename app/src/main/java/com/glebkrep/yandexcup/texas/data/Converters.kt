package com.glebkrep.yandexcup.texas.data

import androidx.room.TypeConverter
import com.glebkrep.yandexcup.texas.utils.Debug

class Converters {
    @TypeConverter
    fun toListOfObject(value: String): Object {
        val lines = value.split(":").filter { it!="" }
        if (lines.size != 2) {
            throw Exception("Can't convert")
        }
        return com.glebkrep.yandexcup.texas.data.Object(
            type = enumValueOf<ItemType>(lines.first()),
            count = lines[1].toInt()
        )
    }

    @TypeConverter
    fun fromListOfObject(value: Object): String {
        return "${value.type.name}:${value.count}"
    }

    @TypeConverter
    fun fromLatLon(value: LatLon): String {
        return "${value.lat}:${value.lon}"
    }

    @TypeConverter
    fun fromString(value: String): LatLon {
        val data = value.split(":")
        return LatLon(lat = data.first().toFloat(), lon = data[1].toFloat())
    }
}