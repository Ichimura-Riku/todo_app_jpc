package com.example.todo_app_jpc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo_app_jpc.ui.home.HomeDestination
import com.example.todo_app_jpc.ui.home.MyAppView
import com.example.todo_app_jpc.ui.todo.DetailScreen
import com.example.todo_app_jpc.ui.todo.TodoDetailDestination

// ここではnavigationで画面遷移させるcomposeをそれぞれ設定する

@Composable
fun TodoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(navController = navController, startDestination = HomeDestination.route, modifier = modifier) {
        composable(route = HomeDestination.route) {
            MyAppView(modifier = modifier, onTodoClick = {navController.navigate("${TodoDetailDestination.route}/${it}")})
        }

        composable(route = TodoDetailDestination.routeWithArgs, arguments = listOf(navArgument(TodoDetailDestination.todoIdArg){
            type = NavType.IntType
        })){
            DetailScreen()
        }
    }
}