package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.RowsBuilder
import io.haskins.staffmanagement.StaffManagementIcons
import io.haskins.staffmanagement.models.ProjectResource
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.Tooltip


@OptIn(ExperimentalFoundationApi::class)
@Composable

fun BoxScope.ViewResourceButtons(
    resource: ProjectResource,
    deleteDialog: MutableState<Boolean>,
    isEditing: MutableState<Boolean>,
    selectedResource: MutableState<Int>
) {

    Row(modifier = Modifier.align(alignment = Alignment.CenterEnd)) {
        Tooltip({
            Text("Edit")
        }) {
            IconButton({
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