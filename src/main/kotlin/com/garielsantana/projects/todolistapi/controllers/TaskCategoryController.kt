package com.garielsantana.projects.todolistapi.controllers

import com.garielsantana.projects.todolistapi.models.TaskCategory
import com.garielsantana.projects.todolistapi.services.TaskCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task_categories")
class TaskCategoryController(
    private val taskCategoryService: TaskCategoryService
) {

    @GetMapping
    fun getTaskCategoriesByUser(): ResponseEntity<List<TaskCategory>> {
        return ResponseEntity.ok(taskCategoryService.getTaskCategoriesByUser())
    }
}