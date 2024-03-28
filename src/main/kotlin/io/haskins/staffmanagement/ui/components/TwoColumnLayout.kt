package io.haskins.staffmanagement.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.detail.detailPanel
import io.haskins.staffmanagement.ui.list.dataList
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import java.awt.Cursor

private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun twoColumnLayout() {

    val currentDetail: MutableState<ListItem> = remember { mutableStateOf(ListItem(0,"",1)) };

    val splitterState = rememberSplitPaneState()

    HorizontalSplitPane(
        splitPaneState = splitterState
    ) {
        first(250.dp) {
            dataList(currentDetail)
        }
        second(50.dp) {
            detailPanel(currentDetail)
        }
        splitter {
            visiblePart {
                Box(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colors.background)
                )
            }
            handle {
                Box(
                    Modifier
                        .markAsHandle()
                        .cursorForHorizontalResize()
                        .background(SolidColor(Color.Gray), alpha = 0.50f)
                        .width(9.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}