package com.glebkrep.yandexcup.texas.ui.pages.previous

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glebkrep.yandexcup.texas.data.InternetStatus
import com.glebkrep.yandexcup.texas.data.ObjectData
import com.glebkrep.yandexcup.texas.data.repository.ObjectDataRepository
import com.glebkrep.yandexcup.texas.data.repository.ObjectDataRoomDatabase
import com.glebkrep.yandexcup.texas.utils.SharePreferences
import com.glebkrep.yandexcup.texas.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreviousObjectsPageVM(application: Application):AndroidViewModel(application) {
    val objectDataList:LiveData<List<ObjectData>>

    private val _internetStatus: MutableLiveData<InternetStatus> = MutableLiveData()
    val internetStatus: LiveData<InternetStatus> = _internetStatus

    private var objectDataRepository: ObjectDataRepository? = null
    init {
        objectDataRepository = ObjectDataRepository(
            ObjectDataRoomDatabase.getDatabase(getApplication()).objectDataDao()
        )
        objectDataList = objectDataRepository!!.getAllBreathingItems()
        checkInternet()
    }

    private fun checkInternet() {
        viewModelScope.launch(Dispatchers.IO) {
            val isInternetOk = Utils.isInternetOk()
            if (isInternetOk) {
                _internetStatus.postValue(InternetStatus.OK)
            } else {
                _internetStatus.postValue(InternetStatus.NO_INTERNET)
            }
        }
    }

    fun sendData(objectData: ObjectData,activity: Activity){
        Utils.sendEmailWithData(SharePreferences.getEmail(),objectData,activity)
        viewModelScope.launch(Dispatchers.IO) {
            objectDataRepository?.setSentById(objectData.id)
        }
    }

}
