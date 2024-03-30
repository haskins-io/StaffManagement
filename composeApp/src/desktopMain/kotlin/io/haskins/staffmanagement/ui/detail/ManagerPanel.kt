package io.haskins.staffmanagement.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.ManagerDao
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.DetailTitle

@Composable
fun managerPanel(currentDetail: MutableState<ListItem>) {

    val employees = ManagerDao.getInstance().managerReports(currentDetail.value.id)

    val vScrollState = rememberScrollState()

    if (currentDetail.value.id == 0) {
        Text("Select a Manager")
    } else {
        Column {

            DetailTitle(currentDetail.value.name)

            Row {
                Box(modifier = Modifier.padding(10.dp).verticalScroll(vScrollState)) {
                    DataTable(
                        columns = {
                            column { Text("") }
                            column { Text("Name") }
                        }
                    ) {
                        employees.forEach { employee ->

                            var filter = FilterType.Employees.id

                            row(modifier = Modifier) {

                                cell {
                                    if (employee.isManager) {
                                        filter = FilterType.Managers.id
                                        Icon(Icons.Filled.Person, "Manager")
                                    }
                                }

                                cell {

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
    }
}