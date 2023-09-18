package com.example.todo_app_jpc.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_app_jpc.R
import com.example.todo_app_jpc.ui.TodoAppViewModelProvider
import com.example.todo_app_jpc.ui.home.TodoAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier.padding(10.dp),
    viewModel: TodoDetailViewModel = viewModel(factory = TodoAppViewModelProvider.Factory)
) {
    MaterialTheme {
        Scaffold(
            topBar = { TodoAppBar(topBarText = "") },


        ){ innerPadding -> TodoDetailBody(modifier = modifier.padding(innerPadding))

        }
    }
}

@Composable
fun TodoDetailBody(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,


    ) {
        TodoDetails()
    }
}

@Composable
fun TodoDetails(
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(text = "title")
            Divider()


        }
    }
}

