@file:Suppress("FunctionName")
package com.example.todolistapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolistapp.data.Task
import com.example.todolistapp.ui.theme.ToDoListAppTheme
import androidx.compose.foundation.lazy.items
import com.example.todolistapp.data.DataSource

@Composable
fun ToDoListScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.padding(8.dp)
    ) {
        Column {
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                items(
                    items = DataSource.getTasks(),
                ) { task ->
                    TaskItem(
                        task = task,
                        onEditClick = {},
                        onDelete = {},
                        onCheckedChange = {}
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun ToDoListScreenPreview() {
    ToDoListAppTheme {
        ToDoListScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    task: Task,
    onEditClick: () -> Unit,
    onDelete: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) } // Toggle expanded state
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp
    ) {
        // Contents here
    }

}