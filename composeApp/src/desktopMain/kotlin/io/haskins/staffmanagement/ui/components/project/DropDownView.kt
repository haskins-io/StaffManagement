package io.haskins.staffmanagement.ui.components.project

import androidx.compose.runtime.*
import io.haskins.staffmanagement.models.DropDownOption
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text

@Composable
fun DropDownView(selected: MutableState<Int>, options: List<DropDownOption>) {

    val name: MutableState<String> = remember {
        mutableStateOf(
            options.first { it.id == selected.value }.name
        )
    }

    Dropdown(
        menuContent = {
            options.forEach { item ->
                selectableItem(
                    selected = selected.value == item.id,
                    onClick = {
                        selected.value = item.id
                        name.value = item.name
                    },
                ) {
                    Text(item.name)
                }
            }
        }
    ) {
        Text(name.value)
    }
}