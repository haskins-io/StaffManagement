package io.haskins.staffmanagement.ui.detail.employees.holidays

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.onClick
import androidx.compose.material.TextButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.utils.DateUtils
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddHoliday(
    currentDetail: MutableState<ListItem>,
    addingNew: MutableState<Boolean>
) {

    var showDateDialog by remember { mutableStateOf(false) }

    val state = rememberDateRangePickerState(
        initialSelectedStartDateMillis = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
        initialSelectedEndDateMillis = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
    )

    Column {

        DataTable(
            columns = {
                column { Text("") }
                column { Text("") }
            }
        ) {

            row(modifier = Modifier) {
                cell { Text(text = "Period") }
                cell {
                    Text(
                        text = DateUtils.epochToLocalDate(state.selectedStartDateMillis ?: 0).toString() +
                                " - "
                                + DateUtils.epochToLocalDate(state.selectedEndDateMillis ?: 0).toString(),
                        Modifier.onClick {
                            showDateDialog = true
                        }
                    )
                }
            }
        }
        Row {
            DefaultButton({
                addingNew.value = false
            }) {
                Text("Cancel")
            }

            DefaultButton({
                EmployeeDao.getInstance().addHoliday(
                    employeeId = currentDetail.value.id,
                    start = state.selectedStartDateMillis!!,
                    end = state.selectedEndDateMillis!!
                )
                addingNew.value = false
            }) {
                Text("Save")
            }
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
                DateRangePicker(state = state)
            }
        )
    }
}