package io.haskins.staffmanagement.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.TableCell

@Composable
fun managerPanel(currentDetail: MutableState<ListItem>) {

    var employees = EmployeeDao.getInstance().employeesForManager(currentDetail.value.id)

    val column1Weight = .3f
    val column2Weight = .7f

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Row(Modifier.background(Color.LightGray)) {
                TableCell(text = "Col 1", weight = column1Weight)
                TableCell(text = "Col 2", weight = column2Weight)
            }
        }

        items(items = employees) { employee ->
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = "1", weight = column1Weight)
                TableCell(text = employee.name, weight = column2Weight)
            }
        }
    }
}