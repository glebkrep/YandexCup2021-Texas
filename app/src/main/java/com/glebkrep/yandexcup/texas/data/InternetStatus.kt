package com.glebkrep.yandexcup.texas.data

abstract class InternetStatus(val msg: String) {
    object OK:InternetStatus("Интернет соединение присутствует")
    object NO_INTERNET:InternetStatus("Интеренет соединение отсутствует")

}