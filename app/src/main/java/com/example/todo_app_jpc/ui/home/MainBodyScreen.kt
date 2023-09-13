package com.example.todo_app_jpc.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_app_jpc.R
import com.example.todo_app_jpc.data.TodoEntity
import com.example.todo_app_jpc.ui.TodoAppViewModelProvider


//これはtutorialで使われてた画面
@Composable
fun MainBody(
     onTodoClick: (Int) -> Unit = {}, modifier: Modifier = Modifier, viewModel: MainBodyViewModel = viewModel(factory = TodoAppViewModelProvider.Factory)
) {
    val mainBodyUiState by viewModel.mainBodyUiState.collectAsState()
    val todoEntityList = mainBodyUiState.todoList
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (todoEntityList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            AllTodoList(
                todoEntityList = todoEntityList,
                onTodoClick = { onTodoClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

//　これもtutorialのやつ
@Composable
private fun AllTodoList(
    todoEntityList: List<TodoEntity>, onTodoClick: (TodoEntity) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier){
        items(items = todoEntityList, key = {it.id}) { item ->
            TodoItem(todoEntity = item, modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .clickable { onTodoClick(item) })
        }
    }
}

@Composable
private fun TodoItem(
    todoEntity: TodoEntity, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
//            rowいらない説濃厚
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = todoEntity.title,
                    style = MaterialTheme.typography.titleLarge,
                )

            }
        }
    }
}
