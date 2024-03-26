package io.haskins.staffmanagement.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import io.haskins.staffmanagement.ui.components.twoColumnLayout

@Composable
@Preview
fun window() {

    MaterialTheme {
        twoColumnLayout()
    }
}
