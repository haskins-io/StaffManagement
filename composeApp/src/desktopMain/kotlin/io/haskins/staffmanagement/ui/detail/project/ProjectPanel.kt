package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.DetailTitle
import io.haskins.staffmanagement.ui.detail.project.overview.projectOverview
import io.haskins.staffmanagement.ui.detail.project.resources.ProjectResources
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.*

@Composable
fun projectPanel(currentDetail: MutableState<ListItem>) {

    var selectedTabIndex by remember { mutableStateOf(0) }

    val theTabs = listOf("Overview", "Tasks","Notes", "Resources")
    val tabs = mutableListOf<TabData>()

    if (currentDetail.value.id == 0) {
        ProjectsOverview(currentDetail)
    } else {
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
            DetailTitle(currentDetail.value.name)

            Row(verticalAlignment = Alignment.CenterVertically) {
                TabStrip(tabs, modifier = Modifier.weight(1f))
            }
            Row {
                Divider(Orientation.Horizontal)
            }

            when (selectedTabIndex) {
                0 -> projectOverview(currentDetail)
                1 -> ProjectTasks(currentDetail)
                2 -> ProjectNotes(currentDetail)
                3 -> ProjectResources(currentDetail)
            }
        }
    }
}
