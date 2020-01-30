package com.orangetv.server.entity

/**
 * Assign a default value to each primary constructor parameter
 * to generate an empty constructor for myBatis to use
 */
data class MovieRoute(
        val id: Int = 0,
        val movieId: Int = 0,
        val hostId: Int = 0
)