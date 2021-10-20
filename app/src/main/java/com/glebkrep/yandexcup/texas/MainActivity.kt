package com.glebkrep.yandexcup.texas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.glebkrep.yandexcup.texas.ui.Screen
import com.glebkrep.yandexcup.texas.ui.pages.addnew.AddNewObjectPage
import com.glebkrep.yandexcup.texas.ui.pages.home.HomePage
import com.glebkrep.yandexcup.texas.ui.pages.previous.PreviousObjectsPage
import com.glebkrep.yandexcup.texas.ui.theme.TexasTheme
//Вы оказались в Техасе после очередного сильного урагана.
//Администрация штата поручила вам разработать приложение для
//инвентаризации инфраструктуры. Напишите приложение для коммунальщиков,
//которые на местности смогут отмечать следующие уничтоженные объекты:
//столбы ЛЭП;
//уличные фонари;
//деревья;
//почтовые ящики;
//пожарные гидранты.
//Поскольку сотовая связь порядком пострадала,
//нужно хранить данные офлайн и отправлять их по электронной почте,
// когда появляется интернет.
//
// Север готов принимать запросы в формате:
//  {
//    "coordinates": {
//      "lat": float,
//      "lon": float
//     },
//    "objects": {
//      "object": {
//          "type": "<power_pylon | streetlight | tree | mailbox | hydrant>"
//          "count": int
//      }
//    }
//  }

//Приложение должно:
//- задавать местоположение объекта на карте или определять его автоматически,
//- позволять выбирать тип объекта,
//- собирать данные при отсутствии сети и восстанавливаться после своей перезагрузки,
//- отправлять собранные данные по электронному адресу, указанному пользователем.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TexasTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val mainNavController = rememberNavController()
                    NavHost(
                        navController = mainNavController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) { HomePage(toAddNewObjects = {
                            mainNavController.navigate(Screen.AddNewObject.route)
                        },
                        toScannedObjects = {
                            mainNavController.navigate(Screen.PreviousObjects.route)
                        }) }
                        composable(Screen.PreviousObjects.route) { PreviousObjectsPage() }
                        composable(Screen.AddNewObject.route) { AddNewObjectPage() }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TexasTheme {
        Greeting("Android")
    }
}