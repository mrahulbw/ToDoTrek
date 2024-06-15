package com.todotrek.feature.todo.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.todotrek.convertor.R

@Composable
fun ExtendedFabView(
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        expanded = isExpanded,
        icon = { Icon(Icons.Filled.Add, stringResource(R.string.add_to_do_button)) },
        text = { Text(text = stringResource(R.string.add_todo)) },
    )
}