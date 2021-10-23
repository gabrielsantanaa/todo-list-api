package com.garielsantana.projects.todolistapi.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var description: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    @get:JsonIgnore
    val user: User
) {
    @Temporal(TemporalType.DATE)
    val creationDate: Date = Date(System.currentTimeMillis())

    @Temporal(TemporalType.DATE)
    var lastUpdatedDate: Date = Date(System.currentTimeMillis())

    fun updateDate() {
        lastUpdatedDate = Date(System.currentTimeMillis())
    }

}