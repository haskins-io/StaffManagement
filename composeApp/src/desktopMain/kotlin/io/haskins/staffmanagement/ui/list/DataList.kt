package io.haskins.staffmanagement.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Text
import io.haskins.staffmanagement.dao.DepartmentDao
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.dao.ManagerDao
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListFilter
import io.haskins.staffmanagement.models.ListItem
import org.jetbrains.jewel.intui.standalone.styling.light
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.styling.DividerStyle

@Composable
fun dataList(currentDetail: MutableState<ListItem>) {

    val filterOptions = arrayOf(
        ListFilter(FilterType.Projects.id, "Projects"),
        ListFilter(FilterType.Departments.id, "Departments"),
        ListFilter(FilterType.Managers.id, "Managers"),
        ListFilter(FilterType.Employees.id, "Employees"),
    )

    var selected by remember { mutableStateOf(filterOptions[0]) }

    Column(Modifier.layoutId("first")) {
        Row {
            Dropdown(
                modifier = Modifier.fillMaxWidth(),
                menuContent = {
                    filterOptions.forEach { item ->
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
        FilterType.Departments.id -> data = DepartmentDao.getInstance().departmentHeads()
        FilterType.Managers.id -> data = ManagerDao.getInstance().managers()
        FilterType.Employees.id -> data = EmployeeDao.getInstance().employees()
    }

    LazyColumn {
        items(items = data) {
            row ->
            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            currentDetail.value = row
                        }
                        .padding(5.dp)
                ) {
                    Text(row.name)
                }

                Row {
                    Divider(Orientation.Horizontal, color = Color.LightGray, style = DividerStyle.light())
                }
            }
        }
    }
}
