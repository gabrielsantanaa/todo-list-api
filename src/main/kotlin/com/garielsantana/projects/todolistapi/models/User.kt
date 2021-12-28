package com.garielsantana.projects.todolistapi.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.garielsantana.projects.todolistapi.security.ProfileAuthorities
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(unique = true, nullable = false)
    var email: String,
    @get:JsonIgnore
    var password: String
) {

    @OneToMany(mappedBy = "user")
    @get:JsonIgnore
    val tasks: List<Task> = arrayListOf()

    @OneToMany(mappedBy = "user")
    @get:JsonIgnore
    val taskCategories: List<TaskCategory> = arrayListOf()

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profiles")
    private val authorities: MutableSet<Int> = HashSet()

    init {
        authorities.add(ProfileAuthorities.CLIENT.code)
    }

    fun setAsAdmin() = authorities.add(ProfileAuthorities.ADMIN.code)

    @JsonIgnore
    fun getAuthorities(): Set<ProfileAuthorities> = authorities.map { ProfileAuthorities.fromCode(it) }.toSet()

}