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
import io.haskins.staffmanagement.StaffManagementIcons
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import org.jetbrains.jewel.ui.component.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectResources(currentDetail: MutableState<ListItem>) {

    val resources = ProjectDao.getInstance().projectResources(currentDetail.value.id)

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    val selectedResource = remember { mutableStateOf(0) }

    val isEditing = remember { mutableStateOf(false) }
    val deleteDialog = remember { mutableStateOf(false) }

    var allocation by remember { mutableStateOf("0") }

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
                                cell { TextField(allocation, { allocation = it }) }
                                cell { Text(text = resource.cost.toString()) }
                                cell {
                                    Row(modifier = Modifier.align(alignment = Alignment.CenterEnd)) {
                                        Tooltip({
                                            Text("Save")
                                        }) {
                                            IconButton({
                                                ProjectDao.getInstance().updateResource(selectedResource.value, allocation.toInt())
                                                isEditing.value = false
                                            }, modifier = Modifier.size(25.dp).padding(5.dp)) {
                                                Icon("icons/check-2.svg", "Save", StaffManagementIcons::class.java)
                                            }
                                        }

                                        Tooltip({
                                            Text("Revert")
                                        }) {
                                            IconButton({
                                                isEditing.value = false
                                            }, modifier = Modifier.size(25.dp).padding(5.dp)) {
                                                Icon("icons/remove-square.svg", "Revert", StaffManagementIcons::class.java)
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            row(modifier = Modifier) {
                                cell { Text(text = resource.name) }
                                cell { Text(text = resource.allocation.toString()) }
                                cell { Text(text = resource.cost.toString()) }
                                cell {
                                    Row(modifier = Modifier.align(alignment = Alignment.CenterEnd)) {
                                        Tooltip({
                                            Text("Edit")
                                        }) {
                                            IconButton({
                                                allocation = resource.allocation.toString()
                                                selectedResource.value = resource.id
                                                isEditing.value = true
                                            }, modifier = Modifier.size(25.dp).padding(5.dp)) {
                                                Icon("icons/pencil-2.svg", "Edit", StaffManagementIcons::class.java)
                                            }
                                        }

                                        Tooltip({
                                            Text("Delete")
                                        }) {
                                            IconButton({
                                                selectedResource.value = resource.id
                                                deleteDialog.value = true
                                            }, modifier = Modifier.size(25.dp).padding(5.dp)) {
                                                Icon("icons/bin.svg", "Delete", StaffManagementIcons::class.java)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (deleteDialog.value && selectedResource.value > 0) {

        AlertDialog(
            onDismissRequest = {
                deleteDialog.value = false
            },
            title = {
                Text(text = "Remove Resource")
            },
            text = {
                Text(text = "Remove the resource from the Project")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        ProjectDao.getInstance().removeResource(selectedResource.value)
                        deleteDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        deleteDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}
