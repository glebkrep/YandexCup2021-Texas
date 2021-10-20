package com.glebkrep.yandexcup.texas.ui.pages.previous

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.glebkrep.yandexcup.texas.data.InternetStatus
import com.glebkrep.yandexcup.texas.data.ObjectData
import com.glebkrep.yandexcup.texas.utils.getActivity

@Composable
fun PreviousObjectsPage(previousObjectsVM: PreviousObjectsPageVM = viewModel()) {

    val objectDataList by previousObjectsVM.objectDataList.observeAsState(listOf())
    val internetStatus by previousObjectsVM.internetStatus.observeAsState(InternetStatus.NO_INTERNET)
    val context = LocalContext.current

    val withPadding = Modifier.padding(16.dp)
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = internetStatus.msg,
                color = if (internetStatus is InternetStatus.OK) Color.Green else Color.Red,
                modifier = withPadding
            )
            if (internetStatus !is InternetStatus.OK) {
                Text(text = "Пока нет интернета, отправлять данные нельзя", withPadding)
            }
            Text(text = "Нажми по неотправленным объектам чтобы отправить их", withPadding)
        }
        items(objectDataList) {
            ObjectDataItem(it) {
                if (internetStatus is InternetStatus.OK) {
                    previousObjectsVM.sendData(it, context.getActivity() ?: return@ObjectDataItem)
                }
            }
        }

    }
}

@Composable
fun ObjectDataItem(objectData: ObjectData, onClick: (ObjectData) -> (Unit)) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(2.dp, if (objectData.isSent) Color.Green else Color.Red)
            .clickable {
                if (!objectData.isSent) {
                    onClick.invoke(objectData)
                }
            }
    ) {
        Column {
            Text(text = "Сообщение ${objectData.id}", Modifier.padding(4.dp))
            Text(text = "${objectData.`object`.type.label}:${objectData.`object`.count}", Modifier.padding(4.dp))
        }
        Text(
            text = if (objectData.isSent) "Отправлено!" else "Не отправлено!",
            color = if (objectData.isSent) Color.Green else Color.Red
        )
    }
}