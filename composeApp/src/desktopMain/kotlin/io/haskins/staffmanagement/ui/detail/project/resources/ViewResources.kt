package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun ColumnScope.ViewResources(
    currentDetail: MutableState<ListItem>,
    addingNew: MutableState<Boolean>,
    deleteDialog: MutableState<Boolean>
) {

    val resources = ProjectDao.getInstance().resources(currentDetail.value.id)

    val allocation: MutableState<String> = remember { mutableStateOf("0") }
    val selectedResource: MutableState<Int> = remember { mutableStateOf(0) }

    val isEditing: MutableState<Boolean> = remember { mutableStateOf(false) }

    Row(Modifier.align(Alignment.End).padding(10.dp)) {
        Tooltip({
            Text("Add Resource")
        }) {
            IconButton({
                addingNew.value = true
            }, modifier = Modifier.size(25.dp).padding(5.dp)) {
                Icon("icons/add.svg", "Add Resource", StaffManagementIcons::class.java)
            }
        }
    }

    Row {
        DataTable(
            columns = {
                headerBackground {
                    Box(modifier = Modifier.background(color = Color.LightGray))
                }
                column { Text("Name") }
                column { Text("Allocation") }
                column { Text("Cost") }
                column { Text("") }
            }
        ) {

            resources.forEach { resource ->

                row(modifier = Modifier) {
                    cell { Text(text = resource.name) }

                    if (isEditing.value && resource.id == selectedResource.value) {
                        cell { TextField(allocation.value, { allocation.value = it }) }
                    } else {
                        cell { Text(text = resource.allocation.toString()) }
                    }

                    cell { Text(text = resource.cost.toString()) }
                    cell {
                        if (isEditing.value && resource.id == selectedResource.value) {
                            EditResourceButtons(
                                isEditing,
                                selectedResource,
                                allocation
                            )
                        } else {
                            ViewResourceButtons(
                                resource,
                                deleteDialog,
                                isEditing,
                                selectedResource
                            )
                        }
                    }
                }
            }
        }
    }
}