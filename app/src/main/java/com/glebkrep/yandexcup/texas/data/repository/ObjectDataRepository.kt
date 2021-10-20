package com.glebkrep.yandexcup.texas.data.repository

import com.glebkrep.yandexcup.texas.data.ObjectData

class ObjectDataRepository(private val objectDataDao: ObjectDataDao) {
    fun getAllBreathingItems() = objectDataDao.getAllItems()

    suspend fun setSentById(id:Int){
        objectDataDao.setSentById(id)
    }

    suspend fun insert(objectData: ObjectData){
        objectDataDao.insert(objectData)
    }

}