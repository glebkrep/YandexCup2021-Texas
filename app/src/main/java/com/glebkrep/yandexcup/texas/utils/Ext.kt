package com.glebkrep.yandexcup.texas.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Patterns
import com.glebkrep.yandexcup.texas.data.ObjectData

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun ObjectData.toJsonString():String{
    //todo to json string
    return ""
}