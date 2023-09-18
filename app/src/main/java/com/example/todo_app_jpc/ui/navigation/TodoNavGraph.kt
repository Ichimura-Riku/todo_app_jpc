package com.example.todo_app_jpc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo_app_jpc.ui.home.MyAppView

// ここではnavigationで画面遷移させるcomposeをそれぞれ設定する

@Composable
fun TodoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable(route = "home") { //　home -> HomeDestination.route
            MyAppView(modifier = modifier)
        }
    }
}