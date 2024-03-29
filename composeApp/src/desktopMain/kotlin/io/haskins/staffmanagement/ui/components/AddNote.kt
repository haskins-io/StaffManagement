package io.haskins.staffmanagement.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import io.haskins.staffmanagement.dao.ProjectDao
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

    Column {

        Row {
            TextField(title.value, { title.value = it })
        }

        Row {
            TextArea(note.value, { note.value = it })
        }

        Row {
            DefaultButton({
                addingNew.value = false
            }) {
                Text("Cancel")
            }

            DefaultButton({
                save()

                addingNew.value = false
            }) {
                Text("Save")
            }
        }
    }
}