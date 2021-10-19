package com.glebkrep.yandexcup.texas.ui

sealed class Screen(val route: String) {
    object Home:Screen("Home")
    object PreviousObjects : Screen("PreviousObjects")
    object AddNewObject: Screen("NewObject")
}