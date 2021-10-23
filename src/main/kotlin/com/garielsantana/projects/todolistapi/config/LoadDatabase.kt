package com.garielsantana.projects.todolistapi.config

import com.garielsantana.projects.todolistapi.models.Todo
import com.garielsantana.projects.todolistapi.models.User
import com.garielsantana.projects.todolistapi.repositories.TodoRepository
import com.garielsantana.projects.todolistapi.repositories.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
internal class LoadDatabase {

    @Bean
    fun initDatabase(
        userRepository: UserRepository,
        todoRepository: TodoRepository,
        pe: BCryptPasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {
            if(userRepository.findByEmail("admin@gmail.com") == null) {
                val user = User(email = "admin@gmail.com", password = pe.encode("123456"))
                user.setAsAdmin()
                userRepository.save(user)

                val todo = Todo(title = "example title", description = "example description", user = user)
                todoRepository.save(todo)
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
