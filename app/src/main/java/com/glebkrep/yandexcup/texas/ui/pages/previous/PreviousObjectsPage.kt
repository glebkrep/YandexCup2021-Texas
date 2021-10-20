package com.glebkrep.yandexcup.texas.ui.pages.previous

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PreviousObjectsPage(previousObjectsVM: PreviousObjectsPageVM = viewModel()) {

    val withPadding = Modifier.padding(16.dp)
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
}