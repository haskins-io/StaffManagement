package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.dao.ProjectDao
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun AllocateResource(addingNew: MutableState<Boolean>, projectId: Int) {

    var allocation by remember { mutableStateOf("0") }

    val employees = EmployeeDao.getInstance().employees()
    var selected by remember { mutableStateOf(employees[0]) }

    Column {

        Row {
            Row {
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

        Row {
            TextField(allocation, { allocation = it })
        }

        Row {
            DefaultButton({
                addingNew.value = false
            }) {
                Text("Cancel")
            }

            DefaultButton({
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