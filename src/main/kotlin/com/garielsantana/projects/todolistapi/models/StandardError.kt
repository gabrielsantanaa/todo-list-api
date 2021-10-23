package com.garielsantana.projects.todolistapi.models

import java.io.Serializable
import java.util.*

data class StandardError(
    val timestamp: Date = Date(),
    val status: Int,
    val error: String,
    val path: String,
): Serializable