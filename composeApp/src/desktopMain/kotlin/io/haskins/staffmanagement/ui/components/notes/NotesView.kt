package io.haskins.staffmanagement.ui.components.notes

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.models.Note
import org.jetbrains.jewel.ui.component.Text

@Composable
fun NotesView(notes: List<Note>) {

    DataTable(
        columns =  {
            column(Modifier.size(50.dp)) { Text("Created") }
            column(Modifier.size(50.dp)) { Text("Title") }
            column { Text("Note") }
        }
    ) {
        notes.forEach { note ->
            row(modifier = Modifier) {
                cell(Modifier.size(50.dp)) { Text(text = note.created.toString()) }
                cell(Modifier.size(50.dp)) { Text(text = note.title) }
                cell { Text(text = note.note) }
            }
        }
    }
}