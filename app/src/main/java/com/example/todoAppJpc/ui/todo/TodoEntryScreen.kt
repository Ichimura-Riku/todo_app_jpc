package com.example.todoAppJpc.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoAppJpc.R

@Composable
fun TodoEntryBody(
    todoState: TodoState,
    onTodoValueChange: (TodoState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        TodoInputForm(
            todoState = todoState,
            onValueChange = onTodoValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.add_action))
        }
    }
}

@Composable
fun TodoInputForm(
    todoState: TodoState,
    modifier: Modifier = Modifier,
    onValueChange: (TodoState) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TextField(
            value = todoState.title,
            onValueChange = { onValueChange(todoState.copy(title = it)) },
            label = { Text(stringResource(R.string.todo_title_req)) },
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.surface,
//                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
//                disabledContainerColor = MaterialTheme.colorScheme.surface,
//            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
        )
        TextField(
            value = todoState.content,
            onValueChange = { onValueChange(todoState.copy(content = it)) },
            label = { Text(stringResource(R.string.todo_content_req)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
        )
    }
}
