package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.AddButtonRow
import io.haskins.staffmanagement.ui.components.notes.AddNote
import io.haskins.staffmanagement.ui.components.notes.NotesView

@Composable
fun ProjectNotes(currentDetail: MutableState<ListItem>) {

    val notes = ProjectDao.getInstance().notes(currentDetail.value.id)

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(10.dp)) {

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

            AddButtonRow(
                "Add Note",
                addingNew
            )

            Row {
                NotesView(notes)
            }
        }
    }
}