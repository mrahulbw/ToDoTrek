package com.todotrek.feature.todo

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.todotrek.common.Routes
import com.todotrek.feature.todo.ui.addtodo.AddToDoRoute
import com.todotrek.feature.todo.ui.todolist.ToDoListRoute

fun NavController.navigateToToDoList(navOptions: NavOptions? = null) {
    navigate(Routes.TO_DO_LIST, navOptions)
}

fun NavController.navigateToAddToDo(navOptions: NavOptions? = null) {
    navigate(Routes.ADD_TO_DO, navOptions)
}

fun NavGraphBuilder.toDoListScreen(
    navController: NavHostController
) {
    composable(
        Routes.TO_DO_LIST,
        enterTransition = {
            return@composable fadeIn(tween(1000))
        }, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
            )
        }, popEnterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(700)
            )
        }) {
        val parentEntry = remember(it) {
            navController.getBackStackEntry(Routes.TO_DO_LIST)
        }

        val toDoViewModel: ToDoViewModel = hiltViewModel(parentEntry)

        ToDoListRoute(toDoViewModel, navController::navigateToAddToDo)
    }
}

fun NavGraphBuilder.addToDoScreen(navController: NavHostController) {
    composable(
        Routes.ADD_TO_DO,
        enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
            )
        },
        popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(700)
            )
        },
    ) {
        val parentEntry = remember(it) {
            navController.getBackStackEntry(Routes.TO_DO_LIST)
        }

        val toDoViewModel: ToDoViewModel = hiltViewModel(parentEntry)

        AddToDoRoute(toDoViewModel) {
            navController.popBackStack()
        }
    }
}