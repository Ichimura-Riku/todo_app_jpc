package com.example.todo_app_jpc.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_app_jpc.R
import com.example.todo_app_jpc.ui.theme.Todo_app_jpcTheme


@ExperimentalMaterial3Api
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier,

        ) {
        Text(
            text = "Hello $name!",
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimaryContainer

        )
        TextField(value = text, onValueChange = { text = it })

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun MyAppView() {
    val topAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    )
    Todo_app_jpcTheme {
        Scaffold(
            topBar = {

                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = {
                        Text(
                            text = "top app bar",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    colors = topAppBarColors,
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.statusBarsPadding()
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Row {
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    painterResource(id = R.drawable.round_format_list_bulleted_24),
                                    contentDescription = "Localized description"
                                )
                            }
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    painterResource(id = R.drawable.round_swap_vert_24),
                                    contentDescription = "Localized description"
                                )
                            }
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    painterResource(id = R.drawable.round_more_horiz_24),
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                        FloatingActionButton(
                            onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(Icons.Rounded.Add, "localized description")
                        }
                    }
                }
            },
            content = {
                Surface(
                    modifier = Modifier, color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Greeting("Android")
                    }
                }
            },
        )
    }
}


@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun ScfPreview() {
    Todo_app_jpcTheme {
        MyAppView()
    }
}

