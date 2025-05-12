@file:Suppress("FunctionName")
package com.example.todolistapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolistapp.data.DataSource
import com.example.todolistapp.ui.AddEditScreen
import com.example.todolistapp.ui.ToDoListScreen

enum class ToDoAppDestinations() {
    List,
    Add,
    Edit
}

@Composable
fun ToDoListApp (modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(ToDoAppDestinations.Add.name)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ToDoAppDestinations.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = ToDoAppDestinations.List.name) {
                ToDoListScreen(
                    onEdit = { taskToEdit-> navController.navigate(ToDoAppDestinations.Edit.name + "/${taskToEdit.taskId}") },
                )
            }
            composable(route = ToDoAppDestinations.Add.name) {
                AddEditScreen(
                    onSave = {
                        DataSource.addTask(it)
                        navController.navigateUp()
                    },
                    onCancel = {navController.navigateUp()}
                )
            }
            composable(route = ToDoAppDestinations.Edit.name) {
                AddEditScreen(
                    task = null,
                    onSave = {},
                    onCancel = {}
                )
            }
            composable(route = ToDoAppDestinations.Edit.name + "/{taskToEditId}",
                arguments = listOf(navArgument(name = "taskToEditId") {
                    type = NavType.LongType
                }))
            { backStackEntry ->
                val taskToEditId = backStackEntry.arguments?.getLong("taskToEditId")
                taskToEditId ?: return@composable
                val task = DataSource.getTask(taskToEditId)
                AddEditScreen(
                    task = task,
                    onSave = {
                        DataSource.updateTask(it)
                        navController.navigateUp()
                    },
                    onCancel = {navController.navigateUp()}
                )
            }
        }
    }
}

@Preview
@Composable
fun ToDoListAppPreview() {
    ToDoListApp()
}