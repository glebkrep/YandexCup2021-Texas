package com.glebkrep.yandexcup.texas

import android.app.Application
import com.glebkrep.yandexcup.texas.utils.SharePreferences

class MainApp : Application(){
    override fun onCreate() {
        super.onCreate()
        SharePreferences.init(this)
    }
}