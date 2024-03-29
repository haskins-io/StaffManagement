package io.haskins.staffmanagement.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import io.haskins.staffmanagement.dao.ProjectDao
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirm: () -> Unit,
    cancel: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            cancel()
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    confirm()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    cancel()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}