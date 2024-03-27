package io.haskins.staffmanagement.ui.detail.employees

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.models.ListItem

@Composable
fun employeePanel(currentDetail: MutableState<ListItem>) {

    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Overview", "Notes", "Holidays")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> employeeOverview(currentDetail)
            1 -> employeeNotes(currentDetail)
            2 -> employeeHolidays(currentDetail)
        }
    }
}