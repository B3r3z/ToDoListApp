@file:Suppress("FunctionName")
package com.example.todolistapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.example.todolistapp.data.DataSource
import androidx.compose.material3.Icon

@Composable
fun ToDoListScreen(modifier: Modifier = Modifier, onEdit: (Task) -> Unit = {},) {
    var showDeleteDialog by remember { mutableStateOf(false) }// State to control the visibility of the delete dialog
    var taskToDelete: Task? by remember { mutableStateOf(null) } // State to hold the task to delete
    Surface(
        modifier = modifier
            .padding(8.dp)
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
                        onEditClick = {onEdit(task)},
                        onDelete = {
                            showDeleteDialog = true
                            taskToDelete = task
                        },
                        onCheckedChange = {
                            // Handle checkbox state change
                            // For example, update the task's isCompleted property
                            // DataSource.updateTask(task.copy(isCompleted = it))
                            DataSource.updateTask(task.copy(isCompleted = it))
                        }
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
            .padding(8.dp)
            .combinedClickable(
                onClick = { expanded = !expanded },
                onLongClick = onDelete
            ),
        color = if (task.isCompleted) {
            //MaterialTheme.colorScheme.
            Color.Green
        } else {
            MaterialTheme.colorScheme.surface
        },
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp
    ) {
        /* -------CHECKBOX-------- */
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {onCheckedChange(it)}
        )
        /*-------------------- icon edit task -----------------*/
        IconButton(
            onClick = onEditClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.TwoTone.Edit,
                contentDescription = "Edit Task",
                //tint = MaterialTheme.colorScheme.primary
            )
        }
    }

}