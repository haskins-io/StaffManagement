package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.github.windedge.table.RowsBuilder
import io.haskins.staffmanagement.StaffManagementIcons
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.ConfirmDialog
import org.jetbrains.jewel.ui.component.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectResources(currentDetail: MutableState<ListItem>) {

    val resources = ProjectDao.getInstance().projectResources(currentDetail.value.id)

    val allocation: MutableState<String> = remember { mutableStateOf("0") }

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    val selectedResource: MutableState<Int> = remember { mutableStateOf(0) }

    val isEditing: MutableState<Boolean> = remember { mutableStateOf(false) }
    val deleteDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding()) {

        if (addingNew.value) {
            AllocateResource(addingNew, projectId = currentDetail.value.id)
        } else {

            Row(Modifier.align(Alignment.End).padding(end = 10.dp)) {
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
                        if (isEditing.value && resource.id == selectedResource.value) {
                            row(modifier = Modifier) {
                                cell { Text(text = resource.name) }
                                cell { TextField(allocation.value, { allocation.value = it }) }
                                cell { Text(text = resource.cost.toString()) }
                                cell {
                                    EditResourceButtons(
                                        isEditing,
                                        selectedResource,
                                        allocation
                                    )
                                }
                            }
                        } else {
                            row(modifier = Modifier) {
                                cell { Text(text = resource.name) }
                                cell { Text(text = resource.allocation.toString()) }
                                cell { Text(text = resource.cost.toString()) }
                                cell {
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
    }

    if (deleteDialog.value && selectedResource.value > 0) {

        ConfirmDialog(
            title = "Remove Resource",
            message = "Remove the resource from the Project",
            confirm = {
                ProjectDao.getInstance().removeResource(selectedResource.value)
                deleteDialog.value = false
            },
            cancel = {
                deleteDialog.value = false
            }
        )
    }
}
