package com.garielsantana.projects.todolistapi.config

import com.garielsantana.projects.todolistapi.models.Task
import com.garielsantana.projects.todolistapi.models.TaskCategory
import com.garielsantana.projects.todolistapi.models.User
import com.garielsantana.projects.todolistapi.repositories.TaskCategoryRepository
import com.garielsantana.projects.todolistapi.repositories.TaskRepository
import com.garielsantana.projects.todolistapi.repositories.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@Configuration
internal class LoadDatabase {

    @Bean
    fun initDatabase(
        userRepository: UserRepository,
        taskRepository: TaskRepository,
        taskCategoryRepository: TaskCategoryRepository,
        pe: BCryptPasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {
            if(userRepository.findByEmail("admin@gmail.com") == null) {

                val user = User(email = "admin@gmail.com", password = pe.encode("123456"))
                user.setAsAdmin()
                val user2 = User(email = "user@gmail.com", password = pe.encode("12345678"))
                userRepository.saveAll(listOf(user, user2))

                val taskCategory = TaskCategory(name = "Food", user = user)
                taskCategoryRepository.save(taskCategory)

                val dateNow = LocalDateTime.now()
                val task = Task(
                    title = "example title",
                    description = "example description",
                    user = user,
                    taskCategory = taskCategory,
                    isCompleted = false,
                    dueTime = dateNow
                )
                val task2 = Task(
                    title = "example title 2",
                    description = "example description 2",
                    user = user,
                    taskCategory = taskCategory,
                    isCompleted = false,
                    dueTime = dateNow.plusDays(1)
                )
                val task3 = Task(
                    title = "example title 3",
                    description = "example description 3",
                    user = user,
                    taskCategory = taskCategory,
                    isCompleted = false,
                    dueTime = dateNow.plusDays(7)
                )
                val task4 = Task(
                    title = "example title 4",
                    description = "example description 4",
                    user = user,
                    taskCategory = taskCategory,
                    isCompleted = false,
                    dueTime = dateNow.plusDays(8)
                )
                taskRepository.saveAll(listOf(task, task2, task3, task4))
            }

//            val user = userRepository.findByEmail("admin@gmail.com")!!
//            val list = listOf("porra", "clint", "eastwood", "eita bixo", "i'm feeling glad", "it's coming on")
//            val list2 = mutableListOf<Todo>()
//            for(x in 1..1000) {
//                list2.add(Todo(title = list.random(), description = list.random(), user = user))
//            }
//            todoRepository.saveAll(list2)
        }
    }

}
