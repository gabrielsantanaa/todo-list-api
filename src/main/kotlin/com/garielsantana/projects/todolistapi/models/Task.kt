package com.garielsantana.projects.todolistapi.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var title: String,

    var description: String,

    var isCompleted: Boolean = false,

    var isStarred: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @get:JsonIgnore
    val user: User,

    @ManyToOne
    @JoinColumn(name = "task_id")
    @get:JsonIgnore
    val taskCategory: TaskCategory,

    var dueTime: LocalDateTime
) {
    val creationDate: LocalDateTime = LocalDateTime.now()

    var lastUpdatedDate: LocalDateTime = LocalDateTime.now()

    fun updateDate() {
        lastUpdatedDate = LocalDateTime.now()
    }
}

@Converter(autoApply = true)
class LocalDateTimeAttributeConverter: AttributeConverter<LocalDateTime, Date> {
    override fun convertToDatabaseColumn(localDateTime: LocalDateTime?): Date {
        return localDateTime?.let {
            val instant = localDateTime.toInstant(ZoneOffset.UTC)
            return Date.from(instant)
        } ?: run {
            Date()
        }
    }

    override fun convertToEntityAttribute(date: Date?): LocalDateTime {
        return date?.let {
            val instant = Instant.ofEpochMilli(date.time)
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        } ?: run {
            LocalDateTime.now()
        }

    }

}