package com.garielsantana.projects.todolistapi.controllers

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.garielsantana.projects.todolistapi.models.Todo
import com.garielsantana.projects.todolistapi.models.dto.TodoDTO
import com.garielsantana.projects.todolistapi.services.TodoService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import kotlin.jvm.Throws

@RestController
@RequestMapping("/todos")
class TodoController(
    private val todoService: TodoService
) {
    @GetMapping("/page")
    fun getTodosFromCurrentUser(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "linesPerPage", defaultValue = "10") linesPerPage: Int,
        @RequestParam(value = "orderBy", defaultValue = "id") orderBy: String,
        @RequestParam(value = "direction", defaultValue = "DESC") direction: String
    ): ResponseEntity<Page<Todo>> {
        return ResponseEntity.ok(todoService.getTodosFromCurrentUser(page, linesPerPage, orderBy, direction))
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable("id") id: Long): ResponseEntity<Todo> = ResponseEntity.ok(todoService.getTodoById(id))
    
    @PostMapping
    fun newTodo(@RequestBody todo: TodoDTO): ResponseEntity<Todo> {
        val createdTodo = todoService.newTodo(todo)
        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdTodo.id)
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @PutMapping("/{id}")
    fun updateTodo(@RequestBody todo: TodoDTO, @PathVariable("id") id: Long): ResponseEntity<Todo> =
        ResponseEntity.ok(todoService.updateTodo(id,todo))



}