package io.haskins.staffmanagement.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.models.ListItem

@Composable
fun employeePanel(currentDetail: MutableState<ListItem>) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(currentDetail.value.name)
    }
}