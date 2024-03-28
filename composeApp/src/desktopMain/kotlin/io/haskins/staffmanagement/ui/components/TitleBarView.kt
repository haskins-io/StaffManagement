package io.haskins.staffmanagement.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.Tooltip
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls
import io.haskins.staffmanagement.StaffManagementIcons

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DecoratedWindowScope.TitleBarView() {

    TitleBar(Modifier.newFullscreenControls()) {

        Text(title)

        Row(Modifier.align(Alignment.End)) {
            Tooltip({
                Text("Settings")
            }) {
                IconButton({
                    //
                }, modifier = Modifier.size(30.dp).padding(5.dp)) {
                    Icon("icons/cog.svg", "Settings", StaffManagementIcons::class.java, tint = Color.White)
                }
            }
        }
    }
}