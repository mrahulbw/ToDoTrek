package com.todotrek.feature.todo.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.todotrek.feature.todo.ToDoViewModel

@Composable
fun AddToDoRoute() {
    AddToDoScreen()
}

@Composable
fun AddToDoScreen(
    viewModel: ToDoViewModel = hiltViewModel()
) {

}