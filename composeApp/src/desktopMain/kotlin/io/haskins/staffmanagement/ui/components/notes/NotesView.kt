package io.haskins.staffmanagement.ui.components.notes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.models.dao.Note
import io.haskins.staffmanagement.ui.components.TableHeader
import org.jetbrains.jewel.ui.component.Text

@Composable
fun NotesView(notes: List<Note>) {

    DataTable(
        columns =  {
            column { TableHeader("Created") }
            column { TableHeader("Title") }
            column { TableHeader("Note") }
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