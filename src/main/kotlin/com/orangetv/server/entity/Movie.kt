package com.orangetv.server.entity

/**
 * Assign a default value to each primary constructor parameter
 * to generate an empty constructor for myBatis to use
 */
data class Movie(
        val id: Int = 0,
        val title: String = "",
        val posterUrl: String = "",
        val videoUrl: String = "",
        val host: String = "localhost:8080",
        val description: String = ""
)