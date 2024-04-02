package io.haskins.staffmanagement.ui.detail.departments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.DepartmentDao
import io.haskins.staffmanagement.ui.components.TableHeader
import io.haskins.staffmanagement.ui.components.buttonrows.AddButtonRow
import org.jetbrains.jewel.ui.component.Text

@Composable
fun departmentPanel() {

    val departments = DepartmentDao.getInstance().departments()

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    val vScrollState = rememberScrollState()

    Column(modifier = Modifier.padding(10.dp)) {

        if (addingNew.value) {
            AddDepartment(addingNew)
        } else {

            AddButtonRow(
                "Add Department",
                addingNew
            )

            Row {

                Box(modifier = Modifier.verticalScroll(vScrollState)) {

                    DataTable(
                        columns = {
                            column { TableHeader("Department") }
                            column { TableHeader("Head") }
                        }
                    ) {

                        departments.forEach { department ->

                            row {
                                cell { Text(text = department.name) }
                                cell { Text(text = department.headName) }
                            }
                        }
                    }
                }
            }
        }
    }
}