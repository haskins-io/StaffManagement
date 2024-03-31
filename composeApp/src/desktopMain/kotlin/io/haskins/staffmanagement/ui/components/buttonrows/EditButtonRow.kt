package io.haskins.staffmanagement.ui.components.buttonrows

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.StaffManagementIcons
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.Tooltip

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.EditButtonRow(
    toolTip: String,
    editing: MutableState<Boolean>
) {

    Row(Modifier.align(Alignment.End).padding(end = 10.dp)) {
        Tooltip({
            Text(toolTip)
        }) {
            IconButton({
                editing.value = true
            }, modifier = Modifier.size(25.dp).padding(5.dp)) {
                Icon("icons/pencil-2.svg", toolTip, StaffManagementIcons::class.java)
            }
        }
    }
}