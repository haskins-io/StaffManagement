package io.haskins.staffmanagement.ui.components

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.detail.detailPanel
import io.haskins.staffmanagement.ui.list.dataList
import org.jetbrains.jewel.ui.component.HorizontalSplitLayout

@Composable
fun TwoColumnLayout() {

    val currentDetail: MutableState<ListItem> = remember { mutableStateOf(ListItem(0,"",1)) }

    Surface(
        color = Color.White,
    ) {
        HorizontalSplitLayout(
            first = { dataList(currentDetail) },
            second = { detailPanel(currentDetail) },
            initialDividerPosition = 250.dp
        )
    }
}