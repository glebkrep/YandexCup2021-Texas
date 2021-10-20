package com.glebkrep.yandexcup.texas.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.glebkrep.yandexcup.texas.data.Converters
import com.glebkrep.yandexcup.texas.data.ObjectData

@TypeConverters(Converters::class)
@Database(entities = [ObjectData::class], version = 1)
abstract class ObjectDataRoomDatabase : RoomDatabase() {

    abstract fun objectDataDao(): ObjectDataDao


    companion object {
        @Volatile
        private var INSTANCE: ObjectDataRoomDatabase? = null

        fun getDatabase(context: Context): ObjectDataRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ObjectDataRoomDatabase::class.java, "local_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
                }

            }
        }
    }
}