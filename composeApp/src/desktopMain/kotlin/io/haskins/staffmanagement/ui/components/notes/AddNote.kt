package io.haskins.staffmanagement.ui.components.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.ui.components.TableHeader
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextArea
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun AddNote(
    addingNew: MutableState<Boolean>,
    title: MutableState<String>,
    note: MutableState<String>,
    save: () -> Unit
) {

    Column(modifier = Modifier.padding(10.dp)) {

        DataTable(
            columns = {
                column { Text("") }
                column { Text("") }
            }
        ) {

            row(modifier = Modifier) {
                cell { TableHeader("Title") }
                cell { TextField(title.value, { title.value = it })  }
            }

            row(modifier = Modifier) {
                cell { TableHeader("Note") }
                cell {
                    TextArea(
                        modifier = Modifier.fillMaxWidth(),
                        value = note.value,
                        onValueChange = { note.value = it }
                    )
                }
            }
        }

        Row {
            DefaultButton(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    addingNew.value = false
                }
            ) {
                Text("Cancel")
            }

            DefaultButton(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    save()
                    addingNew.value = false
                }
            ) {
                Text("Save")
            }
        }
    }
}