package com.example.todoAppJpc.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoAppJpc.R


@Composable
fun TodoEntryBody(
    viewModel: TodoEntryViewModel = hiltViewModel(),
    todoState: TodoState,
    onTodoValueChange: (TodoState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {


    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            TodoInputForm(
                viewModel = viewModel,
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
        BottomAppBar(
            modifier = Modifier.statusBarsPadding(),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        painterResource(id = R.drawable.round_format_list_bulleted_24),
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        painterResource(id = R.drawable.round_swap_vert_24),
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        painterResource(id = R.drawable.round_more_horiz_24),
                        contentDescription = "Localized description",
                    )
                }
            }

        }
    }

}

@Composable
fun TodoInputForm(
    viewModel: TodoEntryViewModel,
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
        if (viewModel.getShowContentTextField()) {
            TextField(
                value = todoState.content,
                onValueChange = { onValueChange(todoState.copy(content = it)) },
                label = { Text(stringResource(R.string.todo_content_req)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
            )
        }
    }
}
