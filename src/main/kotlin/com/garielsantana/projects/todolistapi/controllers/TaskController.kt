package com.garielsantana.projects.todolistapi.controllers

import com.garielsantana.projects.todolistapi.models.Task
import com.garielsantana.projects.todolistapi.models.TasksOverview
import com.garielsantana.projects.todolistapi.models.dto.NewTaskDTO
import com.garielsantana.projects.todolistapi.services.TaskService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService
) {
    @GetMapping("/page")
    fun getTasksFromCurrentUser(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "linesPerPage", defaultValue = "10") linesPerPage: Int,
        @RequestParam(value = "orderBy", defaultValue = "id") orderBy: String,
        @RequestParam(value = "direction", defaultValue = "DESC") direction: String
    ): ResponseEntity<Page<Task>> {
        return ResponseEntity.ok(taskService.getTasksFromCurrentUser(page, linesPerPage, orderBy, direction))
    }


    @GetMapping("/overview")
    fun getTasksOverview(): ResponseEntity<TasksOverview> {
        return ResponseEntity.ok(taskService.getTasksOverview())
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable("id") id: Long): ResponseEntity<Task> = ResponseEntity.ok(taskService.getTaskById(id))
    
    @PostMapping
    fun createTask(@RequestBody todo: NewTaskDTO): ResponseEntity<Task> {
        val createdTodo = taskService.createTask(todo)
        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdTodo.id)
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @PutMapping("/{id}")
    fun updateTask(@RequestBody todo: NewTaskDTO, @PathVariable("id") id: Long): ResponseEntity<Task> =
        ResponseEntity.ok(taskService.updateTask(id,todo))



}