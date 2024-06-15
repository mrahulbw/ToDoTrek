package com.todotrek.feature.todo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.todotrek.common.Routes
import com.todotrek.feature.todo.ui.AddToDoRoute
import com.todotrek.feature.todo.ui.ToDoListRoute

fun NavController.navigateToToDoList(navOptions: NavOptions? = null) {
    navigate(Routes.TO_DO_LIST, navOptions)
}

fun NavController.navigateToAddToDo(navOptions: NavOptions? = null) {
    navigate(Routes.ADD_TO_DO, navOptions)
}

fun NavGraphBuilder.toDoListScreen(navigateToAddToDo: () -> Unit) {
    composable(Routes.TO_DO_LIST) {
        ToDoListRoute(navigateToAddToDo)
    }
}

fun NavGraphBuilder.addToDoScreen() {
    composable(Routes.ADD_TO_DO) {
        AddToDoRoute()
    }
}