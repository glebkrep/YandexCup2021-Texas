package com.glebkrep.yandexcup.texas.ui.pages.addnew

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.glebkrep.yandexcup.texas.data.*
import com.glebkrep.yandexcup.texas.utils.getActivity

@Composable
fun AddNewObjectPage(addNewObjectVM: AddNewObjectPageVM = viewModel()) {
    var powerPylonCount by remember { mutableStateOf(0) }
    var streetLightCount by remember { mutableStateOf(0) }
    var treeCount by remember { mutableStateOf(0) }
    var mailBoxCount by remember { mutableStateOf(0) }
    var hydrantsCount by remember { mutableStateOf(0) }

    val geoPoint by addNewObjectVM.location.observeAsState(LatLon())
    var geoError by remember {
        mutableStateOf("")
    }

    val internetStatus by addNewObjectVM.internetStatus.observeAsState(InternetStatus.NO_INTERNET)

    var submitError by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val withPadding = Modifier.padding(8.dp)
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .horizontalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Какие объекты вы хотите отметить?", withPadding)
        ItemWithCounter(
            itemName = ItemType.power_pylon.label,
            itemCount = powerPylonCount,
            changingQuantity = {
                submitError = ""
                powerPylonCount += it
            }, withPadding
        )
        ItemWithCounter(
            itemName = ItemType.streetlight.label,
            itemCount = streetLightCount,
            changingQuantity = {
                submitError = ""
                streetLightCount += it
            }, withPadding
        )
        ItemWithCounter(
            itemName = ItemType.tree.label,
            itemCount = treeCount,
            changingQuantity = {
                submitError = ""
                treeCount += it
            }, withPadding
        )
        ItemWithCounter(
            itemName = ItemType.mailbox.label,
            itemCount = mailBoxCount,
            changingQuantity = {
                submitError = ""
                mailBoxCount += it
            }, withPadding
        )
        ItemWithCounter(
            itemName = ItemType.hydrant.label,
            itemCount = hydrantsCount,
            changingQuantity = {
                submitError = ""
                hydrantsCount += it
            }, withPadding
        )
        Divider(withPadding, color = Color.Black, thickness = 2.dp)
        Text("Гео-позиция: ${geoPoint.readableString}", withPadding)
        if (geoError != "") {
            Text(geoError, withPadding, color = Color.Red)
        }
        Button(onClick = {
            geoError = ""
            submitError = ""
            addNewObjectVM.getGeo(context.getActivity() ?: return@Button) {
                geoError = it
            }
        }, withPadding) {
            Text(text = "Получить")
        }
        Divider(withPadding, color = Color.Black, thickness = 2.dp)
        Text(
            text = internetStatus.msg, color = when (internetStatus) {
                is InternetStatus.OK -> Color.Green
                else -> Color.Red
            }, modifier = withPadding
        )
        Button(onClick = {
            submitError = ""
            val mObject = getObject(
                powerPylonCnt = powerPylonCount,
                streetLightCnt = streetLightCount,
                treeCnt = treeCount,
                mailBoxCnt = mailBoxCount,
                hydrantCnt = hydrantsCount,
                geo = geoPoint
            ) {
                submitError = it
            } ?: return@Button
            addNewObjectVM.addObject(mObject, internetStatus,context.getActivity()?:return@Button)
        }) {
            Text(
                text = if (internetStatus is InternetStatus.OK) "Отправить и сохранить" else "Сохранить",
                withPadding
            )
        }
        if (submitError != "") {
            Text(text = submitError, color = Color.Red, modifier = withPadding)
        }
    }
}

private fun getObject(
    powerPylonCnt: Int,
    streetLightCnt: Int,
    treeCnt: Int,
    mailBoxCnt: Int,
    hydrantCnt: Int,
    geo: LatLon,
    onError: (String) -> (Unit)
): ObjectData? {
    if (geo.lat == 0.0f && geo.lon == 0.0f) {
        onError.invoke("Сначала обнои геолокацию")
        return null
    }
    if ((powerPylonCnt + streetLightCnt + treeCnt + mailBoxCnt + hydrantCnt) == 0) {
        onError.invoke("Выбери хотя бы один объект")
        return null
    }
    val listObjects = listOf(
        com.glebkrep.yandexcup.texas.data.Object(
            ItemType.power_pylon, powerPylonCnt
        ),
        com.glebkrep.yandexcup.texas.data.Object(
            ItemType.hydrant, hydrantCnt
        ),
        com.glebkrep.yandexcup.texas.data.Object(
            ItemType.mailbox, mailBoxCnt
        ),
        com.glebkrep.yandexcup.texas.data.Object(
            ItemType.tree, treeCnt
        ),
        com.glebkrep.yandexcup.texas.data.Object(
            ItemType.streetlight, streetLightCnt
        )
    ).filter { it.count != 0 }
    return ObjectData(
        location = geo,
        objects = listObjects
    )
}

@Composable
fun ItemWithCounter(
    itemName: String,
    itemCount: Int,
    changingQuantity: (Int) -> (Unit),
    modifier: Modifier = Modifier
) {
    Row(modifier, horizontalArrangement = Arrangement.Start) {
        Text(text = itemName, Modifier.padding(16.dp))
        Button(onClick = {
            if (itemCount > 0) {
                changingQuantity.invoke(-1)
            }
        }) {
            Text(text = "-")
        }
        Text(text = itemCount.toString(), Modifier.padding(16.dp))
        Button(onClick = { changingQuantity.invoke(1) }) {
            Text(text = "+")
        }
    }
}