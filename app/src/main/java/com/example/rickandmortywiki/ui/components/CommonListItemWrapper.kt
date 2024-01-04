package com.example.rickandmortywiki.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun CommonListItemWrapper(content: @Composable () -> Unit) {
    Surface(
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colors.onBackground
        ),
        modifier = androidx.compose.ui.Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        content()
    }
}
