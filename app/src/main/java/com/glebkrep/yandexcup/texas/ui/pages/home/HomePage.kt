package com.glebkrep.yandexcup.texas.ui.pages.home

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.glebkrep.yandexcup.texas.utils.SharePreferences
import com.glebkrep.yandexcup.texas.utils.isValidEmail
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomePage(toScannedObjects: () -> (Unit), toAddNewObjects: () -> (Unit)) {
    val locationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    var email by remember { mutableStateOf(SharePreferences.getEmail()) }
    var emailError by remember { mutableStateOf("") }

    val withPadding = Modifier.padding(16.dp)
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Спасаем Техас", withPadding)

        PermissionRequired(
            permissionState = locationPermissionState,
            permissionNotGrantedContent = {
                Text(
                    "Для работы приложения нужно разрешение на доступ к геолокации",
                    withPadding,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { locationPermissionState.launchPermissionRequest() },
                    withPadding
                ) {
                    Text("Предоставить разрешение!")
                }
            },
            permissionNotAvailableContent = {
                Text(
                    "Разрешение не было предоставлено, приложение не сможет работать...",
                    withPadding
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        ) {
            TextField(value = email, onValueChange = {
                email = it
            }, label = {
                Text(text = "Почта для отчетов")
            }, modifier = withPadding)
            if (emailError!=""){
                Text(emailError,withPadding,color=Color.Red)
            }
            Button(onClick = {
                if (!email.isValidEmail()) {
                    emailError = "Почта не валидна"
                    return@Button
                }
                SharePreferences.setEmail(email)
                toScannedObjects.invoke()
            }, withPadding) {
                Text(text = "Отсканированные объекты")
            }
            Button(onClick = {
                if (!email.isValidEmail()) {
                    emailError = "Почта не валидна"
                    return@Button
                }
                SharePreferences.setEmail(email)
                toAddNewObjects.invoke()
            }, withPadding) {
                Text(text = "Добавить новый объект")
            }
        }


    }
}