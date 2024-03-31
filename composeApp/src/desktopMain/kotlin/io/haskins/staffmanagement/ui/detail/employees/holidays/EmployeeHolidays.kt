package io.haskins.staffmanagement.ui.detail.employees.holidays

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.buttonrows.AddButtonRow
import org.jetbrains.jewel.ui.component.Text

@Composable
fun employeeHolidays(currentDetail: MutableState<ListItem>) {

    val holidays = EmployeeDao.getInstance().holidays(currentDetail.value.id)

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(10.dp)) {

        if (addingNew.value) {

            AddHoliday(currentDetail, addingNew)

        } else {
            AddButtonRow(
                "Add Holiday",
                addingNew
            )

            Row {
                DataTable(
                    columns =  {
                        column { Text("Start") }
                        column { Text("End") }
                    }
                ) {
                    holidays.forEach { holiday ->
                        row(modifier = Modifier) {
                            cell { Text(text = holiday.start.toString()) }
                            cell { Text(text = holiday.end.toString()) }
                        }
                    }
                }
            }
        }
    }
}