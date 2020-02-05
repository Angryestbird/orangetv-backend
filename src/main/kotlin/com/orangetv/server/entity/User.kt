package com.orangetv.server.entity

import javax.validation.constraints.NotBlank

data class User(
        val id: Int = 0,
        @get:NotBlank val name: String = "",
        @get:NotBlank val email: String = "",
        @get:NotBlank val password: String = "",
        val enabled: Boolean = true,
        val motto: String = ""
)