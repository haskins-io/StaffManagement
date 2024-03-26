package io.haskins.staffmanagement.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.ui.detail.*
import io.haskins.staffmanagement.ui.list.*
import io.haskins.staffmanagement.models.ListItem

@Composable
fun twoColumnLayout() {

    val currentDetail: MutableState<ListItem> = remember { mutableStateOf(ListItem(0,"",1)) };

    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(0.4f), contentAlignment = Alignment.Center) {
            dataList(currentDetail)
        }
        detailPanel(currentDetail)
    }
}