package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.models.ListItem
import org.jetbrains.jewel.ui.component.SimpleTabContent
import org.jetbrains.jewel.ui.component.TabData
import org.jetbrains.jewel.ui.component.TabStrip
import org.jetbrains.jewel.ui.component.Text

@Composable
fun projectPanel(currentDetail: MutableState<ListItem>) {

    var selectedTabIndex by remember { mutableStateOf(0) }

    val theTabs = listOf("Overview", "Notes", "Resources")
    val tabs = mutableListOf<TabData>()

    theTabs.forEachIndexed { index, title ->
        val data =
            TabData.Default(
                selected = index == selectedTabIndex,
                content = {tabState ->
                    SimpleTabContent(
                        state = tabState,
                        label = { Text(title) }
                    )
                },
                onClick = { selectedTabIndex = index }
            )

        tabs.add(data)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TabStrip(tabs, modifier = Modifier.weight(1f))
        }
        when (selectedTabIndex) {
            0 -> projectOverview()
            1 -> projectNotes()
            2 -> projectResources(currentDetail)
        }
    }
}