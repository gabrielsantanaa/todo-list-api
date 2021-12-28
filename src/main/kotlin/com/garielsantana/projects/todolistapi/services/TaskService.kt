package com.garielsantana.projects.todolistapi.services

import com.garielsantana.projects.todolistapi.exception.AccessDeniedException
import com.garielsantana.projects.todolistapi.exception.ObjectNotFoundException
import com.garielsantana.projects.todolistapi.models.Task
import com.garielsantana.projects.todolistapi.models.TasksOverview
import com.garielsantana.projects.todolistapi.models.dto.NewTaskDTO
import com.garielsantana.projects.todolistapi.repositories.TaskRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val userService: UserService
) {

    fun getTaskById(id: Long): Task = taskRepository
        .findById(id)
        .orElseThrow { ObjectNotFoundException("Could not find this todo id: $id") }

    fun getTasksFromCurrentUser(
        page: Int,
        linesPerPage: Int,
        orderBy: String,
        direction: String
    ): Page<Task> {
        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.fromString(direction), orderBy)
        return taskRepository.findTasksByUserId(userService.getCurrentUser().id, pageRequest)
    }

    fun getTasksOverview(): TasksOverview {
        val localDateTimeNow = LocalDateTime.now()
        val numberOfCompletedTasks = taskRepository.countByIsCompleted(true)
        val numberOfPendingTasks: Int = taskRepository.countByIsCompleted(false)
        return TasksOverview(
            totalTasks = numberOfPendingTasks + numberOfCompletedTasks,
            numberOfPendingTasks = numberOfPendingTasks,
            numberOfCompletedTasks = numberOfCompletedTasks,
            tasksInNext7Days = taskRepository.findTasksBetweenDates(
                localDateTimeNow,
                localDateTimeNow.plusDays(7)
            )
        )
    }

    @Transactional
    fun updateTask(id: Long, oldTask: NewTaskDTO): Task {
        val newTask = getTaskById(id)
        if (newTask.user.id != userService.getCurrentUser().id)
            throw AccessDeniedException("You do not have authorization to change this resource")
        newTask.title = oldTask.title
        newTask.description = oldTask.description
        newTask.updateDate()
        return taskRepository.save(newTask)
    }

    @Transactional
    fun createTask(task: NewTaskDTO): Task {
        val newTask = Task(
            title = task.title,
            description = task.description,
            user = userService.getCurrentUser(),
            taskCategory = task.taskCategory,
            dueTime = task.dueTime
        )
        return taskRepository.save(newTask)
    }
}