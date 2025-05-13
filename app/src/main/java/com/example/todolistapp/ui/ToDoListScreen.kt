@file:Suppress("FunctionName")
package com.example.todolistapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.example.todolistapp.data.DataSource
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.todolistapp.R
import com.example.todolistapp.database.ToDoRepository

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ToDoListScreen(modifier: Modifier = Modifier,
                   onEdit: (Task) -> Unit = {},
                   repository: ToDoRepository) {
    var showDeleteDialog by remember { mutableStateOf(false) }// State to control the visibility of the delete dialog
    var taskToDelete: Task? by remember { mutableStateOf(null) } // State to hold the task to delete
    val tasks by repository.tasksFlow.collectAsState(initial = emptyList()) // Collecting tasks from the repository
    Log.d("ToDoListScreen", "Tasks: $tasks") // Logging the tasks for debugging
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
                   // items = DataSource.getTasks(),
                    items = tasks
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
            if(showDeleteDialog && taskToDelete != null) {
                DeleteConfirmationDialog(
                    task= taskToDelete!!,
                    onConfirm = {
                        // Handle task deletion
                        DataSource.deleteTask(taskToDelete!!)
                        showDeleteDialog = false
                        taskToDelete = null
                    },
                    onDismiss= {
                        // Dismiss the dialog without deleting
                        showDeleteDialog = false
                        taskToDelete = null
                    }
                )
            }
        }
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
                onLongClick = onDelete // Long click to delete
            ),
        color = if (task.isCompleted) {
            Color.DarkGray // Consider using MaterialTheme.colorScheme for consistency
        } else {
            MaterialTheme.colorScheme.surface
        },
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp
    ) {
        Column( // Changed from Row to Column
            modifier = Modifier.padding(8.dp) // Added padding to the Column
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                /* -------CHECKBOX-------- */
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onCheckedChange(it) }
                )
                /*-------------------- icon edit task -----------------*/
                IconButton(
                    onClick = onEditClick,
                    // modifier = Modifier.padding(8.dp) // Padding can be adjusted or removed if Column padding is sufficient
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Edit,
                        contentDescription = "Edit Task",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                /*-------------------- task name -----------------*/
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .weight(1f) // Take up remaining space in this Row
                        .padding(start = 8.dp)
                )
            }
            /*-------------------- task description -----------------*/
            if (expanded) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth() // Description takes full width
                        .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 8.dp) // Padding for the description
                )
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    task: Task,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss, // Dismiss the dialog when the user clicks outside of it
        title = { Text(stringResource(R.string.delete_task_title)) },
        text = { Text(stringResource(R.string.delete_msg, task.name)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.delete), color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel),color = Color.Gray)
            }
        }
    )
}

@Preview
@Composable
fun DeleteConfirmationDialogPreview() {
    ToDoListAppTheme {
        DeleteConfirmationDialog(
            task = Task("Task 1", "Description of task 1"),
            onConfirm = {},
            onDismiss = {}
        )
    }
}