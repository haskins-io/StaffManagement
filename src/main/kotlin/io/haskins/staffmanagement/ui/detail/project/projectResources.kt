package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.TableCell
import io.haskins.staffmanagement.ui.components.column2Weight

@Composable
fun projectResources(currentDetail: MutableState<ListItem>) {

    val employees = ProjectDao.getInstance().projectResources(currentDetail.value.id)

    val vScrollState = rememberScrollState()

    Scaffold(
        content = {

            Box(modifier = Modifier.padding(10.dp).verticalScroll(vScrollState)) {
                DataTable(
                    columns = {
                        headerBackground {
                            Box(modifier = Modifier.background(color = Color.LightGray))
                        }
                        column { Text("Name") }
                        column { Text("Allocation") }
                        column { Text("Cost") }
                    }
                ) {

                    employees.forEach { employee ->
                        row(modifier = Modifier) {
                            cell { Text(text = employee.name) }
                            cell { Text(text = employee.allocation.toString()) }
                            cell { Text(text = employee.cost.toString()) }
                        }
                    }
                }
            }
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