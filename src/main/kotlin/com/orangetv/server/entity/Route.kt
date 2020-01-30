package com.orangetv.server.entity

/**
 * Assign a default value to each primary constructor parameter
 * to generate an empty constructor for myBatis to use
 */
data class Route(
        val id: Int = 0,
        val host: String = "localhost",
        val subPath: String = "/",
        val port: Int = 8080
)