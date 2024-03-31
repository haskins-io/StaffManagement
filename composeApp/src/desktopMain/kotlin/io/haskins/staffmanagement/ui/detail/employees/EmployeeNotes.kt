package io.haskins.staffmanagement.ui.detail.employees

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.dao.EmployeeDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.AddButtonRow
import io.haskins.staffmanagement.ui.components.notes.AddNote
import io.haskins.staffmanagement.ui.components.notes.NotesView

@Composable
fun employeeNotes(currentDetail: MutableState<ListItem>) {

    val notes = EmployeeDao.getInstance().notes(currentDetail.value.id)

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
                    EmployeeDao.getInstance().addNote(
                        employeeId = currentDetail.value.id,
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

            NotesView(notes)
        }
    }
}