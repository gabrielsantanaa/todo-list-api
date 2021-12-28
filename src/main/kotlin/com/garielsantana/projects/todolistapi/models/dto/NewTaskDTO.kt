package com.garielsantana.projects.todolistapi.models.dto

import com.garielsantana.projects.todolistapi.models.TaskCategory
import java.io.Serializable
import java.time.LocalDateTime

data class NewTaskDTO(
    val title: String,
    val description: String,
    val taskCategory: TaskCategory,
    val dueTime: LocalDateTime
): Serializable