package com.garielsantana.projects.todolistapi.services

import com.garielsantana.projects.todolistapi.models.TaskCategory
import com.garielsantana.projects.todolistapi.repositories.TaskCategoryRepository
import com.garielsantana.projects.todolistapi.utils.getAuthenticatedUser
import org.springframework.stereotype.Service

@Service
class TaskCategoryService(
    private val taskCategoryRepository: TaskCategoryRepository,
    private val userService: UserService
) {

    fun getTaskCategoriesByUser(): List<TaskCategory> {
        val currentUser = userService.getCurrentUser()
        return taskCategoryRepository.findAllByUserId(currentUser.id)
    }
}