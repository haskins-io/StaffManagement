package io.haskins.staffmanagement.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.detail.employees.employeePanel
import io.haskins.staffmanagement.ui.detail.project.projectPanel

@Composable
fun detailPanel(currentDetail: MutableState<ListItem>) {
    when(currentDetail.value.type) {
        0 -> currentDetailStatus { Text("Select list item") }
        FilterType.Projects.id -> projectPanel(currentDetail)
        FilterType.Departments.id -> departmentPanel(currentDetail)
        FilterType.Managers.id -> managerPanel(currentDetail)
        FilterType.Employees.id -> employeePanel(currentDetail)
    }
}

@Composable
fun currentDetailStatus(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        content()
    }
}