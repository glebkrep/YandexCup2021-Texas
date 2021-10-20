package com.glebkrep.yandexcup.texas.utils

import android.util.Log
import com.glebkrep.yandexcup.texas.BuildConfig

object Debug {
    fun log(any:Any?){
        if (BuildConfig.DEBUG){
            Log.e("Texas.Debug:::",any.toString())
        }
    }
}