package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import org.jetbrains.jewel.ui.component.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.StaffManagementIcons
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.AddNote
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.Tooltip

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectNotes(currentDetail: MutableState<ListItem>) {

    val notes = ProjectDao.getInstance().notes(currentDetail.value.id)

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding()) {

        if (addingNew.value) {

            val title: MutableState<String> = remember { mutableStateOf("") }
            val note: MutableState<String> = remember { mutableStateOf("") }

            AddNote(
                addingNew,
                title,
                note,
                save = {
                    ProjectDao.getInstance().addNote(
                        projectId = currentDetail.value.id,
                        title = title.value,
                        note = note.value
                    )
                }
            )

        } else {

            Row(Modifier.align(Alignment.End).padding(end = 10.dp)) {
                Tooltip({
                    Text("Add Note")
                }) {
                    IconButton({
                        addingNew.value = true
                    }, modifier = Modifier.size(25.dp).padding(5.dp)) {
                        Icon("icons/add.svg", "Add Note", StaffManagementIcons::class.java)
                    }
                }
            }

            Row {
                DataTable(
                    columns =  {
                        column(Modifier.size(50.dp)) { Text("Title") }
                        column { Text("Note") }
                    }
                ) {
                    notes.forEach() {note ->
                        row(modifier = Modifier) {
                            cell(Modifier.size(50.dp)) { Text(text = note.title) }
                            cell { Text(text = note.note) }
                        }
                    }
                }
            }
        }
    }
}