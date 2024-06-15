package com.todotrek.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.todotrek.common.Routes
import com.todotrek.feature.todo.addToDoScreen
import com.todotrek.feature.todo.navigateToAddToDo
import com.todotrek.feature.todo.toDoListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = Routes.TO_DO_LIST
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        toDoListScreen(navigateToAddToDo = navController::navigateToAddToDo)
        addToDoScreen()
    }
}