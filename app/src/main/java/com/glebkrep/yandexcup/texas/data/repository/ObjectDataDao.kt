package com.glebkrep.yandexcup.texas.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glebkrep.yandexcup.texas.data.ObjectData

@Dao
interface ObjectDataDao {
    @Query("select * from object_data_table order by is_sent asc")
    fun getAllItems(): LiveData<List<ObjectData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(objectData: ObjectData): Long

    @Query("update object_data_table set is_sent=:mTrue where id=:id")
    suspend fun setSentById(id: Int,mTrue:Boolean=true)
}
