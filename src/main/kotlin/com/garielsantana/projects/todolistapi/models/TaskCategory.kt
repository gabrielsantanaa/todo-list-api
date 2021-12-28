package com.garielsantana.projects.todolistapi.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "task_categories")
data class TaskCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var name: String,

    @ManyToOne
    @JoinColumn(name = "task_id")
    @get:JsonIgnore
    val user: User
) {
    @OneToMany(mappedBy = "taskCategory")
    @get:JsonIgnore
    val tasks: List<Task> = arrayListOf()

    val numberOfTasks: Int
        get() = tasks.size
}