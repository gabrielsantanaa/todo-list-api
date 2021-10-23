package com.garielsantana.projects.todolistapi.security

enum class ProfileAuthorities(val code: Int, val description: String) {

    ADMIN(1, "ROLE_ADMIN"), CLIENT(2, "ROLE_CLIENT");

    companion object {
        fun fromCode(code: Int): ProfileAuthorities {
            for (x in ProfileAuthorities.values()) {
                if (x.code == code) {
                    return x
                }
            }
            throw IllegalArgumentException("Id inválido: $code")
        }
    }
}
