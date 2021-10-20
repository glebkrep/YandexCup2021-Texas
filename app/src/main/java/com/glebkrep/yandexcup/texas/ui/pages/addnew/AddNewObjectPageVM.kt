package com.glebkrep.yandexcup.texas.ui.pages.addnew

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glebkrep.yandexcup.texas.data.InternetStatus
import com.glebkrep.yandexcup.texas.data.LatLon
import com.glebkrep.yandexcup.texas.data.ObjectData
import com.glebkrep.yandexcup.texas.utils.Debug
import com.glebkrep.yandexcup.texas.utils.SharePreferences
import com.glebkrep.yandexcup.texas.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.InetAddress

class AddNewObjectPageVM(application: Application) : AndroidViewModel(application) {
    private val _location: MutableLiveData<LatLon> = MutableLiveData()
    val location: LiveData<LatLon> = _location

    private val _internetStatus: MutableLiveData<InternetStatus> = MutableLiveData()
    val internetStatus: LiveData<InternetStatus> = _internetStatus

    init {
        checkInternet()
    }

    private fun checkInternet() {
        viewModelScope.launch(Dispatchers.IO) {
            val isInternetOk = try {
                val ipAddr: InetAddress = InetAddress.getByName("google.com")
                !ipAddr.equals("")
            } catch (e: Exception) {
                Debug.log("isInternetAvailable() Exception:${e.message} ${e}")
                false
            }
            if (isInternetOk) {
                _internetStatus.postValue(InternetStatus.OK)
            } else {
                _internetStatus.postValue(InternetStatus.NO_INTERNET)
            }
        }
    }

    @SuppressLint("VisibleForTests", "MissingPermission")
    fun getGeo(activity: Activity, failedToGetGeo: (String) -> (Unit)) {
        val fusedLocationClient = FusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            val latLon = LatLon(lon = location.longitude, lat = location.latitude)
            _location.postValue(latLon)
        }.addOnFailureListener {
            Debug.log("Failed to get geo")
            failedToGetGeo.invoke("Не получилось получить геолокацию...")
        }
    }

    fun addObject(mObject: ObjectData, internetStatus: InternetStatus, activity: Activity) {
        when (internetStatus) {
            is InternetStatus.OK -> {
                saveAndSend(mObject, activity, SharePreferences.getEmail())
            }
            else -> {
                saveLocally(mObject)
            }
        }
    }

    private fun saveLocally(mObject: ObjectData) {
        mObject.isSent = false
        //todo save to db
    }

    private fun saveAndSend(mObject: ObjectData, activity: Activity, email: String) {
        mObject.isSent = true
        //todo save to db
        Utils.sendEmailWithData(email, mObject, activity)
    }

}
