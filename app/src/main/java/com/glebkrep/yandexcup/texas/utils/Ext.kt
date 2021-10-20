package com.glebkrep.yandexcup.texas.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Patterns
import com.glebkrep.yandexcup.texas.data.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun ObjectData.toJsonString(): String {
    val objectForSending = ObjectForSending(
        `object` = TypeCountForSending(
            type = this.`object`.type.name,
            count = this.`object`.count
        )
    )
    val objectDataForSending = ObjectDataForSending(
        coordinates = this.location,
        objects = objectForSending
    )
    return Json.encodeToString(objectDataForSending)
}