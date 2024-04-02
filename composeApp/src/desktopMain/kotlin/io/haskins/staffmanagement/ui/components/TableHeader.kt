package io.haskins.staffmanagement.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.jewel.ui.component.Text

@Composable
fun TableHeader(title: String) {
    Text(title, fontSize = 13.sp, fontWeight = FontWeight.Bold)
}