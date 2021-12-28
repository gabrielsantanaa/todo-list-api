package com.garielsantana.projects.todolistapi.models

data class TasksOverview(
    val totalTasks: Int,
    val numberOfPendingTasks: Int,
    val numberOfCompletedTasks: Int,
    val tasksInNext7Days: List<Task>
)