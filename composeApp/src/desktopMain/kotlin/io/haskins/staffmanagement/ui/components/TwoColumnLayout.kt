package io.haskins.staffmanagement.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.detail.detailPanel
import io.haskins.staffmanagement.ui.list.dataList
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.ui.component.HorizontalSplitLayout

@Composable
fun twoColumnLayout() {

    val currentDetail: MutableState<ListItem> = remember { mutableStateOf(ListItem(0,"",1)) };
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