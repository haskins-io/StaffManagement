package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.StaffManagementIcons
import io.haskins.staffmanagement.dao.ProjectDao
import org.jetbrains.jewel.ui.component.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.EditResourceButtons(
    isEditing: MutableState<Boolean>,
    selectedResource: MutableState<Int>,
    allocation: MutableState<String>
) {

    Row(modifier = Modifier.align(alignment = Alignment.CenterEnd)) {
        Tooltip({
            Text("Save")
        }) {
            IconButton({
                ProjectDao.getInstance().updateResource(selectedResource.value, allocation.value.toInt())
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