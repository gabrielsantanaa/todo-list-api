package com.garielsantana.projects.todolistapi.repositories

import com.garielsantana.projects.todolistapi.models.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface TaskRepository: JpaRepository<Task, Long> {


    fun findTasksByUser(id: Long, pageable: Pageable): Page<Task>

    @Query("SELECT task FROM Task task WHERE task.dueTime BETWEEN :startDate AND :endDate")
    fun findTasksBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): List<Task>

    fun countByIsCompleted(isCompleted: Boolean): Int

//    @Query("SELECT count(*) FROM Task WHERE task.id = :id and task.isCompleted = false")
//    fun getNumberOfPendingTasksByUser(id: Long): Int
//
//    @Query("SELECT count(*) FROM Task WHERE task.id = :id and task.isCompleted = false")
//    fun getNumberOfCompletedTasksByUser(id: Long): Int

}