package io.haskins.staffmanagement.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.detail.detailPanel
import io.haskins.staffmanagement.ui.list.dataList
import org.jetbrains.jewel.ui.component.HorizontalSplitLayout

@Composable
fun twoColumnLayout() {

    val currentDetail: MutableState<ListItem> = remember { mutableStateOf(ListItem(0,"",1)) };

    HorizontalSplitLayout(
        first = { dataList(currentDetail) },
        second = { detailPanel(currentDetail) },
        initialDividerPosition = 250.dp
    )
}