package com.example.todoAppJpc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoAppJpc.ui.home.HomeDestination
import com.example.todoAppJpc.ui.home.MyAppView
import com.example.todoAppJpc.ui.todo.EditScreen
import com.example.todoAppJpc.ui.todo.TodoEditDestination

// ここではnavigationで画面遷移させるcomposeをそれぞれ設定する

@Composable
fun TodoNavHost(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    modifier: Modifier = Modifier,
) {
    NavHost(navController = navController, startDestination = HomeDestination.route, modifier = modifier) {
        composable(route = HomeDestination.route) {
            MyAppView(
                modifier = modifier,
                onTodoClick = { navController.navigate("${TodoEditDestination.route}/$it") },
            )
        }

// 　ここのcomposable引数は、Roomから特定のデータを参照するときに必要な値
        composable(
            route = TodoEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(TodoEditDestination.todoIdArg) {
                    type = NavType.IntType
                },
            ),
        ) {
            EditScreen(
                modifier = modifier,
                navController = navController,
                navBackStackEntry = navBackStackEntry,
            )
        }
    }
}
