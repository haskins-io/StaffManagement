package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.enums.ProjectPriority
import io.haskins.staffmanagement.enums.ProjectStatus
import io.haskins.staffmanagement.models.DropDownOption
import io.haskins.staffmanagement.models.dao.Project
import io.haskins.staffmanagement.ui.components.TableHeader
import io.haskins.staffmanagement.ui.components.project.DropDownView
import io.haskins.staffmanagement.utils.DateUtils
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextArea
import org.jetbrains.jewel.ui.component.TextField
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddProject(addingNew: MutableState<Boolean>) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("0") }
    var progress by remember { mutableStateOf("0") }
    val status: MutableState<Int> = remember { mutableStateOf(1) }
    val priority: MutableState<Int> = remember { mutableStateOf(1) }

    var showDateDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis  =LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    )

    DataTable(
        columns = {
            column { Text("") }
            column { Text("") }
        }
    ) {

        row(modifier = Modifier) {
            cell { TableHeader("Name") }
            cell { TextField(name, { name = it  }) }
        }

        row(modifier = Modifier) {
            cell { TableHeader("Description") }
            cell {
                TextArea(
                    value = description,
                    onValueChange = { description = it  })
            }
        }

        row(modifier = Modifier) {
            cell { TableHeader("Code") }
            cell { TextField(code, { code = it  }) }
        }

        row(modifier = Modifier) {
            cell { TableHeader("Budget") }
            cell { TextField(budget, { budget = it  }) }
        }

        row(modifier = Modifier) {
            cell { TableHeader("Status") }
            cell {
                DropDownView(
                    status,
                    listOf(
                        DropDownOption(ProjectStatus.NotSet.id, "Select Status"),
                        DropDownOption(ProjectStatus.Red.id, "Red"),
                        DropDownOption(ProjectStatus.Amber.id, "Amber"),
                        DropDownOption(ProjectStatus.Green.id, "Green"),
                    )
                )
            }
        }

        row(modifier = Modifier) {
            cell { TableHeader("priority") }
            cell {
                DropDownView(
                    priority,
                    listOf(
                        DropDownOption(ProjectPriority.NotSet.id, "Select Priority"),
                        DropDownOption(ProjectPriority.Now.id, "Now"),
                        DropDownOption(ProjectPriority.Next.id, "Next"),
                        DropDownOption(ProjectPriority.Future.id, "Future"),
                    )
                )
            }
        }

        row(modifier = Modifier) {
            cell { TableHeader("Due") }
            cell {
                Text(
                    text = DateUtils.epochToLocalDate(datePickerState.selectedDateMillis!!).toString(),
                    Modifier.onClick {
                        showDateDialog = true
                    }
                )
            }
        }

        row(modifier = Modifier) {
            cell { TableHeader("Progress") }
            cell { TextField(progress, { progress = it  }) }
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
                val prj = Project(
                    0,
                    name,
                    description,
                    code,
                    budget.toInt(),
                    0,
                    status.value,
                    priority.value,
                    DateUtils.epochToLocalDate(datePickerState.selectedDateMillis!!),
                    progress.toInt(),
                )

                ProjectDao.getInstance().create(prj)

                addingNew.value = false
            }
        ) {
            Text("Save")
        }
    }

    if (showDateDialog) {
        DatePickerDialog(
            onDismissRequest = {
                showDateDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDateDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDateDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            },
            content = {
                DatePicker(state = datePickerState)
            }
        )
    }
}