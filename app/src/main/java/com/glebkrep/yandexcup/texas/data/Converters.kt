package com.glebkrep.yandexcup.texas.data

import androidx.room.TypeConverter
import com.glebkrep.yandexcup.texas.utils.Debug

class Converters {
    @TypeConverter
    fun toListOfObject(value: String):List<Object>{
        val lines = value.split(";")
        val mutableListOfObject = mutableListOf<Object>()
        for (line in lines){
            val objectCountPair = line.split(":").filter { it !="" }
            if (objectCountPair.size!=2){
                continue
            }
            Debug.log("objectCountPair: ${objectCountPair.toString()}")
            mutableListOfObject.add(com.glebkrep.yandexcup.texas.data.Object(
                type = enumValueOf<ItemType>(objectCountPair.first()),
                count = objectCountPair[1].toInt()
            ))
        }
        return mutableListOfObject
    }

    @TypeConverter
    fun fromListOfObject(value: List<Object>):String {
        var finalStr = ""
        for (mObject in value){
            finalStr+="${mObject.type.name}:${mObject.count};"
        }
        return finalStr
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