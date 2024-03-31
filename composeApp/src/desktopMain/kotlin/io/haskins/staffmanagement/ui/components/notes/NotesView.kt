package io.haskins.staffmanagement.ui.components.notes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.models.dao.Note
import org.jetbrains.jewel.ui.component.Text

@Composable
fun NotesView(notes: List<Note>) {

    DataTable(
        columns =  {
            column { Text("Created") }
            column { Text("Title") }
            column { Text("Note") }
        }
    ) {
        notes.forEach { note ->
            row(modifier = Modifier) {
                cell { Text(text = note.created.toString()) }
                cell { Text(text = note.title) }
                cell { Text(text = note.note) }
            }
        }
    }
}