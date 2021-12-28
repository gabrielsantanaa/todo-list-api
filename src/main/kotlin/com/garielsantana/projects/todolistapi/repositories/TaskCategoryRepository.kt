package com.garielsantana.projects.todolistapi.repositories

import com.garielsantana.projects.todolistapi.models.TaskCategory
import org.springframework.data.jpa.repository.JpaRepository

interface TaskCategoryRepository: JpaRepository<TaskCategory, Long> {

    fun findAllByUserId(userId: Long): List<TaskCategory>
}