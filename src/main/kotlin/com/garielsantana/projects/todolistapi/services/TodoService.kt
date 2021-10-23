package com.garielsantana.projects.todolistapi.services

import com.garielsantana.projects.todolistapi.exception.AccessDeniedException
import com.garielsantana.projects.todolistapi.exception.ObjectNotFoundException
import com.garielsantana.projects.todolistapi.models.Todo
import com.garielsantana.projects.todolistapi.models.dto.TodoDTO
import com.garielsantana.projects.todolistapi.repositories.TodoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val userService: UserService
) {

    fun getTodoById(id: Long): Todo = todoRepository
        .findById(id)
        .orElseThrow { ObjectNotFoundException("Could not find this todo id: $id") }

    fun getTodosFromCurrentUser(
        page: Int,
        linesPerPage: Int,
        orderBy: String,
        direction: String
    ): Page<Todo> {
        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.fromString(direction), orderBy)
        return todoRepository.findTodosByUserId(userService.getCurrentUser().id, pageRequest)
    }

    @Transactional
    fun updateTodo(id: Long, todo: TodoDTO): Todo {
        val newTodo = getTodoById(id)
        if (newTodo.user.id != userService.getCurrentUser().id)
            throw AccessDeniedException("You do not have authorization to change this resource")
        newTodo.title = todo.title
        newTodo.description = todo.description
        newTodo.updateDate()
        return todoRepository.save(newTodo)
    }

    @Transactional
    fun newTodo(todo: TodoDTO): Todo {
        val newTodo = Todo(title = todo.title, description = todo.description, user = userService.getCurrentUser())
        return todoRepository.save(newTodo)
    }
}