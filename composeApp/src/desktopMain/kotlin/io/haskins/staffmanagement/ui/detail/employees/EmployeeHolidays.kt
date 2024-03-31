package io.haskins.staffmanagement.ui.detail.employees

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.buttonrows.AddButtonRow

@Composable
fun employeeHolidays(currentDetail: MutableState<ListItem>) {

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(10.dp)) {

        AddButtonRow(
            "Add Holiday",
            addingNew
        )

        Row {

        }
    }
}