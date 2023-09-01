@file:OptIn(InternalComposeUiApi::class)

package com.example.todo_app_jpc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.InternalComposeUiApi
import androidx.core.view.WindowCompat
import com.example.todo_app_jpc.ui.home.MyAppView
import com.example.todo_app_jpc.ui.theme.Todo_app_jpcTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Todo_app_jpcTheme {
                MyAppView()
            }
        }
    }
}
