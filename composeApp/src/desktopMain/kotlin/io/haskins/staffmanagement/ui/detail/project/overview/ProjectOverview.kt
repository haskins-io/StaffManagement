 package io.haskins.staffmanagement.ui.detail.project.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem

@Composable
fun projectOverview(currentDetail: MutableState<ListItem>) {

    val project = ProjectDao.getInstance().project(currentDetail.value.id)

    val editing: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(10.dp)) {

        if (editing.value) {
            EditProjectOverview(project, editing)
        } else {
            ViewProjectOverview(project, editing)
        }
    }
}