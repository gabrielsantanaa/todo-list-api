package com.garielsantana.projects.todolistapi.models.dto

import java.io.Serializable


class CredentialsDTO : Serializable {
    var email: String? = null
    var password: String? = null

    constructor() {}
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}