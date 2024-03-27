package io.haskins.staffmanagement.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
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

@OptIn(ExperimentalMaterial3Api::class)
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
                    expanded = it
                }
            ) {
                TextField(
                    value = selected.name,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    filterOptions.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.name) },
                            onClick = {
                                selected = item
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
        Row {
            listBody(
                selected,
                currentDetail
            )
        }
    }
}

@Composable
fun listBody(filter: ListFilter,
             currentDetail: MutableState<ListItem>
) {

    var data = listOf<ListItem>()

    when(filter.id) {
        FilterType.Projects.id -> data = ProjectDao.getInstance().projects()
        FilterType.Departments.id -> data = DepartmentDao.getInstance().allDepartments()
        FilterType.Managers.id -> data = ManagerDao.getInstance().managers()
        FilterType.Employees.id -> data = EmployeeDao.getInstance().employees()
    }


    LazyColumn {
        items(items = data) { row ->
            Row {
                Box(modifier = Modifier.clickable {
                    currentDetail.value = row
                }, contentAlignment = Alignment.CenterStart) {
                    ListItem(
                        headlineContent = { Text(row.name) },
                    )
                }
            }
        }
    }

}
