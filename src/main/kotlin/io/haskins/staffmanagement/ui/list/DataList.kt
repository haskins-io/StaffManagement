package io.haskins.staffmanagement.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.dao.DepartmentDao
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.dao.ManagerDao
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListFilter
import io.haskins.staffmanagement.models.ListItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dataList(currentDetail: MutableState<ListItem>) {

    val filterOptions = arrayOf(
        ListFilter(FilterType.Projects.id, "Projects"),
        ListFilter(FilterType.Departments.id, "Departments"),
        ListFilter(FilterType.Managers.id, "Managers"),
        ListFilter(FilterType.Employees.id, "Employees"),
    )

    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(filterOptions[0]) }

    Column {
        Row {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selected.type_name,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    filterOptions.forEach { item ->
                        DropdownMenuItem(
                            content = { Text(text = item.type_name) },
                            onClick = {
                                selected = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Row {
            Column {
                listBody(
                    selected,
                    currentDetail
                )
            }
        }
    }
}

@Composable
fun listBody(filter: ListFilter,
             currentDetail: MutableState<ListItem>
) {

    var data = listOf<ListItem>()

    when(filter.type_id) {
        FilterType.Projects.id -> data = ProjectDao.getInstance().allProjects()
        FilterType.Departments.id -> data = DepartmentDao.getInstance().allDepartments()
        FilterType.Managers.id -> data = ManagerDao.getInstance().allManagers()
        FilterType.Employees.id -> data = EmployeeDao.getInstance().allEmployees()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(items = data) { row ->
                Row {
                    Box(modifier = Modifier.clickable {
                        currentDetail.value = row
                    }, contentAlignment = Alignment.CenterStart) {
                        Text(row.name)
                    }
                }
            }
        }
    }
}
