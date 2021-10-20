package com.glebkrep.yandexcup.texas.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.glebkrep.yandexcup.texas.data.ObjectData
import java.net.InetAddress

object Utils {
    fun sendEmailWithData(
        email: String,
        mObject: ObjectData,
        activity: Activity
    ) {
        if (email==""){
            throw Exception("Email can't be empty")
        }
        val selectorIntent = Intent(Intent.ACTION_SENDTO)
        selectorIntent.data = Uri.parse(
            "mailto:"
        )

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "damage_report")
        emailIntent.putExtra(Intent.EXTRA_TEXT, mObject.toJsonString())
        emailIntent.selector = selectorIntent
        try {
            activity.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        } catch (ex: ActivityNotFoundException) {
        }
    }

    fun isInternetOk():Boolean{
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            !ipAddr.equals("")
        } catch (e: Exception) {
            Debug.log("isInternetAvailable() Exception:${e.message} ${e}")
            false
        }
    }
}