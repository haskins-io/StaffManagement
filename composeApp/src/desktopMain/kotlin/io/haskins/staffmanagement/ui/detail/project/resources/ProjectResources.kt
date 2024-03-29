package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.ConfirmDialog


@Composable
fun ProjectResources(currentDetail: MutableState<ListItem>) {

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }
    val selectedResource: MutableState<Int> = remember { mutableStateOf(0) }
    val deleteDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding()) {

        if (addingNew.value) {
            AllocateResource(addingNew, projectId = currentDetail.value.id)
        } else {

            ViewResources(
                currentDetail,
                addingNew,
                deleteDialog
            )
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
