package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable

@Composable
fun projectResources() {

    Scaffold(
        content = {
            Text("Resources Panel")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    )
}