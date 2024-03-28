package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.StaffManagementIcons
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import org.jetbrains.jewel.ui.component.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun projectResources(currentDetail: MutableState<ListItem>) {

    val employees = ProjectDao.getInstance().projectResources(currentDetail.value.id)

    val vScrollState = rememberScrollState()

    var addingNew by remember { mutableStateOf(false) }

    Column {

        if (addingNew) {
            DefaultButton({
                addingNew = false
            }) {
                Text("Cancel")
            }

        } else {

            Row(Modifier.align(Alignment.End)) {
                Tooltip({
                    Text("Add Resource")
                }) {
                    IconButton({
                        addingNew = true
                    }, modifier = Modifier.size(30.dp).padding(5.dp)) {
                        Icon("icons/add.svg", "Add Resource", StaffManagementIcons::class.java)
                    }
                }
            }

            Row {
                Box(modifier = Modifier.padding(10.dp).verticalScroll(vScrollState)) {
                    DataTable(
                        columns = {
                            headerBackground {
                                Box(modifier = Modifier.background(color = Color.LightGray))
                            }
                            column { Text("Name") }
                            column { Text("Allocation") }
                            column { Text("Cost") }
                        }
                    ) {

                        employees.forEach { employee ->
                            row(modifier = Modifier) {
                                cell { Text(text = employee.name) }
                                cell { Text(text = employee.allocation.toString()) }
                                cell { Text(text = employee.cost.toString()) }
                            }
                        }
                    }
                }
            }
        }
    }
}