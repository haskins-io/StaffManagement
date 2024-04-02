package io.haskins.staffmanagement.ui.detail.departments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.DepartmentDao
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.ui.components.TableHeader
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun AddDepartment(addingNew: MutableState<Boolean>) {

    var department by remember { mutableStateOf("") }

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
                cell { TableHeader("Department Name") }
                cell { TextField(department, { department = it }) }

            }

            row(modifier = Modifier) {
                cell { TableHeader("Department Head") }
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
                    DepartmentDao.getInstance().create(
                        name = department,
                        head = selected.id,
                    )

                    addingNew.value = false
                }) {
                Text("Save")
            }
        }
    }
}