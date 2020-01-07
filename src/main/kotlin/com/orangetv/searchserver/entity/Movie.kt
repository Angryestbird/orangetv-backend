package com.orangetv.searchserver.entity

data class Movie(
        val id: Int,
        val title: String,
        val posterUrl: String,
        val videoUrl: String,
        val description: String?
)