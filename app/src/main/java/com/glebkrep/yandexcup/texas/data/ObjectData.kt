package com.glebkrep.yandexcup.texas.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "object_data_table")
data class ObjectData(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    @ColumnInfo(name = "location")
    val location:LatLon,
    val objects:List<Object>,
    @ColumnInfo(name = "is_sent")
    var isSent:Boolean = false
)

data class Object(
    val type:ItemType,
    val count:Int
)