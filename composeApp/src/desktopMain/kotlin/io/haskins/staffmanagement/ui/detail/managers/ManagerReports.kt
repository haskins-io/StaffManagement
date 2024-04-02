package io.haskins.staffmanagement.ui.detail.managers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.ManagerDao
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.TableHeader
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ManageReports(currentDetail: MutableState<ListItem>) {

    val employees = ManagerDao.getInstance().managerReports(currentDetail.value.id)

    val vScrollState = rememberScrollState()

    Row {
        Box(modifier = Modifier.padding(20.dp).verticalScroll(vScrollState)) {
            DataTable(
                columns = {
                    column(Modifier.width(30.dp)) { TableHeader("") }
                    column(Modifier.fillMaxWidth()) { TableHeader("Name") }
                }
            ) {
                employees.forEach { employee ->

                    var filter = FilterType.Employees.id

                    row(modifier = Modifier) {

                        cell(Modifier.width(30.dp)) {
                            if (employee.isManager) {
                                filter = FilterType.Managers.id
                                Icon(Icons.Filled.Person, "Manager")
                            }
                        }

                        cell(Modifier.fillMaxWidth()) {

                            Box(modifier = Modifier.clickable {
                                currentDetail.value = ListItem(employee.id, employee.name, type = filter)
                            }, contentAlignment = Alignment.CenterStart) {
                                Text(employee.name)
                            }
                        }
                    }
                }
            }
        }
    }
}