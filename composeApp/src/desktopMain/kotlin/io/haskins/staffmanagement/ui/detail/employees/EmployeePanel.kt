package io.haskins.staffmanagement.ui.detail.employees

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.DetailTitle
import io.haskins.staffmanagement.ui.detail.employees.holidays.employeeHolidays
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.*

@Composable
fun employeePanel(currentDetail: MutableState<ListItem>) {

    var selectedTabIndex by remember { mutableStateOf(0) }

    if (currentDetail.value.id == 0) {
        Text("Select an Employee")
    } else {
        val theTabs = listOf("Overview", "Notes", "Holidays")
        val tabs = mutableListOf<TabData>()

        theTabs.forEachIndexed { index, title ->
            val data =
                TabData.Default(
                    selected = index == selectedTabIndex,
                    content = { tabState ->
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
                0 -> employeeOverview(currentDetail)
                1 -> employeeNotes(currentDetail)
                2 -> employeeHolidays(currentDetail)
            }
        }
    }
}