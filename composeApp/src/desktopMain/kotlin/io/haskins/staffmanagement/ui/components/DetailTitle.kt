package io.haskins.staffmanagement.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Text

@Composable
fun DetailTitle(title: String) {
    Row(modifier = Modifier.padding(10.dp)) {
        Text(title)
    }
}