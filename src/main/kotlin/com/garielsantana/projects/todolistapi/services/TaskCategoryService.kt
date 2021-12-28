package com.garielsantana.projects.todolistapi.services

import com.garielsantana.projects.todolistapi.exception.ObjectNotFoundException
import com.garielsantana.projects.todolistapi.models.TaskCategory
import com.garielsantana.projects.todolistapi.repositories.TaskCategoryRepository
import com.garielsantana.projects.todolistapi.utils.getAuthenticatedUser
import org.springframework.stereotype.Service

@Service
class TaskCategoryService(
    private val taskCategoryRepository: TaskCategoryRepository,
    private val userService: UserService
) {

    fun getTaskCategoryById(id: Long): TaskCategory {
        val currentUser = userService.getCurrentUser()
        return taskCategoryRepository.findTaskCategoryByIdAndUserId(id, currentUser.id)
            ?: throw ObjectNotFoundException("Task category not found for this id")
    }

    fun getTaskCategoriesByUser(): List<TaskCategory> {
        val currentUser = userService.getCurrentUser()
        return taskCategoryRepository.findAllByUserId(currentUser.id)
    }
}