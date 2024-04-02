package io.haskins.staffmanagement.ui.detail.employees

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.TableHeader
import org.jetbrains.jewel.ui.component.Text

@Composable
fun employeeOverview(currentDetail: MutableState<ListItem>) {

    val projects = EmployeeDao.getInstance().projects(currentDetail.value.id)

    val vScrollState = rememberScrollState()

    val showDialog =  remember { mutableStateOf(false) }

    if (showDialog.value) {
        newEmployeeDialog(value = "", setShowDialog = {
            showDialog.value = it
        }) {
           println("Here")
        }
    }

    Box(modifier = Modifier.padding(10.dp).verticalScroll(vScrollState)) {
        DataTable(
            columns = {
                column { TableHeader("Name") }
                column { TableHeader("Allocation") }
                column { TableHeader("Cost") }
            }
        ) {

            projects.forEach { employee ->
                row(modifier = Modifier) {
                    cell { Text(text = employee.name) }
                    cell { Text(text = employee.allocation.toString()) }
                    cell { Text(text = employee.cost.toString()) }
                }
            }
        }
    }
}
