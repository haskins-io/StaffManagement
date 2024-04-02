package io.haskins.staffmanagement.ui.detail.managers

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.DetailTitle
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.*

@Composable
fun managerPanel(currentDetail: MutableState<ListItem>) {

    var selectedTabIndex by remember { mutableStateOf(0) }

    val theTabs = listOf("Overview", "Reports","Holidays")
    val tabs = mutableListOf<TabData>()

    if (currentDetail.value.id == 0) {
        ManagerOverview(currentDetail)
    } else {
        theTabs.forEachIndexed { index, title ->
            val data =
                TabData.Default(
                    selected = index == selectedTabIndex,
                    content = {tabState ->
                        SimpleTabContent(
                            state = tabState,
                            label = { Text(title) }
                        )
                    },
                    onClick = { selectedTabIndex = index }
                )

            tabs.add(data)
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            DetailTitle(currentDetail.value.name)

            Row(verticalAlignment = Alignment.CenterVertically) {
                TabStrip(tabs, modifier = Modifier.weight(1f))
            }
            Row {
                Divider(Orientation.Horizontal)
            }

            when (selectedTabIndex) {
                0 -> ManagerOverview(currentDetail)
                1 -> ManageReports(currentDetail)
                2 -> ManagerHolidays()
            }
        }
    }
}