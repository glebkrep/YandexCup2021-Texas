package com.glebkrep.yandexcup.texas.ui.pages.addnew

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
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
fun AddNewObjectPage(addNewObjectVM: AddNewObjectPageVM = viewModel(), onSent: () -> (Unit)) {
    var count by remember { mutableStateOf(0) }
    var itemType by remember {
        mutableStateOf(ItemType.tree)
    }

    val geoPoint by addNewObjectVM.location.observeAsState(LatLon())
    var geoError by remember {
        mutableStateOf("")
    }

    val internetStatus by addNewObjectVM.internetStatus.observeAsState(InternetStatus.NO_INTERNET)
    var expanded by remember { mutableStateOf(false) }
    var submitError by remember {
        mutableStateOf("")
    }
    val allTypes by remember {
        mutableStateOf(
            listOf(
                ItemType.tree,
                ItemType.streetlight,
                ItemType.mailbox,
                ItemType.mailbox,
                ItemType.power_pylon
            )
        )
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
        Text(text = "Какой объект вы хотите отметить?", withPadding)
        Button(
            onClick = { expanded = !expanded },
            withPadding
        ) {
            Text(text = "Выбрать объект")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }, content = {
                allTypes.forEach { type ->
                    DropdownMenuItem(onClick = {
                        itemType = type
                        expanded = !expanded
                    }) {
                        Text(text = type.label)
                    }
                }
            }, modifier = withPadding.align(Alignment.CenterHorizontally)
        )


        ItemWithCounter(
            itemName = itemType.label,
            itemCount = count,
            changingQuantity = {
                submitError = ""
                count += it
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
                count = count,
                itemType = itemType,
                geo = geoPoint
            ) {
                submitError = it
            } ?: return@Button
            addNewObjectVM.addObject(
                mObject,
                internetStatus,
                context.getActivity() ?: return@Button
            )
            onSent.invoke()
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
    count: Int,
    itemType: ItemType,
    geo: LatLon,
    onError: (String) -> (Unit)
): ObjectData? {
    if (geo.lat == 0.0f && geo.lon == 0.0f) {
        onError.invoke("Сначала обнои геолокацию")
        return null
    }
    if (count == 0) {
        onError.invoke("Выбери хотя бы один объект")
        return null
    }
    return ObjectData(
        location = geo,
        `object` = com.glebkrep.yandexcup.texas.data.Object(itemType, count)
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