package io.haskins.staffmanagement.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.TableCell

@Composable
fun departmentPanel(currentDetail: MutableState<ListItem>) {

    val employees = EmployeeDao.getInstance().employeesForDepartment(currentDetail.value.id)

    val column2Weight = .7f

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        items(items = employees) { employee ->
            Row(Modifier.fillMaxWidth()) {

                if (employee.departmentId == employee.managerId) {

                    Box(modifier = Modifier.clickable {
                        currentDetail.value = ListItem(employee.id, employee.name, type= FilterType.Managers.id)
                    }, contentAlignment = Alignment.CenterStart) {
                        TableCell(text = employee.name, weight = column2Weight)
                    }
                } else {
                    Box(modifier = Modifier.clickable {
                        currentDetail.value = ListItem(employee.id, employee.name, type= FilterType.Employees.id)
                    }, contentAlignment = Alignment.CenterStart) {
                        TableCell(text = employee.name, weight = column2Weight)
                    }
                }
            }
        }
    }
}