package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.ui.components.TableHeader
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun AllocateResource(
    addingNew: MutableState<Boolean>,
    projectId: Int
) {

    var allocation by remember { mutableStateOf("0") }

    val employees = EmployeeDao.getInstance().employees()
    var selected by remember { mutableStateOf(employees[0]) }

    Column(Modifier.padding(5.dp)) {

        DataTable(
            columns = {
                column { Text("") }
                column { Text("") }
            }
        ) {

            row(modifier = Modifier) {
                cell { TableHeader("Resource") }
                cell {
                    Dropdown(
                        menuContent = {
                            employees.forEach { item ->
                                selectableItem(
                                    selected = selected == item,
                                    onClick = { selected = item },
                                ) {
                                    Text(item.name)
                                }
                            }
                        }
                    ) {
                        Text(selected.name)
                    }
                }
            }

            row(Modifier.padding(5.dp)) {
                cell { TableHeader("Allocation") }
                cell { TextField(allocation, { allocation = it }) }
            }
        }

        Row {

            DefaultButton(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    addingNew.value = false
                }
            ) {
                Text("Cancel")
            }

            DefaultButton(
                modifier = Modifier.padding(5.dp),
                onClick = {
                ProjectDao.getInstance().allocateResource(
                    projectId = projectId,
                    employeeId = selected.id,
                    allocationPerc = allocation.toInt()
                )

                addingNew.value = false
            }) {
                Text("Save")
            }
        }
    }
}