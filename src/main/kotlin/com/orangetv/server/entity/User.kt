package com.orangetv.server.entity

data class User(
        val id: Int = 0,
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val enabled: Boolean = true,
        val motto: String = ""
)