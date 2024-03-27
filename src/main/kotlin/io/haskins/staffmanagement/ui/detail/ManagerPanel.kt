package io.haskins.staffmanagement.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.ManagerDao
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem

@Composable
fun managerPanel(currentDetail: MutableState<ListItem>) {

    val employees = ManagerDao.getInstance().managerReports(currentDetail.value.id)

    val vScrollState = rememberScrollState()

    Box(modifier = Modifier.padding(10.dp).verticalScroll(vScrollState)) {
        DataTable(
            columns = {
                headerBackground {
                    Box(modifier = Modifier.background(color = Color.LightGray))
                }
                column { Text("Name") }
            }
        ) {
            employees.forEach { employee ->

                row(modifier = Modifier) {

                    cell {

                        if (employee.departmentId == employee.managerId) {

                            Box(modifier = Modifier.clickable {
                                currentDetail.value = ListItem(employee.id, employee.name, type= FilterType.Managers.id)
                            }, contentAlignment = Alignment.CenterStart) {
                                Row {
                                    Icon(Icons.Filled.Person, "Manager")
                                    Text(employee.name,)
                                }

                            }
                        } else {
                            Box(modifier = Modifier.clickable {
                                currentDetail.value = ListItem(employee.id, employee.name, type= FilterType.Employees.id)
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