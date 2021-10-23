package com.garielsantana.projects.todolistapi.repositories

import com.garielsantana.projects.todolistapi.models.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TodoRepository: JpaRepository<Todo, Long> {
    @Query("select t from Todo t where t.user.id = :id")
    fun findTodosByUserId(id: Long, pageable: Pageable): Page<Todo>
}